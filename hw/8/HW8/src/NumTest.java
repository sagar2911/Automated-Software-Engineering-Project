import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NumTest {
	
	@Test
	void test() {
		Num num = new Num(0, "");
		num.addNum(1);
		assertEquals(1.0, num.getMean());
		assertEquals(0, num.getSd());
	}
	
	@Test
	void test2() {
		Num num = new Num(0, "");
		num.addNum(10);
		num.addNum(20);
		assertEquals(15.0, num.getMean());
		assertEquals(7.0710678118654755, num.getSd());
	}
	
	@Test
	void test3() {
		Num num = new Num(0, "");
		num.addNum(10);
		num.addNum(12);
		num.addNum(23);
		num.addNum(23);
		num.addNum(16);
		num.addNum(23);
		num.addNum(21);
		num.addNum(16);
		assertEquals(18.000000000000004, num.getMean());
		assertEquals(5.237229365663817, num.getSd());
	}

}
