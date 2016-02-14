package eu.securebit.gungame.errorhandling;

import java.util.Map;

import eu.securebit.gungame.errorhandling.objects.ThrowableObject;
import eu.securebit.gungame.errorhandling.objects.ThrownError;

public interface ErrorHandler {
	
	public abstract void throwError(String objectId);
	
	public abstract void throwError(String objectId, String causeId);
	
	public abstract void throwError(String objectId, ThrownError cause);
	
	public abstract void throwError(ThrowableObject<?> object);
	
	public abstract void throwError(ThrowableObject<?> object, String causeId);
	
	public abstract void throwError(ThrowableObject<?> object, ThrownError cause);
	
	public abstract boolean isErrorPresent(String errorId);
	
	public abstract boolean isErrorPresent(ThrownError error);
	
	public abstract ThrownError getTriggerCause(String errorId);
	
	public abstract ThrownError getTriggerCause(ThrownError error);
	
	public abstract ThrownError getCause(String errorId);
	
	public abstract ThrownError getCause(ThrownError error);
	
	public abstract ThrownError getTrigger(String errorId);
	
	public abstract ThrownError getTrigger(ThrownError error);
	
	public abstract Map<ThrownError, ThrownError> getErrors();
	
}
