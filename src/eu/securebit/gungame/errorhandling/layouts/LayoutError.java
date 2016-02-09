package eu.securebit.gungame.errorhandling.layouts;

import java.util.Arrays;
import java.util.List;

public class LayoutError extends Layout {
	
	private String[] superErrors;
	
	public LayoutError(String message) {
		this(message, new String[0]);
	}
	
	public LayoutError(String message, String... superErrors) {
		super(message);
		
		this.superErrors = superErrors;
	}

	public List<String> getSuperErrors() {
		return Arrays.asList(this.superErrors);
	}
	
}
