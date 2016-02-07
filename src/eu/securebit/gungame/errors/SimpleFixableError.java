package eu.securebit.gungame.errors;

import java.util.Arrays;
import java.util.List;

public class SimpleFixableError extends FixableError {
	
	private Runnable action;
	
	private String message;
	private List<String> superError;
	
	public SimpleFixableError(String message, String superError, Runnable action) {
		this(message, action, superError);
	}
	
	public SimpleFixableError(String message, Runnable action, String... superError) {
		this.message = message;
		this.action = action;
		this.superError = Arrays.asList(superError);
	}
	
	@Override
	public void fix() {
		this.action.run();
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
