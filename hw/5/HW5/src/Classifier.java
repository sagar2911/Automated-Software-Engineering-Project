import java.util.List;

public interface Classifier {
	
	public void train(int r, List<Cell> row);

	public String classify(List<Cell> row);
	
	public int getGoalColIndex();
	
}
