import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	private static final int NUM_COL_CHARS = 20;

	public static void main(String[] args) {
		if (args.length != 4) {
			throw new IllegalArgumentException("Error! example format java -jar hw9.jar xomo10000.csv incremental 0.5 20");
		}

		String in = args[0];
		String type = args[1];
		double alpha = Double.parseDouble(args[2]);
		int numRepeats = Integer.parseInt(args[3]);

		if (!type.equals("all") && !type.equals("incremental")) {
			throw new IllegalArgumentException("Error! incorrect type " + type + " supported types: all, incremental");
		}
		boolean incremental = type.equals("incremental");

		String name = in.substring(0, in.indexOf(".csv"));
		String out = name + "-" + type + "-[alpha=" + alpha + "].out.txt";

		compute(in, out, incremental, alpha, numRepeats);
	}

	private static void compute(String in, String out, boolean incremental, double alpha, int numRepeats) {
		StringBuilder sb = new StringBuilder();

		sb.append("Data file: " + in);
		sb.append((incremental ? "Incremental" : "All") + " Tree: ");
		sb.append("\n\n");
		sb.append(String.format("%" + NUM_COL_CHARS + "s", "Score"));
		sb.append("\n");

		sb.append(String.format("%" + NUM_COL_CHARS + "s", "").replace(' ', '-'));
		sb.append("\n");

		Report report = new Report();

		double score = report.reportTree(in, incremental, alpha, numRepeats);

		sb.append(String.format("%" + NUM_COL_CHARS + "s", score));
		sb.append("\n");

		write(sb.toString(), out);
	}

	private static void write(String content, String path) {
		Path file = Paths.get(path);
		try {
			Files.write(file, content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
