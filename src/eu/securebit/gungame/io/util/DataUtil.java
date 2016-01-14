package eu.securebit.gungame.io.util;

import eu.securebit.gungame.exception.CSVParseException;

public class DataUtil {

	public static String getFromCSV(String csv, int index) throws CSVParseException {
		String[] values = csv.split(",");
		if (index >= values.length) {
			throw new CSVParseException("Cannot access index " + index + ", the CSV contains only " + values.length + " entries.");
		}
		return csv.split(",")[index];
	}
	
	public static String toCSV(Object... values) {
		StringBuilder builder = new StringBuilder();
		for (Object object : values) {
			builder.append(object.toString()).append(",");
		}
		builder.setLength(builder.length() - 1);
		return builder.toString();
	}
}
