import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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

	private List<Num> goals = new ArrayList<>();
	private List<Col> indepCols = new ArrayList<>();

	private static final double p = 2;

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

			if (metadata.isLess) {
				col.w = -1;
			}

			if (metadata.isGoal) {
				goals.add((Num) col);
			} else {
				indepCols.add(col);
			}
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

	public String sort() {
		StringBuilder sb = new StringBuilder();
		Random r = new Random();

		for (int i = 0; i < rows.size(); i++) {
			Row ri = rows.get(i);

			int j = -1;
			Set<Integer> selected = new HashSet<>();
			while (selected.size() != 100) {
				j = r.nextInt(rows.size() - 1);
				if (selected.contains(j) || j == i) {
					continue;
				}
				selected.add(j);
			}
			for (int s : selected) {
				Row rj = rows.get(s);
				if (Domination.dominates(ri, rj, goals)) {
					ri.count++;
				}
				;
			}
		}

		Collections.sort(rows);

		sb.append(cols.toString()).append("\n").append("\n");

		for (int i = 0; i < 5; i++) {
			Row row = rows.get(i);
			sb.append(row.toString()).append("\n\n");
		}
		sb.append("\n\n...\n\n");
		for (int i = rows.size() - 5; i < rows.size(); i++) {
			Row row = rows.get(i);
			sb.append(row.toString()).append("\n\n");
		}

		return sb.toString();
	}

	public String tree() {
		StringBuilder sb = new StringBuilder();
		int min = (int) Math.sqrt(size());

		List<Split> leaves = new ArrayList<>();

		Split split = new Split(this, 0);
		split.kids = dt(min, 1, leaves);

//		sb.append(split).append(baseline()).append("\n");

//		System.out.print(sb.toString());
//		System.out.println("---------");

//		Split s1 = leaves.get(0);
//		Split s2 = leaves.get(1);
//		System.out.println(s1.centroidIndepRow);
//		System.out.println(s2.centroidIndepRow);
//		System.out.println(s1.centroidRow);
//		System.out.println(s2.centroidRow);
		
		computeSigmas(leaves);

		for (Split l : leaves) {
			computeEnvy(l, leaves);
//			System.out.println(l);
//			System.out.println(l.envious);
//			System.out.println("----------------------");
//			
			Tbl dtTbl = l.tbl;
			if(l.envious!=null) {
			for(Row r : l.envious.tbl.rows) {
					List<Cell> row = r.cells;
					dtTbl.addRow(l.tbl.rows.size(), row);
				}
			String re = dtTbl.tree(true);
			sb.append(re).append("\n\n");
//			System.out.println(re);
			}
		}
		
		return sb.toString();
	}

	public String tree( Boolean b ) {
		StringBuilder sb = new StringBuilder();
		int min = (int) Math.sqrt(size());

		List<Split> leaves = new ArrayList<>();

		Split split = new Split(this, 0);
		split.kids = dt(min, 1, leaves);

		sb.append(split).append(baseline()).append("\n");
		
		return sb.toString();
	}
	
	private void computeSigmas(List<Split> leaves) {
		
		for (Split s1 : leaves) {
			for (Split s2 : leaves) {
				if (s1 == s2) {
					continue;
				}
				int dominates = Domination.countDominates(s1.centroid, s2.centroid, goals);

				if(dominates > s1.domhigh) s1.domhigh = dominates;
				if(dominates < s1.domlow) s1.domlow = dominates;
			}
			s1.countDominations = s1.domhigh-s1.domlow;
		//	System.out.print("countdominations : "+s1+"res :"+s1.countDominations);
		}
		
	}

	private void computeEnvy(Split cur, List<Split> leaves) {
		double maxEnvy = Integer.MIN_VALUE;

		
//		System.out.println("sigma: " + U.d(cur.sigma));
		for (Split l : leaves) {
			if (l == cur) {
				continue;
			}
			int dominates = Domination.countDominates(l.centroid, cur.centroid, goals);

			
			double delta = computeDist(cur, l);
			if(dominates<0) continue;
			double sigma = ((double)dominates-cur.domlow)/(double)cur.countDominations;
//
//			System.out.println("num: " + U.d((double)dominates-cur.domlow));
//			System.out.println("den: " + U.d((double)cur.countDominations));
			double envy = 1 - delta - sigma;
			if (envy > maxEnvy) {
//
//				System.out.println("sigma: " + U.d(sigma));
//				System.out.println("delta: " + U.d(delta));
//
//				System.out.println("envy: " + U.d(envy));				
				maxEnvy = envy;
				cur.envious = l;
			}
		}
	}

	public double computeDist(Split l1, Split l2) {
		double d = 0;
		int n = 0;
		for (Col c : indepCols) {
			double d0 = c.dist(l1.centroidIndepRow.get(n), l2.centroidIndepRow.get(n));
			d += Math.pow(d0, p);
			n++;
		}
		return Math.pow(d, 1 / p) / Math.pow(n, 1 / p);
	}

	private List<Cell> centroidIndepRow() {
		List<Cell> res = new ArrayList<>();
		for (Col col : indepCols) {
			if (col.getType() == ColType.Num) {
				Num s = (Num) col;
				res.add(new Cell(s.mean + ""));
			} else {
				Sym s = (Sym) col;
				res.add(new Cell(s.getMode()));
			}
		}
		return res;
	}
	
	private List<Cell> centroidRow() {
		List<Cell> res = new ArrayList<>();
		for (Col col : cols) {
			if (col.getType() == ColType.Num) {
				Num s = (Num) col;
				res.add(new Cell(s.mean + ""));
			} else {
				Sym s = (Sym) col;
				res.add(new Cell(s.getMode()));
			}
		}
		return res;
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

	
	private List<Split> dt(int min, int lvl, List<Split> leaves) {
		List<Split> splits = split(min, lvl);

		if (splits == null) {
			return null;
		}

		for (Split s : splits) {
			s.kids = s.tbl.dt(min, lvl + 1, leaves);

			if (s.kids == null) {
				leaves.add(s);
			}
			// calculate delta between independent variables of C1 and C2 using Div.distance
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
		return Arrays.asList(split1, split2);// */
	}

	class Split {
		Tbl tbl;
		int lvl;
		List<Split> kids;
		List<Cell> centroidIndepRow;
		List<Cell> centroidRow;
		Row centroid;
		double countDominations;
		int domhigh = Integer.MIN_VALUE;
		int domlow = Integer.MAX_VALUE;
		double sigma;
		
		Split envious;

		public Split(Tbl tbl, int lvl) {
			this.tbl = tbl;
			this.lvl = lvl;
			centroidIndepRow = tbl.centroidIndepRow(); 
			centroidRow = tbl.centroidRow();
			centroid = new Row(centroidRow);
			countDominations = 0;
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
