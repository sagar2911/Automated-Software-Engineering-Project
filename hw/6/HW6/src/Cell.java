
public class Cell {

	private String stringValue;
	private int intValue;
	private double doubleValue;

	private boolean isInt;
	
	public Cell(String value) {
		stringValue = value;
		try {
			doubleValue = Double.parseDouble(stringValue);
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
	
	public double getDoubleValue() {
		return doubleValue;
	}

	@Override
	public String toString() {
		return isInt ? stringValue : '\'' + stringValue + '\'';
	}

}
