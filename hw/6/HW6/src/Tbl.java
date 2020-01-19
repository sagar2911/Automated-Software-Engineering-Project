import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.platform.commons.util.StringUtils;

public class Tbl {

	private static final int MIN_OBS = 4;

	public List<Col> cols = new ArrayList<>();
	public List<Row> rows = new ArrayList<>();
	private List<Cell> head = new ArrayList<>();

	private Set<Integer> skipCols = new HashSet<>(); // track skipped columns

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
		tsb.append(U.toString(cols)).append("\n");
		head = cells;
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

	public int count(int c, String v) {
		int count = 0;
		for (Row row : rows) {
			Cell cell = row.get(c);
			if (cell.getStringValue().equals(v)) {
				count++;
			}
		}
		return count;
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

	public String decisionTree() {
		return tree(ColType.Sym);
	}

	public String regressionTree() {
		return tree(ColType.Num);
	}

	private String tree(ColType yis) {
		StringBuilder sb = new StringBuilder();
		List<Split> splits = dt(yis, 0);
		for (Split split : splits) {
			sb.append(split.toString());
		}
//		System.out.print(sb.toString());
		return sb.toString();
	}

	private List<Split> dt(ColType yis, int lvl) {
		if (rows.size() >= MIN_OBS * 2) {
		Col col = null;
		double gain = -1;
		List<Double> cut=new ArrayList<>();

		List<String> mm1=new ArrayList<>();
		

		for (Col col1 : cols) {

			List<String> mm=new ArrayList<>();
			
			List<Double> cut1=new ArrayList<>();
			if (myGoals.contains(col1.col)) {
				continue;
			}
			int goalIndex = myGoals.get(0) - 1;
			int colIndex = col1.col - 1;


			Div2 div2 = new Div2(this, colIndex, goalIndex, yis); // todo pass Sym ?

			if (div2.rangesNum.size() == 1) {
				continue;
			}

			double gain1 = div2.gain;

			// todo col1 == Sym
			if(yis==ColType.Num) {
				for(int i=0;i<div2.rangesNum.size();i++)
				{
					mm.add(String.valueOf(((Num) div2.rangesNum.get(i).get(0)).getMean()));
				}
			}
			else {
				for(int i=0;i<div2.rangesNum.size();i++)
				{
					mm.add(((Sym) div2.rangesNum.get(i).get(0)).getMode());
				}
			}
			for(int i=0;i<div2.rangesNum.size();i++)
			{
				cut1.add(((Num) div2.rangesNum.get(i).get(1)).high);
			}

//			System.out.println(U.tab(lvl) + col1.txt + " cut1: " + cut1 + " gain1: " + gain1 + " " + (gain1 > gain));
		
			if (gain1 > gain) {
				col = col1;
				gain = gain1;
				cut = cut1;
				mm1=mm;
			}
		}
		if (cut != null) {
			List<Split> splits = split(cut, col, lvl, yis,mm1);

			if (splits == null) {
				return null;
			}

			for (Split s : splits) {
				s.kids = s.tbl.dt(yis, lvl + 1);
			}

			return splits;
		}
		}
		return null;
	}
	
	private List<Split> split(List<Double> cut, Col col, int lvl, ColType yis,List<String> mm) {
		List<Tbl> tbls = new ArrayList<>();
		List<Row> rowList =rows;
		int maxsize=-1;
		int maxIndex=-1;
		int p=0;
		int x;

		double prev = Integer.MIN_VALUE;
		for(Double c: cut) {
		Tbl temp =new Tbl(head);
		for (int j=0;j<rowList.size();j++) {
				
				List<Cell> row = rowList.get(j).getCells();
				Cell cell = row.get(col.col - 1);
				double v = cell.getDoubleValue();

				if (v <= c && v > prev ) {
					temp.addRow(temp.size() + 2, row);
				} 
			}
			if(temp.rows.size()< (MIN_OBS * 2) )
				continue;
			prev=c;
			tbls.add(temp);
			if(temp.rows.size()>maxsize) {maxIndex=p; maxsize=temp.rows.size();}
			
			p++;
		}
		List<Split> splits=new ArrayList<>();
		for(int k=0;k<tbls.size();k++)
		{
			if(tbls.size()==1)
				return null;
			try {
	 		Split split1 = new Split(lvl, k==tbls.size()-1? cut.get(k-1)+"...inf" : (k==0?"-inf..."+cut.get(0):String.valueOf(cut.get(k-1)) +"..."+ cut.get(k)), col.txt, tbls.get(k).rows.size() == maxsize, tbls.get(k), yis,mm.get(k));
			
	 		splits.add(split1);
			}
			catch(Exception ex)
			{
				System.out.print("");
			}
		}



		return splits;
	}

	
	class Split {
		int lvl;
		String range, txt, most, goal;
		Tbl tbl;
		List<Split> kids;

		public Split(int lvl, String range, String txt, boolean most, Tbl tbl, ColType yis,String mm) {
			this.lvl = lvl;
			this.range = range;
			this.txt = txt;
			this.most = most ? "*" : "";
			
			Col gcol = tbl.cols.get(myGoals.get(0) - 1);
			if (yis == ColType.Sym) {
				goal = ((Sym) gcol).getMode();
			} else {
				goal = U.d(((Num) gcol).mean);
			}

			this.tbl = tbl;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			String tab = U.tab(lvl);
			sb.append(String.format("%s%s = %s %s", tab, txt, range, most));

			if (kids == null) {
				sb.append(String.format(" : %s (%d)\n", goal, tbl.rows.size()));
			} else {
				sb.append("\n");
				for (Split s : kids) {
					sb.append(s);
				}
			}
			return sb.toString();
		}
	}

}
