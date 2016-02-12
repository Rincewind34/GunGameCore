package eu.securebit.gungame.interpreter;

import eu.securebit.gungame.errorhandling.ErrorHandler;
import eu.securebit.gungame.errorhandling.objects.ThrownError;

public interface Interpreter {
	
	public abstract boolean wasSuccessful();
	
	public abstract boolean isInterpreted();
	
	public abstract ThrownError getErrorMain();
	
	public abstract ThrownError getErrorInterpret();
	
	public abstract ThrownError createError(String errorId);
	
	public abstract ErrorHandler getErrorHandler();
	
}
