import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Div {

	private static final int N = 10;

	private static final Random rand = new Random();

	private static final double p = 2;

	public class Res {
		Row x; // pivot1
		Row y; // pivot2
		double c; // distance
	}

	private List<Col> cols;
	private List<Row> rows;

	public Div(Tbl tbl) {
		this.cols = tbl.cols;
		this.rows = tbl.rows;
	}

	public Res findGoodPivots() {
		Res goodPivots = null;
		double minSplitRatio = Double.MAX_VALUE;

		for (int i = 0; i < N; i++) {
			Res pivots = findPivots();

			double ratio = splitRatio(pivots.x, pivots.y);

			if (ratio < minSplitRatio) {
				minSplitRatio = ratio;
				goodPivots = pivots;
			}
		}

		return goodPivots;
	}
	
	public double cos(Row x, Row y, Row z, double c) {
		double dist1 = dist(x, z);
		double dist2 = dist(y, z);
		return (Math.pow(dist1, 2) + Math.pow(c, 2) - Math.pow(dist2, 2)) / (2 * c);
	}

	private Res findPivots() {
		Res pivots = new Res();

		int randIndex = rand.nextInt(rows.size());
		Row point = rows.get(randIndex);

		pivots.x = distant(point);
		pivots.y = distant(pivots.x);
		pivots.c = dist(pivots.x, pivots.y);

		return pivots;
	}

	private Row distant(Row x) {
		class DistRow implements Comparable<DistRow> {
			Row r;
			double dist = 0;

			public int compareTo(DistRow o) {
				return dist > o.dist ? 1 : -1;
			}
		};

		List<DistRow> drs = new ArrayList<DistRow>();
		for (Row z : rows) {
			DistRow dr = new DistRow();
			dr.r = z;
			dr.dist = dist(z, x);
			drs.add(dr);
		}
		Collections.sort(drs);
		int index = (int) (drs.size() * .9);
		return drs.get(index).r;
	}

	private double dist(Row i, Row j) {
		double d = 0;
		int n = 0;
		for (Col c : cols) {
			double d0 = c.dist(i.cells.get(n), j.cells.get(n));
			d += Math.pow(d0, p);
			n++;
		}
		return Math.pow(d, 1 / p) / Math.pow(n, 1 / p);
	}

	private double splitRatio(Row x, Row y) {
		int lcount = 0;
		int rcount = 0;
		for (Row z : rows) {
			double ldist = dist(z, x);
			double rdist = dist(z, y);
			if (ldist > rdist) {
				lcount++;
			} else {
				rcount++;
			}
		}
		return lcount / rcount;
	}

}
