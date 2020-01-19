import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class TreeTest {
	
	private static final String XOMO_PATH = "../xomo10000.csv";
	private static final String POM_PATH = "../pom310000.csv";
	
	// -------------------- All Tree --------------------

//	@Test
	void testReportAllXomo() throws IOException {
		Report r = new Report();
		double score = r.reportTree(XOMO_PATH, false, 0.5, 20);
		System.out.println(U.d(score));
	}	
	
//	@Test
	void testReportAllPom() throws IOException {
		Report r = new Report();
		double score = r.reportTree(POM_PATH, false, 0.5, 20);
		System.out.println(U.d(score));
	}
	
//	@Test
	void testAllTreeXomo() throws IOException {
		Path scvPath = Paths.get(XOMO_PATH);
		String csv = new String(Files.readAllBytes(scvPath));
		Tbl tbl = new Tbl(csv);
		Tbl tree = tbl.computeTree(false, 0);
		String res = U.toTreeString(tree);
		System.out.println(res);
	}	
	
//	@Test
	void testAllTreePom() throws IOException {
		Path scvPath = Paths.get(POM_PATH);
		String csv = new String(Files.readAllBytes(scvPath));
		Tbl tbl = new Tbl(csv);
		Tbl tree = tbl.computeTree(false, 0);
		String res = U.toTreeString(tree);
		System.out.println(res);
	}	

	// -------------------- Incremental Tree --------------------
	
//	@Test
	void testReportIncrementalXomo() throws IOException {
		Report r = new Report();
		double score = r.reportTree(XOMO_PATH, true, 0.5, 20);
		System.out.println(U.d(score));
	}	
	
//	@Test
	void testReportIncrementalPom() throws IOException {
		Report r = new Report();
		double score = r.reportTree(POM_PATH, true, 0.5, 20);
		System.out.println(U.d(score));
	}
	
	@Test
	void testIncrementalTreeXomo() throws IOException {
		Path scvPath = Paths.get(XOMO_PATH);
		String csv = new String(Files.readAllBytes(scvPath));
		Tbl tbl = new Tbl(csv);
		Tbl tree = tbl.computeTree(true, 0.5);
		String res = U.toTreeString(tree);
		System.out.println(res);
	}	
	
//	@Test
	void testIncrementalTreePom() throws IOException {
		Path scvPath = Paths.get(POM_PATH);
		String csv = new String(Files.readAllBytes(scvPath));
		Tbl tbl = new Tbl(csv);
		Tbl tree = tbl.computeTree(true, 0.5);
		String res = U.toTreeString(tree);
		System.out.println(res);
	}

}
