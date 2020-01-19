import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	private static final String OUTPUT_1_FILENAME = "outpart1.txt";
	private static final String OUTPUT_2_FILENAME = "outpart2.txt";
	private static final String OUTPUT_3_FILENAME = "outpart3.txt";

	public static void main(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("Error: provide input file name!");
		}
		String filePath = args[0];
		try {
			Tbl tbl = new Tbl(new String(Files.readAllBytes(Paths.get(filePath))));

			String visual1 = tbl.getVisual1();
			String visual2 = tbl.getVisual2();
			String dump = tbl.dump();

			writeVisual1(visual1);
			writeVisual2(visual2);
			writeDump(dump);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeVisual1(String visual1) throws IOException {
		Path file = Paths.get(OUTPUT_1_FILENAME);
		Files.write(file, visual1.getBytes());
	}

	private static void writeVisual2(String visual2) throws IOException {
		Path file = Paths.get(OUTPUT_2_FILENAME);
		Files.write(file, visual2.getBytes());
	}

	private static void writeDump(String dump) throws IOException {
		Path file = Paths.get(OUTPUT_3_FILENAME);
		Files.write(file, dump.getBytes());
	}

}
