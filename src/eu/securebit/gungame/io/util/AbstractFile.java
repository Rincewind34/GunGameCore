package eu.securebit.gungame.io.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.securebit.gungame.exception.GunGameException;

public abstract class AbstractFile {
	
	protected File file;
	protected FileConfiguration config;
	
	public AbstractFile(String path, String name) {
		this.file = FileValidatable.convert(path, name);
		this.config = YamlConfiguration.loadConfiguration(this.file);
		
		this.addDefaults();
		this.save();
	}
	
	public void save() {
		this.config.options().copyDefaults(true);
		try {
			this.config.save(this.file);
		} catch (IOException e) {
			throw new GunGameException(e.getMessage());
		}
	}
	
	public abstract void addDefaults();

}
