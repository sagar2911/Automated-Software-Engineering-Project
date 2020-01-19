import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("Error! example format java -jar hw6.jar diabetes.csv auto.csv");
		}
		try {
			Path dTreeInputPath = Paths.get(args[0]);
			Path rTreeInputPath = Paths.get(args[1]);

			String dTreeInputCsv = new String(Files.readAllBytes(dTreeInputPath));
			String rTreeInputCsv = new String(Files.readAllBytes(rTreeInputPath));

			Tbl dTbl = new Tbl(dTreeInputCsv);
			Tbl rTbl = new Tbl(rTreeInputCsv);

			write(dTbl.decisionTree(), "decision-tree-out.txt");
			write(rTbl.regressionTree(), "regression-tree-out.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write(String content, String path) throws IOException {
		Path file = Paths.get(path);
		Files.write(file, content.getBytes());
	}

}
