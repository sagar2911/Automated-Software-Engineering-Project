import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	private static final String OUTPUT_FILENAME = "output.txt";

	private static final int RANDOM_NUMBER_COUNT = 100;

	private static final int CACHE_DELIMITER = 10;
	
	private static PrintStream out = System.out;

	public static void main(String[] args) {
		setOutputStreamToFile();

		int[] numbers = Utility.generateRandomNumbers(RANDOM_NUMBER_COUNT);

		List<Double> means = new ArrayList<Double>();
		List<Double> sds = new ArrayList<Double>();

		Num num = new Num();
		
		for (int i = 0; i < numbers.length; i++) /* add numbers to list */ {
			num.addNum(numbers[i]);
			if (canCacheForIndex(i)) {
				means.add(num.getMean());
				sds.add(num.getSd());
			}
		}

		out.printf("Means of first 10, 20, ... 100 numbers:\n%s\n\n", Utility.toString(means));
		out.printf("Standard deviations of first 10, 20, ... 100 numbers:\n%s\n", Utility.toString(sds));

		List<Double> newMeans = new ArrayList<Double>();
		List<Double> newSds = new ArrayList<Double>();
		
		out.println("\n\nDeleting numbers from list.\n");

		for (int i = numbers.length - 1; i >= 0; i--) /* delete numbers from list */ {
			if (canCacheForIndex(i)) {
				newMeans.add(num.getMean());
				newSds.add(num.getSd());
			}
			num.deleteNum(numbers[i]);
			
		}

		out.printf("Means of first 100, 90, ... 10 numbers:\n%s\n\n", Utility.toString(newMeans));
		out.printf("Standard deviations of first 100, 90, ... 10 numbers:\n%s\n", Utility.toString(newSds));
		
		out.println("\nChecking means.");
		for (int i = 0; i < newMeans.size(); i++) {
			double newMean = newMeans.get(i);
			double oldMean = means.get(means.size() - 1 - i);
			if (Math.floor(newMean*1000) != Math.floor(oldMean*1000)) {
				out.printf("%s != %s\n", newMean, oldMean);
			}
		}
		
		out.println("\nChecking standard deviations.");
		for (int i = 0; i < newMeans.size(); i++) {
			double newSd = newSds.get(i);
			double oldSd = sds.get(sds.size() - 1 - i);
			if (newSd != oldSd) {
				out.printf("%s != %s\n", newSd, oldSd);
			}
		}
	}
	
	private static boolean canCacheForIndex(int index) {
		return index % CACHE_DELIMITER == 0 && index > 0;
	}
	
	private static void setOutputStreamToFile() {
		try {
			out = new PrintStream(new FileOutputStream(OUTPUT_FILENAME));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
