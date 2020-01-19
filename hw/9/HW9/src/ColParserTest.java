
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class ColParserTest {

	@Test
	void testSkip() {
		ColMetadata metadata = ColParser.parse("test ? test  ");
		assertTrue(metadata.isSkip);
	}
	
	@Test
	void testNotSkip() {
		ColMetadata metadata = ColParser.parse("test");
		assertFalse(metadata.isSkip);
	}
	
	@Test
	void testNumLessThan() {
		ColMetadata metadata = ColParser.parse(" t<test  ");
		assertTrue(metadata.isNum);
	}
	
	@Test
	void testNumGreaterThan() {
		ColMetadata metadata = ColParser.parse("t>test  ");
		assertTrue(metadata.isNum);
	}
	
	@Test
	void testNum$() {
		ColMetadata metadata = ColParser.parse("t$ test  ");
		assertTrue(metadata.isNum);
	}
	
	@Test
	void testNotNum() {
		ColMetadata metadata = ColParser.parse("test");
		assertFalse(metadata.isNum);
	}
	
	@Test
	void testGoalLessThan() {
		ColMetadata metadata = ColParser.parse("t<test  ");
		assertTrue(metadata.isGoal);
	}
	
	@Test
	void testGoalGreaterThan() {
		ColMetadata metadata = ColParser.parse("t>test  ");
		assertTrue(metadata.isGoal);
	}

	@Test
	void testGoalExclamation() {
		ColMetadata metadata = ColParser.parse("t!test  ");
		assertTrue(metadata.isGoal);
	}
	
	@Test
	void testNotGoal() {
		ColMetadata metadata = ColParser.parse("test");
		assertFalse(metadata.isGoal);
	}

	@Test
	void testLess() {
		ColMetadata metadata = ColParser.parse("t<test  ");
		assertTrue(metadata.isLess);
	}
	
	@Test
	void testNotLess() {
		ColMetadata metadata = ColParser.parse("test");
		assertFalse(metadata.isLess);
	}

}
