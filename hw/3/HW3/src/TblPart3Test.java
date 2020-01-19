import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TblPart3Test {
	
	@Test
	void testEmptyCols() {
		Tbl tbl = new Tbl("outlook, ?$temp,  <humid, wind, !play\n" + 
				"rainy, 68, 80, FALSE, yes # comments\n" + 
				"sunny, 85, 85,  FALSE, no\n" + 
				"sunny, 80, 90, TRUE, no\n" + 
				"overcast, 83, 86, FALSE, yes\n" + 
				"rainy, 70, 96, FALSE, yes\n" + 
				"rainy, 65, 70, TRUE, no\n" + 
				"overcast, 64, 65, TRUE, yes\n" + 
				"sunny, 72, 95, FALSE, no\n" + 
				"sunny, 69, 70, FALSE, yes\n" + 
				"rainy, 75, 80, FALSE, yes\n" + 
				"sunny, 75, 70, TRUE, yes\n" + 
				"overcast, 72, 90, TRUE, yes\n" + 
				"overcast, 81, 75, FALSE, yes\n" + 
				"rainy, 71, 91, TRUE, no\n" + 
				"");
		assertEquals("t.cols\n" + 
				"|\t1\n" + 
				"|\t|\tadd: Num1\n" + 
				"|\t|\tcol: 1\n" + 
				"|\t|\thi: 0\n" + 
				"|\t|\tlo: 0\n" + 
				"|\t|\tm2: 0.0\n" + 
				"|\t|\tmu: 0.0\n" + 
				"|\t|\tn: 0\n" + 
				"|\t|\tsd: 0.0\n" + 
				"|\t|\ttxt: <cloudCover\n" +
				"|\t2\n" + 
				"|\t|\tadd: Num1\n" + 
				"|\t|\tcol: 2\n" + 
				"|\t|\thi: 0\n" + 
				"|\t|\tlo: 0\n" + 
				"|\t|\tm2: 0.0\n" + 
				"|\t|\tmu: 0.0\n" + 
				"|\t|\tn: 0\n" + 
				"|\t|\tsd: 0.0\n" + 
				"|\t|\ttxt: $temp\n" + 
				"|\t3\n" + 
				"|\t|\tadd: Sym1\n" + 
				"|\t|\tcnt\n" + 
				"|\t|\tcol: 3\n" + 
				"|\t|\tmode: \n" + 
				"|\t|\tmost: \n" + 
				"|\t|\tn: 0\n" + 
				"|\t|\ttxt: wind\n" + 
				"t.my\n" +
				"|\tclass: 4\n" +
				"|\tgoals\n" +
				"|\t|\t1\n" +
				"|\tnums\n" +
				"|\t|\t1\n" +
				"|\t|\t2\n" +
				"|\tsyms\n" +
				"|\t|\t3\n" +
				"|\tw\n" +
				"|\t|\t1: -1\n" +
				"|\txnums\n" +
				"|\t|\t2\n" +
				"|\txs\n" +
				"|\t|\t2\n" +
				"|\t|\t3\n" +
				"|\txsyms\n" +
				"|\t|\t3\n" +
				"",tbl.dump());
	}

}
