import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class TreeTest {

	@Test
	void testDecisionTree() throws IOException {
		Path scvPath = Paths.get("../diabetes.csv");
		Path ansPath = Paths.get("../decision-tree-out.txt");

		String csv = new String(Files.readAllBytes(scvPath));
		String expected = new String(Files.readAllBytes(ansPath));

		Tbl tbl = new Tbl(csv);

		String actual = tbl.decisionTree();

		assertEquals(expected, actual);
	}	
	
	@Test
	void testRegressionTree() throws IOException {
		Path scvPath = Paths.get("../auto.csv");
		Path ansPath = Paths.get("../auto-ans.txt");

		String csv = new String(Files.readAllBytes(scvPath));
		String expected = new String(Files.readAllBytes(ansPath));

		Tbl tbl = new Tbl(csv);

		String actual = tbl.regressionTree();

		assertEquals(expected, actual);
	}	

}
