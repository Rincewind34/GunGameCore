package eu.securebit.gungame.ioimpl.loader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import eu.securebit.gungame.addonsystem.Addon;
import eu.securebit.gungame.exception.MalformedJarException;
import eu.securebit.gungame.io.loader.AddonLoader;

public class CraftAddonLoader extends AbstractJarLoader<Addon> implements AddonLoader {

	public CraftAddonLoader(File addonJar) throws MalformedJarException, IOException {
		super(addonJar, Addon.class);
	}
	
	@Override
	public File getJar() {
		return this.getJarFile();
	}

	@Override
	public Addon load() throws MalformedJarException, MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return this.loadJar();
	}
	
}
