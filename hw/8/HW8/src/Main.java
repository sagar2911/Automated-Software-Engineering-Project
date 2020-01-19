import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
	
	private static final String rowsBestRestPath = "rowsBestRest.md";
	private static final String treesPath = "trees.txt";

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Error! example format java -jar hw8.jar auto.csv");
		}
		try {
			String filename = args[0];
			Path inputPath = Paths.get(filename);

			String inputCsv = new String(Files.readAllBytes(inputPath));
			
			
			Tbl tbl = new Tbl(inputCsv);

			String rowsBestRest = tbl.sort();
			String trees = tbl.tree();

			write(rowsBestRest, rowsBestRestPath);
			write(trees, treesPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write(String content, String path) throws IOException {
		Path file = Paths.get(path);
		Files.write(file, content.getBytes());
	}

}
