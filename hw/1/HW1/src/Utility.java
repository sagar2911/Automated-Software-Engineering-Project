import java.util.Arrays;
import java.util.List;
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

	public static String toString(List<Double> a) {
		StringBuilder sb = new StringBuilder();
		for (double d : a) {
			sb.append(String.format("%.1f", d) + " ");
		}
		return sb.toString();
	}

}
