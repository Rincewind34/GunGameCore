package eu.securebit.gungame.io;

import java.io.File;
import java.net.MalformedURLException;

import eu.securebit.gungame.addonsystem.Addon;
import eu.securebit.gungame.exception.MalformedJarException;

public interface AddonLoader {
	
	public abstract File getJar();
	
	public abstract Addon load() throws MalformedJarException, MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException;

}
