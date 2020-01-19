import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Error! example format java -jar hw7.jar xomo10000.csv");
		}
		try {
			String filename = args[0];
			Path inputPath = Paths.get(filename);

			String inputCsv = new String(Files.readAllBytes(inputPath));

			Tbl tbl = new Tbl(inputCsv);

			String name = filename.substring(0, filename.indexOf('.'));
			String outputPath = name + ".out.txt";

			write(tbl.tree(), outputPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write(String content, String path) throws IOException {
		Path file = Paths.get(path);
		Files.write(file, content.getBytes());
	}

}
