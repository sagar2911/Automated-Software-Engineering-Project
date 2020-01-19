
public class Cell {

	private String stringValue;
	private int intValue;

	private boolean isInt;
	
	public Cell(String value) {
		stringValue = value;
		try {
			intValue = Integer.parseInt(stringValue);
			isInt = true;
		} catch (NumberFormatException e) {
			isInt = false;
		}	
	}
	
	public String getStringValue() {
		return stringValue;
	}

	public int getIntValue() {
		return intValue;
	}

	@Override
	public String toString() {
		return isInt ? stringValue : '\'' + stringValue + '\'';
	}

}
