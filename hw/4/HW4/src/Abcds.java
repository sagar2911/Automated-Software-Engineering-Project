import java.util.List;

public class Abcds {
	
	private Classifier classifier;
	private Abcd abcd = new Abcd();

	public Abcds(Classifier classifier) {
		setClassifier(classifier);
	}
	
	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}
	
	public String run(String csv, int wait) {
		List<List<Cell>> table = new CSVParser(csv).getParsedTable();
		
		for (int r = 1; r <= table.size(); r++) {
			List<Cell> row = table.get(r - 1);
			if (r > wait) {
				String got = classifier.classify(row);
				int goalColIndex = classifier.getGoalColIndex();
				String want = row.get(goalColIndex).getStringValue();
				abcd.one(want, got);
			}
			classifier.train(r, row);
		}
		
		return abcd.report();
	}

}
