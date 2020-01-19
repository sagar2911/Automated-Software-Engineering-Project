import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class U {

	private static Random random = new Random();

	public static int[] generateRandomNumbers(int count) {
		int[] result = new int[count];
		for (int i = 0; i < count; i++) {
			result[i] = generateRandomNumber(1, 10000);
		}
		return result;
	}
	
	public static int generateRandomNumber(int min, int max) {
		return random.nextInt((max - min) + 1) + min;
	}
	
	public static String toString(Object[] a) {
		return Arrays.toString(a);
	}
	
	public static String toString(List<?> l) {
		return Arrays.toString(l.toArray());
	}
	
	public static void incrementCount(String key, Map<String, Integer> map) {
		int count = map.containsKey(key) ? map.get(key) : 0;
		map.put(key, count + 1);
	}
	
	public static String d(double d) {
		return String.format("%.1f", d);
	}
	
	public static String tab(int lvl) {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		for (int i = 0; i < lvl; i++) {
			sb.append("|. ");
		}
		return sb.toString();
	}
	
	public static String toTreeString(Tbl tbl) {
		StringBuilder sb = new StringBuilder();
		sb.append(toTree(tbl)).append(tbl.baseline()).append("\n");
		return sb.toString();	
	}
	
	public static String toTree(Tbl tbl) {
		StringBuilder sb = new StringBuilder();
		String tab = U.tab(tbl.lvl);
		int n = tbl.size();
		String baseline = tbl.baseline();

		sb.append(tab).append(n).append("\n");

		if (tbl.kids == null) {
			sb.append(tab).append(" ").append(baseline).append("\n");
		} else {
			for (Tbl s : tbl.kids) {
				sb.append(toTree(s));
			}
		}
		return sb.toString();
	}
	
}
