import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.platform.commons.util.StringUtils;

public class Tbl {

	private List<Col> cols = new ArrayList<>();
	private List<Row> rows = new ArrayList<>();
	private Set<Integer> skipCols = new HashSet<>(); // track skipped columns

	private StringBuilder visual1 = new StringBuilder(); // visual for Part1
	private StringBuilder visual2 = new StringBuilder(); // visual for Part2

	private List<Integer> myGoals = new ArrayList<>();
	private List<Integer> myLesses = new ArrayList<>();
	private List<Integer> myNums = new ArrayList<>();
	private List<Integer> mySyms = new ArrayList<>();
	private List<Integer> myXs = new ArrayList<>();
	private List<Integer> myXnums = new ArrayList<>();
	private List<Integer> myXsyms = new ArrayList<>();

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
			Cell cell = cells.get(i);
			String name = cell.getStringValue();
			int index = i + 1;

			ColMetadata metadata = ColParser.parse(name);

			if (metadata.isSkip) {
				skipCols.add(i);
				continue;
			}

			populateMy(metadata, index);

			Col col = metadata.isNum ? new Num(index, name) : new Sym(index, name);
			cols.add(col);
		}
		visual2.append(Utility.toString(cols)).append("\n");
	}

	private void populateMy(ColMetadata metadata, int index) {
		if (metadata.isNum) {
			myNums.add(index);
		} else {
			mySyms.add(index);
		}
		if (metadata.isGoal) {
			myGoals.add(index);
		} else {
			myXs.add(index);
			if (metadata.isNum) {
				myXnums.add(index);
			} else {
				myXsyms.add(index);
			}
		}
		if (metadata.isLess) {
			myLesses.add(index);
		}
	}

	private void parseRow(List<Cell> cells, int lineCount) {
		Row row = new Row(cells, skipCols);
		if (row.size() != cols.size()) {
			visual2.append("E> skipping line " + lineCount).append("\n");
			return;
		}
		List<Cell> r = row.cells;
		for (int i = 0; i < r.size(); i++) {
			Col col = cols.get(i);
			Cell cell = r.get(i);

			switch (col.getType()) {
			case Num:
				((Num) col).addNum(cell.getIntValue());
				break;
			case Sym:
				((Sym) col).addSym(cell.getStringValue());
				break;
			default:
				break;
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
		StringBuilder dump = new StringBuilder();
		dump.append("t.cols\n");

		for (Col col : cols) {
			dump.append("|\t" + col.col + "\n");
			dump.append(col.dump());
		}

		dump.append("t.my\n");
		dump.append("|\tclass: " + (cols.size() + skipCols.size()) + "\n");

		dump.append("|\tgoals\n");
		for (int i : myGoals) {
			dump.append("|\t|\t" + i + "\n");
		}

		dump.append("|\tnums\n");
		for (int i : myNums) {
			dump.append("|\t|\t" + i + "\n");
		}

		dump.append("|\tsyms\n");
		for (int i : mySyms) {
			dump.append("|\t|\t" + i + "\n");
		}

		dump.append("|\tw\n");
		for (int i : myLesses) {
			dump.append("|\t|\t" + i + ": -1\n");
		}
		
		dump.append("|\txnums\n");
		for (int i : myXnums) {
			dump.append("|\t|\t" + i + "\n");
		}

		dump.append("|\txs\n");
		for (int i : myXs) {
			dump.append("|\t|\t" + i + "\n");
		}

		dump.append("|\txsyms\n");
		for (int i : myXsyms) {
			dump.append("|\t|\t" + i + "\n");
		}

		return dump.toString();
	}

}
