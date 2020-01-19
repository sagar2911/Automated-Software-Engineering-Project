
public abstract class Col {
	
	public int w = 1;
	
	protected int col;
	protected String txt;
	
	public Col(int col, String txt) {
		this.col = col;
		this.txt = txt;
	}

	public int getCol() {
		return col;
	}

	public String getTxt() {
		return txt;
	}
	
	public abstract double dist(Cell cell, Cell cell2);
	
	public abstract ColType getType();
	
	public abstract String dump();

	@Override
	public String toString() {
		return '\'' + txt + '\'';
	}
	
}
