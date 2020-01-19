import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TblPart3Test {
	
	@Test
	void testEmptyCols() {
		Tbl tbl = new Tbl("outlook, temperature, humidity, windy, !play\n" + 
//				"sunny,hot,high,FALSE,no\n" + 
//				"sunny, 85, 85,  FALSE, no\n" + 
//				"sunny, 80, 90, TRUE, no\n" + 
//				"overcast, 83, 86, FALSE, yes\n" + 
//				"rainy, 70, 96, FALSE, yes\n" + 
//				"rainy, 65, 70, TRUE, no\n" + 
//				"overcast, 64, 65, TRUE, yes\n" + 
//				"sunny, 72, 95, FALSE, no\n" + 
//				"sunny, 69, 70, FALSE, yes\n" + 
//				"rainy, 75, 80, FALSE, yes\n" + 
//				"sunny, 75, 70, TRUE, yes\n" + 
//				"overcast, 72, 90, TRUE, yes\n" + 
//				"overcast, 81, 75, FALSE, yes\n" + 
//				"rainy, 71, 91, TRUE, no\n" + 
				"");
		assertEquals("t.cols\n" + 
				"|\t1\n" + 
				"|\t|\tadd: Sym1\n" + 
				"|\t|\tcnt\n" + 
				"|\t|\tcol: 1\n" + 
				"|\t|\tmode: \n" + 
				"|\t|\tmost: 0\n" + 
				"|\t|\tn: 0\n" +  
				"|\t|\ttxt: outlook\n" +
				"|\t2\n" + 
				"|\t|\tadd: Sym1\n" + 
				"|\t|\tcnt\n" + 
				"|\t|\tcol: 2\n" + 
				"|\t|\tmode: \n" + 
				"|\t|\tmost: 0\n" + 
				"|\t|\tn: 0\n" + 
				"|\t|\ttxt: temperature\n" + 
				"|\t3\n" + 
				"|\t|\tadd: Sym1\n" + 
				"|\t|\tcnt\n" + 
				"|\t|\tcol: 3\n" + 
				"|\t|\tmode: \n" + 
				"|\t|\tmost: 0\n" + 
				"|\t|\tn: 0\n" + 
				"|\t|\ttxt: humidity\n" + 
				"|\t4\n" + 
				"|\t|\tadd: Sym1\n" + 
				"|\t|\tcnt\n" + 
				"|\t|\tcol: 4\n" + 
				"|\t|\tmode: \n" + 
				"|\t|\tmost: 0\n" + 
				"|\t|\tn: 0\n" + 
				"|\t|\ttxt: windy\n" + 
				"|\t5\n" + 
				"|\t|\tadd: Sym1\n" + 
				"|\t|\tcnt\n" + 
				"|\t|\tcol: 5\n" + 
				"|\t|\tmode: \n" + 
				"|\t|\tmost: 0\n" + 
				"|\t|\tn: 0\n" + 
				"|\t|\ttxt: !play\n" + 
				"t.my\n" +
				"|\tclass: 5\n" +
				"|\tgoals\n" +
				"|\t|\t5\n" +
				"|\tnums\n" +
				"|\tsyms\n" +
				"|\t|\t1\n" +
				"|\t|\t2\n" +
				"|\t|\t3\n" +
				"|\t|\t4\n" +
				"|\t|\t5\n" +
				"|\tw\n" +
				"|\txnums\n" +
				"|\txs\n" +
				"|\t|\t1\n" +
				"|\t|\t2\n" +
				"|\t|\t3\n" +
				"|\t|\t4\n" +
				"|\txsyms\n" +
				"|\t|\t1\n" +
				"|\t|\t2\n" +
				"|\t|\t3\n" +
				"|\t|\t4\n" +
				"",tbl.dump());
	}

}
