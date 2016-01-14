package eu.securebit.gungame.io;

import java.io.File;

import eu.securebit.gungame.util.ColorSet;

public interface FileBootConfig {

	public abstract void initialize();
	
	public abstract ConfigError[] validate();
	
	public abstract File getBootFolder();
	
	public abstract File getFrameJar();
	
	public abstract ColorSet getColorSet();
	
}
