package eu.securebit.gungame.io.abstracts;

import eu.securebit.gungame.errorhandling.ErrorHandler;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.ioutil.FileType;

public interface SimpleFile {
	
	public abstract void create();
	
	public abstract void checkReady();
	
	public abstract boolean isCreated();
	
	public abstract boolean isReady();
	
	public abstract ThrownError getErrorLoad();
	
	public abstract ThrownError getErrorFileType();
	
	public abstract ThrownError getErrorCreate();
	
	public abstract ThrownError createError(String errorId);
	
	public abstract FileType getFileType();
	
	public abstract ErrorHandler getErrorHandler();
	
}
