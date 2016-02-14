package eu.securebit.gungame.ioutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.bukkit.ChatColor;

import eu.securebit.gungame.exception.GunGameIOException;
import eu.securebit.gungame.exception.GunGameJarException;

public class IOUtil {
	
	public static File checkJarFile(File input) throws GunGameJarException, IOException {
		if (input == null || !input.exists()) {
			throw new FileNotFoundException("Cannot find the frame jarfile!");
		}
		
		if (input.length() < 100) {
			throw GunGameJarException.parsingUnable();
		}
		
		FileInputStream fis = new FileInputStream(input);
		String string = new String(new byte[] { (byte) fis.read(), (byte) fis.read() });
		fis.close();
		
		if (!string.equals("PK")) {
			throw GunGameJarException.parsingUnable();
		}
		
		return input;
	}
	
	@SuppressWarnings("resource")
	public static List<String> readJar(File jar) {
		try {
			jar = IOUtil.checkJarFile(jar);
		} catch (Exception ex) {
			throw GunGameIOException.fromOther(ex);
		}
		
		List<String> classes = new ArrayList<String>();
		
		try {
			JarInputStream stream = new JarInputStream(new FileInputStream(jar));
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
			
		} catch (Exception ex) {
			throw GunGameIOException.fromOther(ex);
		}
		
		return classes;
	}
	
	public static String prepare(String prefix, String str) {
		if (str == null) {
			return null;
		}
		
		return prefix + ChatColor.translateAlternateColorCodes('&', str);
	}
	
	public static String replace(String str, String variable, Object value) {
		if (str == null) {
			return str;
		}
		
		return str.replace("${" + variable + "}", String.valueOf(value));
	}
	
	public static String preparePath(String path) {
		String index = "plugins/GunGame";
		
		if (path.contains(index)) {
			path = path.substring(path.lastIndexOf(index) + index.length(), path.length());
		}
		
		return path;
	}
	
	public static void delete(File file) {
		if (file.isDirectory()) {
			for (File target : file.listFiles()) {
				IOUtil.delete(target);
			}
			
			file.delete();
		} else {
			file.delete();
		}
	}
	
}
