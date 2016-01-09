package eu.securebit.gungame.io;

import java.io.File;
import java.net.MalformedURLException;

import eu.securebit.gungame.exception.MalformedFrameException;
import eu.securebit.gungame.framework.Frame;

public interface FrameLoader {

	public abstract File getJar();
	
	public abstract Frame load() throws MalformedFrameException, MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException; // Catch this!
}
