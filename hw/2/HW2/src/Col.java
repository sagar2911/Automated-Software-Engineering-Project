
public class Col {
	
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

	@Override
	public String toString() {
		return '\'' + txt + '\'';
	}

}
