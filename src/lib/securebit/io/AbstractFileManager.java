package lib.securebit.io;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.securebit.gungame.Main;

public abstract class AbstractFileManager implements FileManager {

	protected final File file;
	protected FileConfiguration config;
	
	public AbstractFileManager(String fileName) {
		this.file = new File(Main.instance().getDataFolder(), fileName);
		this.config = YamlConfiguration.loadConfiguration(this.file);
		this.config.options().copyDefaults(true);
		this.addDefaults();
		this.save();
	}
	
	@Override
	public void load() {
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}

	@Override
	public void save() {
		try {
			this.config.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public File getFile() {
		return this.file;
	}

	@Override
	public FileConfiguration getConfig() {
		return this.config;
	}

	public abstract void addDefaults();
}

