import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Report {

	private static final Random rand = new Random();
	
	public double reportTree(String filepath, boolean incremental, double alpha, int numRepeats) {
		try {
			Path scvPath = Paths.get(filepath);
			String csv = new String(Files.readAllBytes(scvPath));

			Tbl tbl = new Tbl(csv);

			Map<Row, Tbl> before = new HashMap<>();

			Tbl tree = tbl.computeTree(incremental, alpha); // build cluster tree

			Set<Row> rows = getRandomRows(tree.rows, tree);

			for (Row row : rows) {
				Tbl cluster = tree.cluster(row);
				before.put(row, cluster);
			}

			int sameCount = 0;
			int totalCount = 0;

			for (int i = 0; i < numRepeats; i++) {
				tree = tbl.computeTree(incremental, alpha); // rebuild cluster tree

				for (Row row : rows) {
					Tbl beforeCluster = before.get(row);
					Tbl afterCluster = tree.cluster(row);
					
					if (afterCluster == null) {
						continue;
					}

					sameCount += beforeCluster.sameCount(afterCluster);
					totalCount += beforeCluster.myGoals.size();
//					System.out.println("------------------------");
//					System.out.println(beforeCluster.baseline());
//					System.out.println(afterCluster.baseline());
				}
			}
			double score = (double) sameCount / totalCount;

			return score;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private static Set<Row> getRandomRows(List<Row> rows, Tbl tree) {
		Set<Row> res = new HashSet<>();
		while (res.size() < 100) {
			int i = rand.nextInt(rows.size());
			Row row = rows.get(i);
			if (!res.contains(row) || tree.cluster(row) != null) {
				res.add(row);
			}
		}
		return res;
	}

}
