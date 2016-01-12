package eu.securebit.gungame.io;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.util.FileValidatable;

public abstract class AbstractFileConfig {
	
	private File file;
	protected FileConfiguration config;
	
	public AbstractFileConfig(String path, String name, String type) {
		this.file = FileValidatable.convert(path, name);
		
		if (!this.file.exists()) {
			if (Main.instance().getFileRegistry().contains(this.file.getAbsolutePath())) {
				Main.instance().getFileRegistry().remove(this.file.getAbsolutePath());
			}
			
			Main.instance().getFileRegistry().add(this.file.getAbsolutePath(), type);
		} else {
			if (!Main.instance().getFileRegistry().contains(this.file.getAbsolutePath())) {
				this.file.delete();
				Main.instance().getFileRegistry().add(this.file.getAbsolutePath(), type);
			} else {
				if (!Main.instance().getFileRegistry().get(this.file.getAbsolutePath()).equals(type)) {
					Main.instance().getFileRegistry().remove(this.file.getAbsolutePath());
					this.file.delete();
					Main.instance().getFileRegistry().add(this.file.getAbsolutePath(), type);
				}
			}
		}
		
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
