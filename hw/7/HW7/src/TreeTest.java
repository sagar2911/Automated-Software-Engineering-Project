import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class TreeTest {

//	@Test
	void testDecisionTree() throws IOException {
		Path scvPath = Paths.get("../xomo10000.csv");
		Path ansPath = Paths.get("../xomo10000.txt");

		String csv = new String(Files.readAllBytes(scvPath));
		String expected = new String(Files.readAllBytes(ansPath));

		Tbl tbl = new Tbl(csv);

		String actual = tbl.tree();

		assertEquals(expected, actual);
	}	
	
	@Test
	void testRegressionTree() throws IOException {
		Path scvPath = Paths.get("../pom310000.csv");
		Path ansPath = Paths.get("../pom310000.txt");

		String csv = new String(Files.readAllBytes(scvPath));
		String expected = new String(Files.readAllBytes(ansPath));

		Tbl tbl = new Tbl(csv);

		String actual = tbl.tree();

		assertEquals(expected, actual);
	}	

}
