
package eu.securebit.gungame.io.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import eu.securebit.gungame.exception.MalformedJarException;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.io.FrameLoader;
import eu.securebit.gungame.io.util.IOUtil;

public class CraftFrameLoader implements FrameLoader {

	private File jar;
	
	public CraftFrameLoader(File frameJar) throws MalformedJarException, IOException {
		this.jar = IOUtil.checkJarFile(frameJar);
	}
	
	@Override
	public File getJar() {
		return this.jar;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Frame load() throws MalformedJarException, MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		URL[] array = { this.jar.toURI().toURL() };
		ClassLoader parent = Frame.class.getClassLoader();
		URLClassLoader classloader = URLClassLoader.newInstance(array, parent);
		classloader.setDefaultAssertionStatus(true);

		Class<? extends Frame> mainClass = null; 
		
		for (String classname : IOUtil.readJar(this.jar)) {
			Class<?> clazz = classloader.loadClass(classname);
			if (clazz.getSuperclass() == Frame.class) {
				mainClass = (Class<? extends Frame>) clazz;
			}
		}
		
		if (mainClass == null) {
			throw new MalformedJarException("The frame doesn't contain a mainclass (subclass of abstract Frame)!");
		}
		
		return mainClass.newInstance();
	}
	
}
