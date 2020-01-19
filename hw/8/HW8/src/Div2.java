import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Div2 {

	public double gain, epsilon;
	private String start, stop;
	private int step;
	private static double MIN = 0.5, COHEN = 0.3, TRIVIAL = 1.025;
	public List<List<Col>> rangesNum = new ArrayList<>();
	private Boolean isFirstCut;
	private ColType type;
	
	class CellComparator implements Comparator<List<Cell>> {
		public int compare(List<Cell> s1, List<Cell> s2) {

			if (s1.get(1).getDoubleValue() < s2.get(1).getDoubleValue())
				return -1;
			else if (s1.get(1).getDoubleValue() > s2.get(1).getDoubleValue())
				return 1;
			return 0;
		}

	}
//	public class pq{
//		public PriorityQueue<List<Cell>> regular =new PriorityQueue<>(new CellComparator(true));
//		public PriorityQueue<List<Cell>> inverted = new PriorityQueue<>(new CellComparator(false));
//		public class range{
//			public List<Cell> high,  low;
//		}
//		
//		public void Add(List<Cell> c) {
//			regular.add(c);
//			inverted.add(c);
//		}
//		public range Poll() {
//			range r = new range();
//			r.low = regular.poll();
//			r.high = inverted.poll();
//			return r;
//		}
//		
//	}

	public Div2(Tbl num, int i, int j, ColType type) {
		this.type = type;
		List<List<Cell>> c = new ArrayList<>();
		ColType t = num.cols.get(j).getType();
		for (int k = 0; k < num.rows.size(); k++) {
			List<Cell> temp = new ArrayList<>();
			Cell a =num.rows.get(k).get(j);
			Cell b =num.rows.get(k).get(i);
			if(a.getStringValue().equals("?") || b.getStringValue().equals("?")) continue;
			temp.add(a);
			temp.add(b);
			for(int q =0; q<num.rows.get(k).size() ; q++)
			{
				if(q!=i && q!=j)
					temp.add(num.rows.get(k).get(q));
			}
			c.add(temp);

		}
		Collections.sort(c, new CellComparator());
		gain = 0.0;
		step = (int) Math.pow(c.size(), MIN);
		start = c.get(0).get(1).getStringValue();
		stop = c.get(c.size() - 1).get(1).getStringValue();
		if (t.toString() == "Num")
			epsilon = ((Num) num.cols.get(j)).getSd() * COHEN;
		else
			epsilon = ((Sym) num.cols.get(j)).getEntropy() * COHEN;
		isFirstCut=true;
		divide(c, num.cols.get(j), 0, c.size(), 1, t.toString());
		gain /= c.size();
	}

	private int divide(List<List<Cell>> c, Col num, int low, int high, int rank, String t) {
		Tbl leftList = new Tbl();
		Tbl rightList = new Tbl();
		Tbl tblList = new Tbl();
		List<Cell> headers = new ArrayList<>();
		if (t == "Num") {
			headers.add(new Cell("$y"));
			headers.add(new Cell("$x"));
		} else {
			headers.add(new Cell("y"));
			headers.add(new Cell("$x"));
		}
		for(int p=0;p<c.get(0).size()-2;p++)
		{
			headers.add(new Cell("$z"+p));
		}
		leftList.addRow(1, headers);
		rightList.addRow(1, headers);
		tblList.addRow(1, headers);
		for (int q = low; q < high; q++) {
			rightList.addRow(q + 2, c.get(q));
			tblList.addRow(q + 2, c.get(q));
		}

		double best;

		if (t == "Num")
			best = ((Num) num).getSd();
		else
			best = ((Sym) num).getEntropy();

		int cut = -1;
		for (int j = low; j < high; j++) {
			leftList.addRow(j + 2, c.get(j));
			List<Cell> ab = c.get(j);
			rightList.removeFirstRow(ab);

//			if(t == "Sym" && Double.isNaN(((Sym)rightList.cols.get(0)).getEntropy())) {
//				((Sym)rightList.cols.get(0)).setEntropy();
//				
//			}
			if (leftList.rows.size() >= step && rightList.rows.size() >= step) {
				Cell now = c.get(j - 1).get(1);
				Cell after = c.get(j).get(1);

				if (now.getStringValue().equals(after.getStringValue()))
					continue;

				Boolean flag = false;

				if (t == "Num" && Math.abs(
						((Num) rightList.cols.get(0)).getMean() - ((Num) leftList.cols.get(0)).getMean()) >= epsilon)
					flag = true;
				else if (t == "Sym")
					flag = true;

				double compareVal = (after.getDoubleValue() - Double.valueOf(start));
				if (flag && compareVal >= epsilon) {

					if (Double.valueOf(stop) - now.getDoubleValue() >= epsilon) {
						double xpect = xpectFn(leftList, rightList, t);
						if (xpect * TRIVIAL < best) {
							best = xpect;
							cut = j;
						}
					}
				}

			}
		}
		if (cut != -1 && isFirstCut) {
				isFirstCut =false;
				rank = divide(c, leftList.cols.get(0), low, cut, rank, t) + 1;
				rank = divide(c, rightList.cols.get(0), cut, high, rank, t);
				
		} else {
			if (t == "Num") {
				Num lis = (Num) tblList.cols.get(0);
				gain += lis.getList().size() * lis.getSd();
				lis.rank = rank;
				List<Col> rangeCols = new ArrayList<>();

				for(int p=0;p<tblList.cols.size();p++)
				{
					rangeCols.add(tblList.cols.get(p));
				}
				rangesNum.add(rangeCols);

			}
			if (t == "Sym") {
				Sym lis = (Sym) tblList.cols.get(0);
				gain += lis.getListCount() * lis.getEntropy();
				lis.rank = rank;
				List<Col> rangeCols = new ArrayList<>();
				for(int p=0;p<tblList.cols.size();p++)
				{
					rangeCols.add(tblList.cols.get(p));
				}
				rangesNum.add(rangeCols);
			}
		}
		return rank;
	}

	private double xpectFn(Tbl leftList, Tbl rightList, String t) {
		int n = leftList.rows.size() + rightList.rows.size();
		double a = leftList.rows.size();
		double b = rightList.rows.size();
		double a1, b1;
		if (t == "Num") {
			a1 = a * ((Num) leftList.cols.get(0)).getSd() / n;
			b1 = b * ((Num) rightList.cols.get(0)).getSd() / n;
		} else {

			a1 = a * ((Sym) leftList.cols.get(0)).getEntropy() / n;
			b1 = b * ((Sym) rightList.cols.get(0)).getEntropy() / n;
		}
		return a1 + b1;
	}

	public String getRange() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rangesNum.size(); i++) {
			List<Col> cols = rangesNum.get(i);
			Num x = ((Num) cols.get(1));
			sb.append(i + 1)
					.append(" x.n\t" + x.getList().size() + " | x.lo " + U.d(x.low) + " x.hi " + U.d(x.high) + " | ");
			if (type == ColType.Num) {
				Num y = ((Num) cols.get(0));
				sb.append("y.lo " + U.d(y.low) + " y.hi " + U.d(y.high));
			} else {
				Sym y = ((Sym) cols.get(0));
				sb.append("y.mode " + y.getMode() + " y.ent " + (y.getEntropy()));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
