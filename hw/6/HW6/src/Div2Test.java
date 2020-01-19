import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class Div2Test {

	@Test
	void testDiv2Num() throws IOException {
		Path path = Paths.get("../num.csv");
		String csv = new String(Files.readAllBytes(path));

		Tbl tbl = new Tbl(csv);
		Div2 div2 = new Div2(tbl, 0, 1, ColType.Num);
		System.out.println(div2.getRange());
	}
	
	@Test
	void testDiv2Sym() throws IOException {
		Path path = Paths.get("../sym.csv");
		String csv = new String(Files.readAllBytes(path));

		Tbl tbl = new Tbl(csv);
		Div2 div2 = new Div2(tbl, 0, 1, ColType.Sym);

		System.out.println(div2.getRange());
	}

}
