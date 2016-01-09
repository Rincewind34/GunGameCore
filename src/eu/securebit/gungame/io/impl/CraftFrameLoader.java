
package eu.securebit.gungame.io.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import eu.securebit.gungame.exception.MalformedFrameException;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.io.FrameLoader;

public class CraftFrameLoader implements FrameLoader {

	private File jar;
	
	public CraftFrameLoader(File frameJar) throws MalformedFrameException, IOException {
		if (frameJar == null || !frameJar.exists()) {
			throw new FileNotFoundException("Cannot find the frame jarfile!");
		}
		
		if (frameJar.length() < 100) {
			throw new MalformedFrameException("Cannot parse jarfile!");
		}
		
		FileInputStream fis = new FileInputStream(frameJar);
		String string = new String(new byte[] { (byte) fis.read(), (byte) fis.read() });
		fis.close();
		if (!string.equals("PK")) {
			throw new MalformedFrameException("Cannot parse jarfile!");
		}
		
		this.jar = frameJar;
		
		
	}
	
	@Override
	public File getJar() {
		return this.jar;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Frame load() throws MalformedFrameException, MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		URL[] array = { this.jar.toURI().toURL() };
		ClassLoader parent = Frame.class.getClassLoader();
		URLClassLoader classloader = URLClassLoader.newInstance(array, parent);
		classloader.setDefaultAssertionStatus(true);

		Class<? extends Frame> mainClass = null; 
		
		for (String classname : this.readJar()) {
			Class<?> clazz = classloader.loadClass(classname);
			if (clazz.getSuperclass() == Frame.class) {
				mainClass = (Class<? extends Frame>) clazz;
			}
		}
		
		if (mainClass == null) {
			throw new MalformedFrameException("The frame doesn't contain a mainclass (subclass of abstract Frame)!");
		}
		
		return mainClass.newInstance();
	}
	
	@SuppressWarnings({ "resource" })
	private List<String> readJar() {
		List<String> classes = new ArrayList<String>();
		
		try {
			JarInputStream stream = new JarInputStream(new FileInputStream(this.jar));
			JarEntry entry = null;
 
			while (true) {
				entry = stream.getNextJarEntry();
				if (entry == null) {
					break;
				}
				
				if (entry.getName().endsWith(".class")) {
					String className = entry.getName().replaceAll("/", "\\.");
					String clazz = className.substring(0, className.lastIndexOf('.'));
					classes.add(clazz);
				}
			}
			
		} catch (Exception e) {
			return null;
		}
		
		return classes;
	}

}
