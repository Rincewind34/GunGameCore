package eu.securebit.gungame.io.loader;

import java.io.File;
import java.net.MalformedURLException;

import eu.securebit.gungame.addonsystem.Addon;

public interface AddonLoader {
	
	public abstract File getJar();
	
	public abstract Addon load() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException;

}
