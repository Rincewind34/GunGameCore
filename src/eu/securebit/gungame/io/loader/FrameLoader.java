package eu.securebit.gungame.io.loader;

import java.io.File;
import java.net.MalformedURLException;

import eu.securebit.gungame.exception.MalformedJarException;
import eu.securebit.gungame.framework.Frame;

public interface FrameLoader {

	public abstract File getJar();
	
	public abstract Frame load() throws MalformedJarException, MalformedURLException,
			ClassNotFoundException, InstantiationException, IllegalAccessException;
}
