import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TblPart3Test {
	
	@Test
	void testEmptyCols() {
		Tbl tbl = new Tbl("$cloudCover, $temp\n");
		assertEquals("t.cols\n" + 
				"|  1\n" + 
				"|  |  add: Num1\n" + 
				"|  |  col: 1\n" + 
				"|  |  hi: 0\n" + 
				"|  |  lo: 0\n" + 
				"|  |  m2: 0.0\n" + 
				"|  |  mu: 0.0\n" + 
				"|  |  n: 0\n" + 
				"|  |  sd: 0.0\n" + 
				"|  |  txt: $cloudCover\n" +
				"|  2\n" + 
				"|  |  add: Num1\n" + 
				"|  |  col: 2\n" + 
				"|  |  hi: 0\n" + 
				"|  |  lo: 0\n" + 
				"|  |  m2: 0.0\n" + 
				"|  |  mu: 0.0\n" + 
				"|  |  n: 0\n" + 
				"|  |  sd: 0.0\n" + 
				"|  |  txt: $temp\n", tbl.dump());
	}
	
	@Test
	void testOneRow() {
		Tbl tbl = new Tbl("$cloudCover, $temp\n100, 68");
		assertEquals("t.cols\n" + 
				"|  1\n" + 
				"|  |  add: Num1\n" + 
				"|  |  col: 1\n" + 
				"|  |  hi: 100\n" + 
				"|  |  lo: 100\n" + 
				"|  |  m2: 0.0\n" + 
				"|  |  mu: 100.0\n" + 
				"|  |  n: 1\n" + 
				"|  |  sd: 0.0\n" + 
				"|  |  txt: $cloudCover\n" +
				"|  2\n" + 
				"|  |  add: Num1\n" + 
				"|  |  col: 2\n" + 
				"|  |  hi: 68\n" + 
				"|  |  lo: 68\n" + 
				"|  |  m2: 0.0\n" + 
				"|  |  mu: 68.0\n" + 
				"|  |  n: 1\n" + 
				"|  |  sd: 0.0\n" + 
				"|  |  txt: $temp\n" +
				"t.rows\n" + 
				"|  1\n" + 
				"|  |  cells\n" + 
				"|  |  |  1: 100\n" + 
				"|  |  |  2: 68\n" + 
				"|  |  cooked\n" + 
				"|  |  dom: 0\n", tbl.dump());
	}

}
