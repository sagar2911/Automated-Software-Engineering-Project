import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class TreeTest {

	@Test
	void clusterTree() throws IOException {
		Path scvPath = Paths.get("../auto.csv");

		String csv = new String(Files.readAllBytes(scvPath));

		Tbl tbl = new Tbl(csv);

		String res = tbl.tree();
		System.out.println("res is her \n"+res);
	}	
	
}
