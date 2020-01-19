import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utility {

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

}
