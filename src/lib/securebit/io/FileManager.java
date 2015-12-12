package lib.securebit.io;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public interface FileManager {
	
	public abstract void load();
	
	public abstract void save();
	
	public abstract File getFile();
	
	public abstract FileConfiguration getConfig();
}