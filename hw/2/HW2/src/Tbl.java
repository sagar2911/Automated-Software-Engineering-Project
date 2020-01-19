import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.platform.commons.util.StringUtils;

public class Tbl {

	private List<Num> cols = new ArrayList<>();
	private List<Row> rows = new ArrayList<>();
	private Set<Integer> skipCols = new HashSet<>(); // track skipped columns

	private StringBuilder visual1 = new StringBuilder(); // visual for Part1
	private StringBuilder visual2 = new StringBuilder(); // visual for Part2
	private StringBuilder visual3 = new StringBuilder(); // visual for Part3
	
	public Tbl(String csv) {
		List<List<Cell>> parsedCSV = parseCSV(csv);
		initTable(parsedCSV);
	}

	private List<List<Cell>> parseCSV(String csv) {
		List<List<Cell>> parsedCSV = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new StringReader(csv))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				List<Cell> cells = new ArrayList<>();
				for (String value : values) {
					cells.add(new Cell(value));
				}
				parsedCSV.add(cells);
				visual1.append(Utility.toString(cells)).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parsedCSV;
	}

	private void initTable(List<List<Cell>> csv) {
		int lineCount = 0;
		for (List<Cell> cells : csv) {
			lineCount++;
			if (isLineBlank(cells)) {
				continue;
			}
			if (cols.isEmpty()) {
				parseCols(cells);
			} else {
				parseRow(cells, lineCount);
			}
		}
	}

	private static boolean isLineBlank(List<Cell> cells) {
		return cells.isEmpty() || StringUtils.isBlank(cells.get(0).getStringValue());
	}

	private void parseCols(List<Cell> cells) {
		for (int i = 0; i < cells.size(); i++) {
			String txt = cells.get(i).getStringValue();
			if (txt.contains("?")) {
				skipCols.add(i);
			} else {
				cols.add(new Num(i + 1, txt));
			}
		}
		visual2.append(Utility.toString(cols)).append("\n");
	}

	private void parseRow(List<Cell> cells, int lineCount) {
		Row row = new Row(cells, skipCols);
		if (row.size() != cols.size()) {
			visual2.append("E> skipping line " + lineCount).append("\n");
			return;
		}
		List<Cell> r = row.cells;
		for(int i = 0; i < r.size(); i++)
		{
			if(r.get(i).isInt()) {
				cols.get(i).addNum(r.get(i).getIntValue());
			}
		}
		rows.add(row);
		visual2.append(row).append("\n");
	}

	public String getVisual1() {
		return visual1.toString();
	}

	public String getVisual2() {
		return visual2.toString();
	}

	public String dump() {
		visual3.append("t.cols\n");
		int k=1;
		for (Num col : cols) {
			visual3.append("|\t" + col.col + "\n");
			visual3.append(col.dump());
		}
		
		visual3.append("t.rows\n");
		for(int i = 0; i < rows.size(); i++)
		{

			int l=i+1;
			visual3.append("|\t"+l+"\n");
			visual3.append("|\t|\tcells\n");
			
			for(int j=0;j<rows.get(i).size();j++)
			{
				List<Cell> r= rows.get(i).cells;
				int m=j+1;
				visual3.append("|\t|\t|\t"+m+":\t"+r.get(j).getIntValue()+"\n");
			}
			visual3.append("|\t|\tcooked\n");
			visual3.append("|\t|\tdom:\t0\n");

			
		}
		return visual3.toString();
	}

}
