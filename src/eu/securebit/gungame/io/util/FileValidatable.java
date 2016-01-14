package eu.securebit.gungame.io.util;

import java.io.File;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.exception.MalformedConfigException;

public interface FileValidatable {
	
	public static File convert(String path, String name) {
		return new File(Main.instance().getDataFolder() + File.separator + path, name);
	}
	
	public abstract void validate() throws MalformedConfigException;
}
