package lib.securebit.io;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;

import eu.securebit.gungame.exception.GunGameException;

public interface FileManager {
	
	public abstract void load() throws GunGameException;
	
	public abstract void save() throws IOException;
	
	public abstract File getFile();
	
	public abstract FileConfiguration getConfig();
}