import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CSVParserTest {

	@Test
	void testOneRow() {
		CSVParser tbl = new CSVParser("$cloudCover,$temp");
		assertEquals("['$cloudCover', '$temp']\n", tbl.toString());
	}
	
	@Test
	void testCellWithTrailingSpaces() {
		CSVParser tbl = new CSVParser("$cloudCover,   $temp   ");
		assertEquals("['$cloudCover', '$temp']\n", tbl.toString());
	}

	@Test
	void testStandardData() {
		CSVParser tbl = new CSVParser("$cloudCover, $temp\n100, 68");
		assertEquals("['$cloudCover', '$temp']\n[100, 68]\n", tbl.toString());
	}
	
	@Test
	void testLargeData() {
		CSVParser tbl = new CSVParser("$cloudCover, $temp, ?$humid, <wind,  $playHours\n" + 
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
		assertEquals("['$cloudCover', '$temp', '?$humid', '<wind', '$playHours']\n" + 
				"[100, 68, 80, 0, 3]\n" + 
				"[0, 85, 85, 0, 0]\n" + 
				"['']\n" + 
				"[0, 80, 90, 10, 0]\n" + 
				"[60, 83, 86, 0, 4]\n" + 
				"[100, 70, 96, 0, 3]\n" + 
				"[100, 65, 70, 20, 0]\n" + 
				"[70, 64, 65, 15, 5]\n" + 
				"[0, 72, 95, 0, 0]\n" + 
				"[0, 69, 70, 0, 4]\n" + 
				"['?', 75, 80, 0, '?']\n" + 
				"[0, 75, 70, 18, 4]\n" + 
				"[60, 72]\n" + 
				"[40, 81, 75, 0, 2]\n" + 
				"[100, 71, 91, 15, 0]\n", tbl.toString());
	}

}
