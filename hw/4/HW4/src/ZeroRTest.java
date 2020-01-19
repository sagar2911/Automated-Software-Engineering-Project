import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class ZeroRTest {

	@Test
	void testWeathernon() throws IOException {
		Abcds abcds = new Abcds(new ZeroR());

		Path path = Paths.get("../weathernon.csv");
		String csv = new String(Files.readAllBytes(path));
		
		String expected =
				  "     db|     rx|    num|      a|      b|      c|      d|    acc|    pre|     pd|     pf|      f|      g|  class\n"
				+ "-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------\n"
				+ "   data|     rx|     12|      6|      3|      3|       |   0.50|   0.00|   0.00|   0.33|   0.00|   0.00|     no\n"
				+ "   data|     rx|     12|       |      3|      3|      6|   0.50|   0.67|   0.67|   1.00|   0.67|   0.00|    yes\n";
		String actual = abcds.run(csv, 3);

		assertEquals(expected, actual);
	}
	
	@Test
	void testDiabetes() throws IOException {
		Abcds abcds = new Abcds(new ZeroR());
		
		Path path = Paths.get("../diabetes.csv");
		String csv = new String(Files.readAllBytes(path));
		
		String expected =
				  "     db|     rx|    num|      a|      b|      c|      d|    acc|    pre|     pd|     pf|      f|      g|  class\n"
				+ "-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------\n"
				+ "   data|     rx|    766|    474|    243|     25|     24|   0.65|   0.49|   0.09|   0.05|   0.15|   0.16|tested_positive\n"
				+ "   data|     rx|    766|     24|     25|    243|    474|   0.65|   0.66|   0.95|   0.91|   0.78|   0.16|tested_negative\n";
		String actual = abcds.run(csv, 3);

		assertEquals(expected, actual);
	}

}
