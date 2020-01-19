import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class NbTest {

	@Test
	void testWeathernon() throws IOException {
		Abcds abcds = new Abcds(new Nb());

		Path path = Paths.get("../weathernon.csv");
		String csv = new String(Files.readAllBytes(path));
		
		String expected =
				  "     db|     rx|    num|      a|      b|      c|      d|    acc|    pre|     pd|     pf|      f|      g|  class\n"
				+ "-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------\n"
				+ "   data|     rx|     11|      3|      1|      5|      2|   0.45|   0.29|   0.67|   0.62|   0.40|   0.48|     no\n"
				+ "   data|     rx|     11|      2|      5|      1|      3|   0.45|   0.75|   0.38|   0.33|   0.50|   0.48|    yes\n";
		String actual = abcds.run(csv, 4);

		assertEquals(expected, actual);
	}
	
	@Test
	void testDiabetes() throws IOException {
		Abcds abcds = new Abcds(new Nb());
		
		Path path = Paths.get("../diabetes.csv");
		String csv = new String(Files.readAllBytes(path));
		
		String expected =
				  "     db|     rx|    num|      a|      b|      c|      d|    acc|    pre|     pd|     pf|      f|      g|  class\n"
				+ "-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------\n"
				+ "   data|     rx|    765|    176|    115|     90|    384|   0.73|   0.81|   0.77|   0.34|   0.79|   0.71|tested_negative\n"
				+ "   data|     rx|    765|    384|     90|    115|    176|   0.73|   0.60|   0.66|   0.23|   0.63|   0.71|tested_positive\n";
		String actual = abcds.run(csv, 20);

		assertEquals(expected, actual);
	}

}
