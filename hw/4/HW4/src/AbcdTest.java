import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AbcdTest {

	@Test
	void test() {
		Abcd abcd = new Abcd();

		for (int i = 0; i < 6; i++) {
			abcd.one("yes", "yes");
		}
		for (int i = 0; i < 2; i++) {
			abcd.one("no", "no");
		}
		for (int i = 0; i < 5; i++) {
			abcd.one("maybe", "maybe");
		}
		abcd.one("maybe", "no");

		String expected = "     db|     rx|    num|      a|      b|      c|      d|    acc|    pre|     pd|     pf|      f|      g|  class\n"
				+ "-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------|-------\n"
				+ "   data|     rx|     14|     11|       |      1|      2|   0.93|   0.67|   1.00|   0.08|   0.80|   0.96|     no\n"
				+ "   data|     rx|     14|      8|      1|       |      5|   0.93|   1.00|   0.83|   0.00|   0.91|   0.91|  maybe\n"
				+ "   data|     rx|     14|      8|       |       |      6|   0.93|   1.00|   1.00|   0.00|   1.00|   1.00|    yes\n";
		String actual = abcd.report();

		assertEquals(expected, actual);
	}

}
