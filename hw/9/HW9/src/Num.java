import java.util.ArrayList;
import java.util.List;

public class Num extends Col {

	private List<Double> list = new ArrayList<Double>();
	double mean = 0;
	double sd = 0;
	double sum = 0;
	double m2 = 0;
	double high = Integer.MIN_VALUE;
	double low = Integer.MAX_VALUE;
	int rank;

	public Num(int col, String value) {
		super(col, value);
	}

	public List getList() {
		return list;
	}

	public void addNum(double num) {
		if (list.size() == 0) {
			list.add(num);
			mean = num;
			sum = num;
			m2 = 0;
			sd = 0;
			high = num;
			low = num;
		} else {
			list.add(num);
			int n = list.size();
			double temp = num - mean;
			mean += temp / n;
			sum += num;
			m2 += temp * (num - mean);
			sd = CalculateSD();
			if (num > high) {
				high = num;
			}
			if (num < low) {
				low = num;
			}
		}
	}

	public Double CalculateSD() {

		if (m2 < 0)
			return 0.0;
		if (list.size() < 2)
			return 0.0;
		return Math.sqrt(m2 / (list.size() - 1));
	}

	public void deleteNum(double num) {
		list.remove(list.size() - 1);
		if (list.size() == 0) {
			m2 = 0;
			sd = 0;
		} else if (list.size() == 1) {
			m2 = num;
			sd = 0;
		} else {
			int n = list.size();
			double temp = num - mean;
			mean -= temp / n;
			m2 -= temp * (num - mean);
			sum -= num;
			sd = CalculateSD();
		}

	}

	public double getMean() {
		return mean;
	}

	public double getSd() {
		return sd;
	}

	public String dump() {
		StringBuilder dump = new StringBuilder();
		dump.append("|\t|\tadd: Num1\n");
		dump.append("|\t|\tcol: " + col + "\n");
		dump.append("|\t|\thi: " + (list.isEmpty() ? 0 : high) + "\n");
		dump.append("|\t|\tlo: " + (list.isEmpty() ? 0 : low) + "\n");
		dump.append("|\t|\tm2: " + m2 + "\n");
		dump.append("|\t|\tmu: " + mean + "\n");
		dump.append("|\t|\tn: " + list.size() + "\n");
		dump.append("|\t|\tsd: " + sd + "\n");
		dump.append("|\t|\ttxt: " + txt + "\n");
		return dump.toString();
	}

	public ColType getType() {
		return ColType.Num;
	}

	private static final double delim = Math.pow(10, -32);
	private static final String no = "?";

	public double dist(Cell cell, Cell cell2) {
		double x, y;
		double denom = high - low + delim;

		if (cell.stringValue.equals(no)) {
			if (cell2.stringValue.equals(no)) {
				return 1;
			}
			y = (cell2.doubleValue - low) / denom;
			x = (y > .5) ? 0 : 1;
		} else {
			x = (cell.doubleValue - low) / denom;
			if (cell2.stringValue.equals(no)) {
				y = (x > .5) ? 0 : 1;
			} else {
				y = (cell2.doubleValue - low) / denom;
			}
		}
		return Math.abs(x - y);
	}

	public double like(int x) {
		double var = Math.pow(sd, 2);
		double denom = Math.pow(3.14159 * 2 * var, 0.5);
		double num = Math.pow(2.71828, -Math.pow(x - mean, 2) / (2 * var + 0.0001));
		return num / (denom + Math.pow(10, -64));
	}

	public boolean same(Num j) {
		double conf = 0.95;
		double small = 0.38;
		return tTestSame(j, conf) || hedges(j, small);
	}

	private boolean tTestSame(Num j, double conf) {
		double nom = Math.abs(mean - j.mean);
		double s1 = sd;
		double s2 = j.sd;
		int n1 = list.size();
		int n2 = j.list.size();
		double denom = (s1 + s2) > 0 ? Math.pow(s1 / n1 + s2 / n2, 0.5) : 1;
		double df = Math.min(n1 - 1, n2 - 1);
		return criticalValue(df, conf) >= nom / denom;
	}

	private double criticalValue(double df, double conf) {
		double[] xs = new double[] { 1, 2, 5, 10, 15, 20, 25, 30, 60, 100 };
		double[] ys = new double[] { 6.314, 2.92, 2.015, 1.812, 1.753, 1.725, 1.708, 1.697, 1.671, 1.66 };
		return interpolate(df, xs, ys);
	}

	private double interpolate(double x, double[] xs, double[] ys) {
		if (x <= xs[0]) {
			return ys[0];
		}
		if (x >= xs[xs.length - 1]) {
			return ys[ys.length - 1];
		}
		double x0 = xs[0];
		double y0 = ys[0];
		double x1 = 0;
		double y1 = 0;
		for (int i = 0; i < xs.length; i++) {
			x1 = xs[i];
			y1 = ys[i];
			if (x < x0 || x > xs[xs.length - 1] || (x0 <= x && x < x1)) {
				break;
			}
			x0 = x1;
			y0 = y1;
		}
		double gap = (x - x0) / (x1 - x0);
		return y0 + gap * (y1 - y0);
	}

	private boolean hedges(Num j, double small) {
		int n1 = list.size();
		int n2 = j.list.size();
		double num = (n1 - 1) * Math.pow(sd, 2) + (n2 - 1) * Math.pow(j.sd, 2);
		double denom = (n1 - 1) + (n2 - 1);
		double sp = Math.pow(num / denom, 0.5);
		double delta = Math.abs(mean - j.mean) / sp;
		double c = 1 - 3.0 / (4 * (n1 + n2 - 2) - 1);
		return delta * c < small;
	}
}
