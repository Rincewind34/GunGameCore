package eu.securebit.gungame.io.abstracts;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.ioutil.FileType;

public interface SimpleFile {
	
	public abstract void create();
	
	public abstract boolean isReady();
	
	public abstract boolean isCreated();
	
	public abstract FileType getFileType();
	
	public abstract CraftErrorHandler getErrorHandler();
	
}
