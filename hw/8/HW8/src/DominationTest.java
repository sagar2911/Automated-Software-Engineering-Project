import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DominationTest {

	@Test
	void testDominatesLessThan() throws IOException {
		Num g1 = new Num(1, "<weight");
		g1.w = -1;
		g1.addNum(10);
		g1.addNum(9);

		List<Num> goals = Arrays.asList(g1);
		
		Row r1 = new Row(Arrays.asList(new Cell("10")));
		Row r2 = new Row(Arrays.asList(new Cell("9")));

		assertTrue(Domination.dominates(r2, r1, goals));
	}	
	
	@Test
	void testDominatesGreaterThan() throws IOException {
		Num g1 = new Num(1, ">acceleration");
		g1.addNum(5);
		g1.addNum(4);

		List<Num> goals = Arrays.asList(g1);
		
		Row r1 = new Row(Arrays.asList(new Cell("5")));
		Row r2 = new Row(Arrays.asList(new Cell("4")));

		assertTrue(Domination.dominates(r1, r2, goals));
	}	
	
	@Test
	void testDominatesTwoGoals() throws IOException {
		Num g1 = new Num(1, "<weight");
		g1.w = -1;
		g1.addNum(5);
		g1.addNum(1);
		g1.addNum(10);

		Num g2 = new Num(2, ">acceleration");
		g2.addNum(6);
		g2.addNum(1);
		g2.addNum(10);

		List<Num> goals = Arrays.asList(g1, g2);
		
		Row r1 = new Row(Arrays.asList(new Cell("5"), new Cell("6")));
		Row r2 = new Row(Arrays.asList(new Cell("1"), new Cell("1")));

		assertTrue(Domination.dominates(r1, r2, goals));
	}	
	
	@Test
	void testDominates() throws IOException {
		Num g1 = new Num(1, "<weight");
		g1.w = -1;
		g1.addNum(10);
		g1.addNum(1);

		Num g2 = new Num(2, ">acceleration");
		g2.addNum(10);
		g2.addNum(1);

		Num g3 = new Num(3, ">mpg");
		g3.addNum(4);
		g3.addNum(5);

		List<Num> goals = Arrays.asList(g1, g2, g3);
		
		Row r1 = new Row(Arrays.asList(new Cell("10"), new Cell("10"), new Cell("4")));
		Row r2 = new Row(Arrays.asList(new Cell("1"), new Cell("1"), new Cell("5")));

		assertTrue(Domination.dominates(r2, r1, goals));
	}	

}
