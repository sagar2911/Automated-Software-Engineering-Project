
public abstract class Col {
	
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
	
	public abstract ColType getType();
	
	public abstract String dump();

	@Override
	public String toString() {
		return '\'' + txt + '\'';
	}
	
}
