import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Abcd {

	private static final int NUM_COL_CHARS = 7;

	private static final String[] COLS = new String[] { "db", "rx", "num", "a", "b", "c", "d", "acc", "pre", "pd",
			"pf", "f", "g", "class" };

	private String rx = "rx";
	private String data = "data";
	private Map<String, Integer> a = new HashMap<>();
	private Map<String, Integer> b = new HashMap<>();
	private Map<String, Integer> c = new HashMap<>();
	private Map<String, Integer> d = new HashMap<>();
	private int yes = 0;
	private int no = 0;
	private Set<String> known = new HashSet<>();

	public void one(String want, String got) {
		if (!known.contains(want)) {
			a.put(want, yes + no);
			known.add(want);
		}
		if (!known.contains(got)) {
			a.put(got, yes + no);
			known.add(got);
		}
		if (want.equals(got)) {
			yes++;
		} else {
			no++;
		}
		for (String x : known) {
			if (want.equals(x)) {
				if (want.equals(got)) {
					U.incrementCount(x, d);
				} else {
					U.incrementCount(x, b);
				}
			} else {
				if (got.equals(x)) {
					U.incrementCount(x, c);
				} else {
					U.incrementCount(x, a);
				}
			}
		}
	}

	public String report() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < COLS.length; i++) {
			sb.append(String.format("%" + NUM_COL_CHARS + "s", COLS[i]));
			sb.append(i == COLS.length - 1 ? "\n" : "|");
		}
		for (int i = 0; i < COLS.length; i++) {
			sb.append(String.format("%" + NUM_COL_CHARS + "s", "").replace(' ', '-'));
			sb.append(i == COLS.length - 1 ? "\n" : "|");
		}

		int a, b, c, d;
		double pd, pf, pn, prec, g, f, acc;

		for (String x : known) {
			pd = pf = pn = prec = g = f = acc = 0;
			a = this.a.containsKey(x) ? this.a.get(x) : 0;
			b = this.b.containsKey(x) ? this.b.get(x) : 0;
			c = this.c.containsKey(x) ? this.c.get(x) : 0;
			d = this.d.containsKey(x) ? this.d.get(x) : 0;
			if (b + d > 0) {
				pd = d / (double) (b + d);
			}
			if (a + c > 0) {
				pf = c / (double) (a + c);
				pn = (b + d) / (double) (a + c);
			}
			if (c + d > 0) {
				prec = d / (double) (c + d);
			}
			if (1 - pf + pd > 0) {
				g = 2 * (1 - pf) * pd / (double) (1 - pf + pd);
			}
			if (prec + pd > 0) {
				f = 2 * prec * pd / (double) (prec + pd);
			}
			if (yes + no > 0) {
				acc = yes / (double) (yes + no);
			}
			StringBuilder row = new StringBuilder();
			row.append(String.format("%" + NUM_COL_CHARS + "s|", data));
			row.append(String.format("%" + NUM_COL_CHARS + "s|", rx));
			row.append(String.format("%" + NUM_COL_CHARS + "s|", yes + no));
			row.append(String.format("%" + NUM_COL_CHARS + "s|", a > 0 ? a : ""));
			row.append(String.format("%" + NUM_COL_CHARS + "s|", b > 0 ? b : ""));
			row.append(String.format("%" + NUM_COL_CHARS + "s|", c > 0 ? c : ""));
			row.append(String.format("%" + NUM_COL_CHARS + "s|", d > 0 ? d : ""));
			row.append(String.format("%" + NUM_COL_CHARS + "s|", String.format("%.2f", acc)));
			row.append(String.format("%" + NUM_COL_CHARS + "s|", String.format("%.2f", prec)));
			row.append(String.format("%" + NUM_COL_CHARS + "s|", String.format("%.2f", pd)));
			row.append(String.format("%" + NUM_COL_CHARS + "s|", String.format("%.2f", pf)));
			row.append(String.format("%" + NUM_COL_CHARS + "s|", String.format("%.2f", f)));
			row.append(String.format("%" + NUM_COL_CHARS + "s|", String.format("%.2f", g)));
			row.append(String.format("%" + Math.max(NUM_COL_CHARS, x.length()) + "s\n", x));
			sb.append(row);
		}

		return sb.toString();
	}

}
