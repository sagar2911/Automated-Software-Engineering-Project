import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	private static final String OUTPUT = "out.txt";

	public static void main(String[] args) {
		if (args.length != 3) {
			throw new IllegalArgumentException("Error! example format java -jar hw4.jar diabetes.csv 3 4");
		}
		try {
			Path path = Paths.get(args[0]);
			int nZeroR = Integer.parseInt(args[1]);
			int nNb = Integer.parseInt(args[2]);

			String csv = new String(Files.readAllBytes(path));
			
			StringBuilder sb = new StringBuilder();

			sb.append("#--- zerorok -----------------------\n\n");
			Abcds abcds = new Abcds(new ZeroR());
			String report = abcds.run(csv, nZeroR);
			sb.append(report).append("\n");

			sb.append("#--- nbok -----------------------\n\n");
			abcds = new Abcds(new Nb());
			report = abcds.run(csv, nNb);
			sb.append(report).append("\n");

			write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void write(String data) throws IOException {
		Path file = Paths.get(OUTPUT);
		Files.write(file, data.getBytes());
	}

}
