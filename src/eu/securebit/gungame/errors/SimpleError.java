package eu.securebit.gungame.errors;

import java.util.Arrays;
import java.util.List;

public class SimpleError extends Error {
	
	private String message;
	private List<String> superError;
	
	public SimpleError(String message) {
		this(message, new String[0]);
	}
	
	public SimpleError(String message, String... superError) {
		this.message = message;
		this.superError = Arrays.asList(superError);
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public List<String> getSuperErrors() {
		return this.superError;
	}

}
