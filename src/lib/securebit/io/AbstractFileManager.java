package lib.securebit.io;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.securebit.gungame.exception.GunGameException;

public abstract class AbstractFileManager implements FileManager {

	protected final File file;
	protected FileConfiguration config;
	
	public AbstractFileManager(String path) throws GunGameException {
		this.file = new File(path);
		this.config = YamlConfiguration.loadConfiguration(this.file);
		this.config.options().copyDefaults(true);
		this.addDefaults();
		
		try {
			this.save();
		} catch (IOException e) {
			throw new GunGameException("Cannot save file, because it does not exists!");
		}
	}
	
	@Override
	public void load() {
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}

	@Override
	public void save() throws IOException {
		this.config.save(this.file);
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

