import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	private static final String OUTPUT = "out.txt";

	public static void main(String[] args) {
		if (args.length != 4) {
			throw new IllegalArgumentException("Error! example format java -jar hw5.jar num.csv 1 2 Num");
		}
		try {
			Path path = Paths.get(args[0]);
			int first = Integer.parseInt(args[1]) - 1;
			int second = Integer.parseInt(args[2]) - 1;
			ColType type = ColType.valueOf(args[3]);

			String csv = new String(Files.readAllBytes(path));

			Tbl tbl = new Tbl(csv);
			Div2 div2 = new Div2(tbl, first, second, type);
			
			write(div2.getRange());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write(String data) throws IOException {
		Path file = Paths.get(OUTPUT);
		Files.write(file, data.getBytes());
	}

}
