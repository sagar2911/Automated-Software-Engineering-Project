import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Nb implements Classifier {

	interface NbTable {
	}

	class SymTable implements NbTable {
		Map<String, Map<String, Integer>> symClassCount = new HashMap<>();
	}

	class NumTable implements NbTable {
		Map<String, Tbl> classStats = new HashMap<>();
	}

	private static final int K = 1;
	private static final int M = 2;

	private Tbl tbl = new Tbl();
	private List<NbTable> tbls = new ArrayList<>();
	private Set<String> classes = new HashSet<>();

	public void train(int r, List<Cell> row) {
		tbl.addRow(r, row);
		if (r == 1) {
			for (Col c : tbl.cols) {
				if (c.getType() == ColType.Num) {
					tbls.add(new NumTable());
				} else {
					tbls.add(new SymTable());
				}
			}
			return;
		}
		String cls = row.get(row.size() - 1).getStringValue();
		classes.add(cls);
		for (int i = 0; i < row.size(); i++) {
			Cell cell = row.get(i);
			ensureClassExists(cls, cell, tbl.cols.get(i));
		}
	}

	public String classify(List<Cell> row) {
		double most = -Math.pow(10, 64);
		String guess = "";
		for (String cls : classes) {
			double like = bayesTheorem(cls, row);
			guess = guess == "" ? cls : guess;
			if (like > most) {
				most = like;
				guess = cls;
			}
		}
		return guess;
	}

	private double bayesTheorem(String cls, List<Cell> row) {
		int n1 = tbl.count(tbl.cols.size() - 1, cls);
		int n = tbl.rows.size();
		int nthings = classes.size();
		double like = (n1 + K) / (double) (n + K * nthings);
		double prior = like;
		like = Math.log(like);
		for (int c : tbl.myXs) {
			int colIndex = c - 1;
			Cell cell = row.get(colIndex);

			if (cell.getStringValue().contains("?")) {
				continue;
			}

			double curlike = 0;
			if (tbl.myNums.contains(c)) {
				NumTable numTable = (NumTable) tbls.get(colIndex);
				Tbl stats = numTable.classStats.get(cls);
				Num statCol = (Num) stats.cols.get(0);
				curlike = statCol.like(cell.getIntValue());
			} else {
				SymTable symTable = (SymTable) tbls.get(colIndex);
				Map<String, Integer> classCount = symTable.symClassCount.get(cell.getStringValue());
				curlike = SymLike(cls, classCount, n, prior);
			}
			double loglike = Math.log(curlike);
			like += loglike;
		}
		return like;
	}

	public int getGoalColIndex() {
		return tbl.myGoals.get(0) - 1;
	}

	private static double SymLike(String cls, Map<String, Integer> classCount, int n, double prior) {
		double f = classCount == null ? 0 : classCount.getOrDefault(cls, 0);
		return (f + M * prior) / (n + M);
	}

	public void ensureClassExists(String cls, Cell cell, Col col) {
		int colIndex = col.getCol() - 1;
		String colTxt = col.getTxt();
		ColType colType = col.getType();

		String cellTxt = cell.getStringValue();

		if (colType == ColType.Num) {
			NumTable numTbl = (NumTable) tbls.get(colIndex);

			List<Cell> firstRow = Arrays.asList(new Cell(colTxt));
			List<Cell> row = Arrays.asList(cell);

			if (!numTbl.classStats.containsKey(cls)) { // initialize table for each class
				Tbl stats = new Tbl();
				stats.addRow(1, firstRow);
				numTbl.classStats.put(cls, stats);
			}
			Tbl stats = numTbl.classStats.get(cls);
			stats.addRow(stats.rows.size() + 2, row);
		} else {
			SymTable symTbl = (SymTable) tbls.get(colIndex);

			if (!symTbl.symClassCount.containsKey(cellTxt)) {
				symTbl.symClassCount.put(cellTxt, new HashMap<>());
			}

			Map<String, Integer> classStats = symTbl.symClassCount.get(cellTxt);
			classStats.putIfAbsent(cls, 0);
			classStats.put(cls, classStats.get(cls) + 1);
		}
	}

}
