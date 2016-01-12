package eu.securebit.gungame.io.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.exception.MalformedConfigException;
import eu.securebit.gungame.io.FileConfigRegistry;

public class CraftFileConfigRegistry implements FileConfigRegistry {
	
	private File file;
	private FileConfiguration config;
	
	public CraftFileConfigRegistry() {
		this.file = new File(Main.instance().getDataFolder(), ".fileregistry");
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

	@Override
	public void validate() throws MalformedConfigException {
		
	}

	@Override
	public void add(String file, String type) {
		List<String> entry = this.config.getStringList("files");
		entry.add(file + "==" + type);
		this.config.set("files", entry);
		this.save();
	}

	@Override
	public void remove(String file) {
		List<String> entry = this.config.getStringList("files");
		
		for (String element : entry) {
			if (element.split("==")[0].equals(file)) {
				entry.remove(element);
				break;
			}
		}
		
		this.config.set("files", entry);
		this.save();
	}
	
	@Override
	public boolean contains(String file) {
		List<String> entry = this.config.getStringList("files");
		
		for (String element : entry) {
			System.out.println(Arrays.asList(element.split("==")));
			if (element.split("==")[0].equals(file)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String get(String file) {
		List<String> entry = this.config.getStringList("files");
		
		for (String element : entry) {
			if (element.split("==")[0].equals(file)) {
				return element.split("==")[1];
			}
		}
		
		return null;
	}
	
	@Override
	public List<String> getEntry() {
		return this.config.getStringList("files");
	}
	
	public void addDefaults() {
		this.config.addDefault("files", Arrays.asList());
	}

}
