import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.platform.commons.util.StringUtils;

public class Tbl {

	// -------------------- miscellaneous --------------------

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

	public String toString() {
		return tsb.toString();
	}

	// -------------------- end --------------------

	public List<Col> cols = new ArrayList<>();
	public List<Row> rows = new ArrayList<>();
	private List<Cell> head = new ArrayList<>();

	private Set<Tbl> leaves = new HashSet<>();
	Set<Integer> skipCols = new HashSet<>(); // track skipped columns

	private StringBuilder tsb = new StringBuilder(); // visual for Part2

	public List<Integer> myGoals = new ArrayList<>();
	private List<Integer> myLesses = new ArrayList<>();
	List<Integer> myNums = new ArrayList<>();
	List<Integer> mySyms = new ArrayList<>();
	List<Integer> myXs = new ArrayList<>();
	private List<Integer> myXnums = new ArrayList<>();
	private List<Integer> myXsyms = new ArrayList<>();

	private Map<Row, Tbl> rowToCluster = new HashMap<>();


	public int lvl;
	public List<Tbl> kids;
	private Tbl parent = null;
	
	public Tbl() {
	}

	public Tbl(List<Cell> head, int lvl) {
		addRow(1, head);
		this.lvl = lvl;
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

	private void addRow(Row row) {
		rows.add(row);
		updateCol(row);
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

	private void updateCol(Row row) {
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
	}

	private void parseRow(int r, List<Cell> cells) {
		Row row = new Row(cells, skipCols);
		if (row.size() != cols.size()) {
			tsb.append("E> skipping line " + r).append("\n");
			return;
		}
		tsb.append(row).append("\n");
		addRow(row);
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

	public int size() {
		return rows.size();
	}

	public Tbl cluster(Row row) {
		return rowToCluster.get(row);
	}
	
	public Tbl computeTree(boolean incremental, double alpha) {
		if (incremental) {
			return computeIncrementalTree(alpha);
		} else {
			return computeAllTree();
		}
	}
	
	private Tbl computeAllTree() {
		int min = (int) Math.sqrt(size());
		lvl = 0;
		kids = dt(min, 1,this.leaves);
		return this;
	}
	
	private Tbl computeIncrementalTree(double alpha) {
		Tbl tbl = new Tbl(head, 0);
		
		for (int i = 0; i < 500; i++) {
			tbl.addRow(rows.get(i));
		}

		Tbl tree = tbl.computeAllTree();
		Div div = new Div(this);

		Div.Res pivots = div.findGoodPivots();
		for (int i = 500; i < size(); i++) {
			int min = (int) Math.sqrt(tbl.size());
			Row z = rows.get(i);
			double minDist=Integer.MAX_VALUE;

			Tbl leaf = null;
			if(findIfAnamolous(z,div,pivots, alpha)) {
				for(Tbl q : tree.leaves)
				{
					double dist = div.dist(q.centroidRow(),z );
					if(dist<minDist)
					{
						minDist=dist;
						leaf= q;
					}

				}
				for(Tbl t =leaf;t!=null;t=t.parent) {
					
					t.addRow(z);
					rowToCluster.put(z, t);

					
				}
				leaf.kids = leaf.dt(min,leaf.lvl+1,leaves);
			}
			
		}
		


		return tree;
	}
	
	private Boolean findIfAnamolous(Row z, Div div,Div.Res pivots, double alpha)
	{
		
		if (pivots == null) {
			return false;
		}

		double c = div.cos(pivots.x, pivots.y, z, pivots.c);
		double a = div.dist(pivots.x, z);
		double b = div.dist(pivots.y, z);
		double x = (a * a + c * c - b * b) / (2 * c);
		double far=0;
		double s = getMedian(pivots.x, pivots.y, pivots.c, div);
		if(s<0.5)
		 {
			far = s * alpha;
			if(x<far)  return true;
			else return false;
		 }
		else
		 {
			far =s + (1-s)*alpha; 
			if(x<far)  return false;
			else return true;
		 }
		
	}

	private Row centroidRow() {
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
		return new Row(res,skipCols);
	}
	
	private List<Tbl> dt(int min, int lvl,Set<Tbl> leaves) {
		List<Tbl> splits = split(min, lvl,leaves);
		if (splits == null) {
			return null;
		}
		for (Tbl s : splits) {
			s.kids = s.dt(min, lvl + 1,leaves);
		}
		return splits;
	}

	private List<Tbl> split(int min, int lvl, Set<Tbl> leaves) {
		Div div = new Div(this);

		Div.Res pivots = div.findGoodPivots();

		if (pivots == null) {
			return null;
		}

		Row x = pivots.x;
		Row y = pivots.y;
		double c = pivots.c;

		double median = getMedian(x, y, c, div);

		Tbl left = new Tbl(head, lvl);
		Tbl right = new Tbl(head, lvl);
		
		left.parent=this;
		right.parent=this; 
		
		for (Row z : rows) {
			double co = div.cos(x, y, z, c);
			if (co < median) {
				left.addRow(z);
				rowToCluster.put(z, left);
			} else {
				right.addRow(z);
				rowToCluster.put(z, right);
			}
		}

		if (left.size() < min || right.size() < min) {
			return null;
		}
		else {

			leaves.remove(this);
			leaves.add(left);
			leaves.add(right);
		}
		
		return Arrays.asList(left, right);
	}
	
	private double getMedian(Row x, Row y, double c, Div div) {
		List<Double> cs = new ArrayList<>();

		for (Row z : rows) {
			double co = div.cos(x, y, z, c);
			cs.add(co);
		}

		Collections.sort(cs);

		double median = cs.get(cs.size() / 2);		
		
		return median;
	}
	
	public int sameCount(Tbl other) {
		int sameCount = 0;
		for (int i = 0; i < cols.size(); i++) {
			Col col1 = cols.get(i);
			Col col2 = other.cols.get(i);

			if (!myGoals.contains(col1.col)) {
				continue;
			}

			Num num1 = (Num) col1;
			Num num2 = (Num) col2;

			if (num1.same(num2)) {
				sameCount++;
			}
		}
		return sameCount;
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

}
