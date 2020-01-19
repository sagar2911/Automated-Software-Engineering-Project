import java.util.List;

public class ZeroR implements Classifier {
	
	private Tbl tbl;
	
	public ZeroR() {
		tbl = new Tbl();
	}

	public void train(int r, List<Cell> row) {
		tbl.addRow(r, row);
	}

	public String classify(List<Cell> row) {
		int goalColIndex = getGoalColIndex();
		return ((Sym) tbl.cols.get(goalColIndex)).getMode();
	}
	
	public int getGoalColIndex() {
		return tbl.myGoals.get(0) - 1;
	}
	
}
