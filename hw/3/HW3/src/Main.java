import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	private static final String OUTPUT = "out.txt";

	public static void main(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("Error: provide input file name!");
		}
		String filePath = args[0];
		try {
			Tbl tbl = new Tbl(new String(Files.readAllBytes(Paths.get(filePath))));

			String dump = tbl.dump();

			writeDump(dump);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeDump(String dump) throws IOException {
		Path file = Paths.get(OUTPUT);
		Files.write(file, dump.getBytes());
	}

}
