package eu.securebit.gungame.errors;

import java.util.List;

public abstract class Error {
	
	public abstract String getMessage();
	
	public abstract List<String> getSuperErrors();
	
}
