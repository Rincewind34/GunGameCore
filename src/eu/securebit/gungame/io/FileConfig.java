package eu.securebit.gungame.io;

import java.io.File;

public interface FileConfig {

	public abstract void initialize();
	
	public abstract boolean isValid();
	
	public abstract File getBootFolder();
	
	public abstract File getFrameJar();
}
