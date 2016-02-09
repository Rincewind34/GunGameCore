package eu.securebit.gungame.errorhandling;

import eu.securebit.gungame.errorhandling.objects.ThrowableObject;
import eu.securebit.gungame.errorhandling.objects.ThrownError;

public interface ErrorHandler {
	
	public abstract void throwError(String objectId);
	
	public abstract void throwError(String objectId, String causeId);
	
	public abstract void throwError(ThrowableObject<?> object);
	
	public abstract void throwError(ThrowableObject<?> object, ThrownError cause);
	
	public abstract boolean isErrorPresent(ThrownError error);
	
	public abstract ThrownError getCause(ThrownError error);
	
}