
package eu.securebit.gungame.ioimpl.loader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import eu.securebit.gungame.exception.GunGameIOException;
import eu.securebit.gungame.exception.GunGameJarException;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.io.loader.FrameLoader;

public class CraftFrameLoader extends AbstractJarLoader<Frame> implements FrameLoader {

	public CraftFrameLoader(File frameJar) throws GunGameJarException, IOException {
		super(frameJar, Frame.class);
	}
	
	@Override
	public File getJar() {
		return this.getJarFile();
	}

	@Override
	public Frame load() {
		try {
			return this.loadJar();
		} catch (MalformedURLException ex) {
			throw GunGameIOException.fromOther(ex);
		} catch (InstantiationException ex) {
			throw GunGameIOException.fromOther(ex);
		} catch (IllegalAccessException ex) {
			throw GunGameIOException.fromOther(ex);
		} catch (ClassNotFoundException ex) {
			throw GunGameIOException.fromOther(ex);
		}
	}
	
}
