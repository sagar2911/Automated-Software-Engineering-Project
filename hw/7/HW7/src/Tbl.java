import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.platform.commons.util.StringUtils;

public class Tbl {

	public List<Col> cols = new ArrayList<>();
	public List<Row> rows = new ArrayList<>();
	private List<Cell> head = new ArrayList<>();

	Set<Integer> skipCols = new HashSet<>(); // track skipped columns

	private StringBuilder tsb = new StringBuilder(); // visual for Part2

	public List<Integer> myGoals = new ArrayList<>();
	private List<Integer> myLesses = new ArrayList<>();
	List<Integer> myNums = new ArrayList<>();
	List<Integer> mySyms = new ArrayList<>();
	List<Integer> myXs = new ArrayList<>();
	private List<Integer> myXnums = new ArrayList<>();
	private List<Integer> myXsyms = new ArrayList<>();

	public Tbl() {
	}

	public Tbl(List<Cell> head) {
		addRow(1, head);
	}

	public Tbl(String csv) {
		List<List<Cell>> table = new CSVParser(csv).getParsedTable();
		int r = 0;
		for (List<Cell> row : table) {
			r++;
			addRow(r, row);
		}
	}
	
	public void addRow(int r, List<Cell> row) {
		if (isLineBlank(row)) {
			return;
		}
		if (r == 1) {
			parseCols(row);
		} else {
			parseRow(r, row);
		}
	}

	private static boolean isLineBlank(List<Cell> row) {
		return row.isEmpty() || StringUtils.isBlank(row.get(0).getStringValue());
	}

	private void parseCols(List<Cell> cells) {
		head = new ArrayList<>();
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
			head.add(cell);
		}
		tsb.append(U.toString(cols)).append("\n");
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

	private void parseRow(int r, List<Cell> cells) {
		Row row = new Row(cells, skipCols);
		if (row.size() != cols.size()) {
			tsb.append("E> skipping line " + r).append("\n");
			return;
		}
		for (int i = 0; i < row.size(); i++) {
			Col col = cols.get(i);
			Cell cell = row.get(i);

			switch (col.getType()) {
			case Num:
				((Num) col).addNum(cell.getDoubleValue());
				break;
			case Sym:
				((Sym) col).addSym(cell.getStringValue());
				break;
			default:
				break;
			}
		}
		rows.add(row);
		tsb.append(row).append("\n");
	}

	public String toString() {
		return tsb.toString();
	}

	public void removeFirstRow(List<Cell> cells) {
		Row row = new Row(cells, skipCols);
		if (row.size() != cols.size()) {
			tsb.append("E> skipping line ").append("\n");
			return;
		}
		for (int i = 0; i < row.size(); i++) {
			Col col = cols.get(i);
			Cell cell = row.get(i);

			switch (col.getType()) {
			case Num:
				((Num) col).deleteNum(cell.getDoubleValue());
				break;
			case Sym:
				((Sym) col).deleteSym(cell.getStringValue());
				break;
			default:
				break;
			}
		}
		rows.remove(0);
		tsb.append(row).append("removed \n");
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
	
	public int size() {
		return rows.size();
	}

	public String tree() {
		StringBuilder sb = new StringBuilder();
		int min = (int) Math.sqrt(size());
		Split split = new Split(this, 0);
		split.kids = dt(min, 1);
		sb.append(split).append(baseline()).append("\n");
//		System.out.print(sb.toString());
		return sb.toString();
	}
	
	public String baseline() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cols.size(); i++) {
			Col col = cols.get(i);
			if (!myGoals.contains(col.col)) {
				continue;
			}
			Num s = (Num) col;
			sb.append(String.format("%s = %s (%s)", s.txt, U.d(s.mean), U.d(s.sd)));
			if (i != cols.size() - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	private List<Split> dt(int min, int lvl) {
		List<Split> splits = split(min, lvl);

		if (splits == null) {
			return null;
		}

		for (Split s : splits) {
			s.kids = s.tbl.dt(min, lvl + 1);
		}

		return splits;
	}
	
	private List<Split> split(int min, int lvl) {
		Div div = new Div(this);

		Div.Res pivots = div.findGoodPivots();

		if (pivots == null) {
			return null;
		}

		Row x = pivots.x;
		Row y = pivots.y;
		double c = pivots.c;

		List<Double> cs = new ArrayList<>();
		
		for (Row z : rows) {
			double co = div.cos(x, y, z, c);
			cs.add(co);
		}
		
		Collections.sort(cs);
		
		double median = cs.get(cs.size() / 2);
		
		Tbl left = new Tbl(head);
		Tbl right = new Tbl(head);

		for (Row z : rows) {
			double co = div.cos(x, y, z, c);
			if (co < median) {
				left.addRow(left.size() + 2, z.cells);
			} else {
				right.addRow(right.size() + 2, z.cells);
			}
		}
		
		if (left.size() < min || right.size() < min) {
			return null;
		}

		Split split1 = new Split(left, lvl);
		Split split2 = new Split(right, lvl);
		return Arrays.asList(split1, split2);//*/
	}
	
	class Split {
		Tbl tbl;
		int lvl;
		List<Split> kids;

		public Split(Tbl tbl, int lvl) {
			this.tbl = tbl;
			this.lvl = lvl;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			String tab = U.tab(lvl);
			int n = tbl.size();
			String baseline = tbl.baseline();

			sb.append(tab).append(n).append("\n");

			if (kids == null) {
				sb.append(tab).append(" ").append(baseline).append("\n");
			} else {
				for (Split s : kids) {
					sb.append(s);
				}
			}
			return sb.toString();
		}
	}

}
