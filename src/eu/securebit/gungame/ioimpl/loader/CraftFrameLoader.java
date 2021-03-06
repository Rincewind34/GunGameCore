
package eu.securebit.gungame.ioimpl.loader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import eu.securebit.gungame.exception.MalformedJarException;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.io.loader.FrameLoader;

public class CraftFrameLoader extends AbstractJarLoader<Frame> implements FrameLoader {

	public CraftFrameLoader(File frameJar) throws MalformedJarException, IOException {
		super(frameJar, Frame.class);
	}
	
	@Override
	public File getJar() {
		return this.getJarFile();
	}

	@Override
	public Frame load() throws MalformedJarException, MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		return this.loadJar();
	}
	
}
