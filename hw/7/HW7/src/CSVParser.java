import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
	
	private List<List<Cell>> table;
	
	public CSVParser(String csv) {
		table = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new StringReader(csv))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				List<Cell> row = new ArrayList<>();
				for (String value : values) {
					row.add(new Cell(parseValue(value)));
				}
				table.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<List<Cell>> getParsedTable() {
		return table;
	}
	
	private static String parseValue(String value) {
		int offset = value.indexOf("#"); // remove comments
		if (offset != -1) {
		    value = value.substring(0, offset);
		}
		value = value.trim(); // remove trailing whitespace
		return value;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (List<Cell> row : table) {
			sb.append(row).append("\n");
		}
		return sb.toString();
	}

}
