package eu.securebit.gungame.io;

import java.io.File;

public interface FileConfig {

	void initialize();
	
	boolean isValid();
	
	File getBootFolder();
	
	File getFrameJar();
}
