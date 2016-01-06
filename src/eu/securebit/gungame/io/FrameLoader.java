package eu.securebit.gungame.io;

import java.io.File;

import eu.securebit.gungame.framework.Frame;

public interface FrameLoader {

	public abstract File getJar();
	
	public abstract Frame load() throws Exception; // Catch this!
}
