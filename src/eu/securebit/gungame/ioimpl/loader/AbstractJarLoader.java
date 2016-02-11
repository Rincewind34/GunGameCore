package eu.securebit.gungame.ioimpl.loader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.exception.GunGameJarException;
import eu.securebit.gungame.ioutil.IOUtil;

public class AbstractJarLoader<T> {
	
	private File jar;
	private Class<T> mainClassType;
	
	public AbstractJarLoader(File frameJar, Class<T> mainClassType) throws IOException {
		this.jar = IOUtil.checkJarFile(frameJar);
		this.mainClassType = mainClassType;
	}
	
	@SuppressWarnings("unchecked")
	protected final T loadJar() throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		URL[] array = { this.jar.toURI().toURL() };
		ClassLoader parent = Main.class.getClassLoader();
		URLClassLoader classloader = URLClassLoader.newInstance(array, parent);
		classloader.setDefaultAssertionStatus(true);

		Class<? extends T> mainClass = null; 
		
		for (String classname : IOUtil.readJar(this.jar)) {
			Class<?> clazz = classloader.loadClass(classname);
			
			if (clazz.getSuperclass() == this.mainClassType) {
				mainClass = (Class<? extends T>) clazz;
			}
		}
		
		if (mainClass == null) {
			throw GunGameJarException.noMainclass(this.mainClassType);
		}
		
		return mainClass.newInstance();
	}
	
	protected final File getJarFile() {
		return this.jar;
	}

	
}
