
public class Cell {
	
	private static String parseValue(String value) {
		int offset = value.indexOf("#"); // remove comments
		if (offset != -1) {
		    value = value.substring(0, offset);
		}
		value = value.trim(); // remove trailing whitespace
		return value;
	}

	private boolean isInt;
	
	private String stringValue;

	private int intValue;
	
	public Cell(String value) {
		stringValue = parseValue(value);
		try {
			intValue = Integer.parseInt(stringValue);
			isInt = true;
		} catch (NumberFormatException e) {
			isInt = false;
		}	
	}
	
	public boolean isInt() {
		return isInt;
	}

	public String getStringValue() {
		return stringValue;
	}

	public int getIntValue() {
		return intValue;
	}
	
	@Override
	public String toString() {
		return isInt ? String.valueOf(intValue) : '\'' + stringValue + '\'';
	}

}
