import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Row {

	List<Cell> cells = new ArrayList<>();
	
	public Row(List<Cell> cells, Set<Integer> skipCols) {
		for (int i = 0; i < cells.size(); i++) {
			if (skipCols.contains(i)) {
				continue;
			}
			this.cells.add(cells.get(i));
		}
	}
	
	public Cell get(int i) {
		return cells.get(i);
	}
	
	public int size() {
		return cells.size();
	}

	@Override
	public String toString() {
		return U.toString(cells);
	}
	
}
