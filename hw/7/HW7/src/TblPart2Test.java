import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TblPart2Test {

	@Test
	void testSkipBlankLine1() {
		Tbl tbl = new Tbl("  ");
		assertEquals("", tbl.toString());
	}

	@Test
	void testSkipBlankLine2() {
		Tbl tbl = new Tbl("$cloudCover, $temp\n ");
		assertEquals("['$cloudCover', '$temp']\n", tbl.toString());
	}	
	
	@Test
	void testSkipDifferentLineSizes() {
		Tbl tbl = new Tbl("$cloudCover, $temp\n1, 2, 3");
		assertEquals("['$cloudCover', '$temp']\nE> skipping line 2\n", tbl.toString());
	}
	
	@Test
	void testQuestionMarkData() {
		Tbl tbl = new Tbl("$cloudCover, $temp\n1, ?");
		assertEquals("['$cloudCover', '$temp']\n[1, '?']\n", tbl.toString());
	}
	
	@Test
	void testComments() {
		Tbl tbl = new Tbl("$cloudCover, $temp  # comment \n1, 2 # comment");
		assertEquals("['$cloudCover', '$temp']\n[1, 2]\n", tbl.toString());
	}
	
	@Test
	void testSkipColumn() {
		Tbl tbl = new Tbl("$?cloudCover, $temp\n1, 2");
		assertEquals("['$temp']\n[2]\n", tbl.toString());
	}
	
	@Test
	void testLargeData() {
		Tbl tbl = new Tbl("$cloudCover, $temp, ?$humid, <wind,  $playHours\n" + 
				"  100,        68,    80,    0,    3   # comments\n" + 
				"  0,          85,    85,    0,    0\n" + 
				"\n" + 
				"  0,          80,    90,    10,   0\n" + 
				"  60,         83,    86,    0,    4\n" + 
				"  100,        70,    96,    0,    3\n" + 
				"  100,        65,    70,    20,   0\n" + 
				"  70,         64,    65,    15,   5\n" + 
				"  0,          72,    95,    0,    0\n" + 
				"  0,          69,    70,    0,    4\n" + 
				"  ?,          75,    80,    0,    ?\n" + 
				"  0,          75,    70,    18,   4\n" + 
				"  60,         72,\n" + 
				"  40,         81,    75,    0,    2\n" + 
				"  100,        71,    91,    15,   0");
		assertEquals("['$cloudCover', '$temp', '<wind', '$playHours']\n" + 
				"[100, 68, 0, 3]\n" + 
				"[0, 85, 0, 0]\n" + 
				"[0, 80, 10, 0]\n" + 
				"[60, 83, 0, 4]\n" + 
				"[100, 70, 0, 3]\n" + 
				"[100, 65, 20, 0]\n" + 
				"[70, 64, 15, 5]\n" + 
				"[0, 72, 0, 0]\n" + 
				"[0, 69, 0, 4]\n" + 
				"['?', 75, 0, '?']\n" + 
				"[0, 75, 18, 4]\n" + 
				"E> skipping line 14\n" + 
				"[40, 81, 0, 2]\n" + 
				"[100, 71, 15, 0]\n", tbl.toString());
	}
	
}
