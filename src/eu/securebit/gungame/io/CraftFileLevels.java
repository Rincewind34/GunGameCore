package eu.securebit.gungame.io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.exception.InvalidLevelException;
import eu.securebit.gungame.exception.MalformedConfigException;
import eu.securebit.gungame.util.FileValidatable;
import eu.securebit.gungame.util.ItemSerializer;

public class CraftFileLevels implements FileLevels {
	
	private File file;
	private FileConfiguration config;
	
	public CraftFileLevels(String path, String name) {
		this.file = FileValidatable.convert(path, name);
		this.config = YamlConfiguration.loadConfiguration(this.file);
		this.addDefaults();
		this.save();
	}
	
	@Override
	public void validate() throws MalformedConfigException {
		if (this.getLevelCount() < 1) {
			throw new MalformedConfigException("You have to create at least one level!");
		}
	}

	@Override
	public void setLevel(int level, ItemStack[] items) throws InvalidLevelException {
		if (level <= 0) {
			throw new InvalidLevelException("The level must be an integer greater than 0.");
		}
		
		if (level > this.getLevelCount() + 1) {
			throw new InvalidLevelException("Fragmentations are not allowed. Please select an level between 1 and " + (this.getLevelCount() + 1));
		}
		
		--level; // collection-operations are working with 0 as the first number!
				
		List<String> data = this.config.getStringList("levels");
		
		if (level < data.size()) {
			data.set(level, ItemSerializer.serializeInventory(items));
		} else {
			data.add(ItemSerializer.serializeInventory(items));
		}
		
		this.config.set("levels", data);
		this.save();
	}
	
	@Override
	public void save() {
		this.config.options().copyDefaults(true);
		
		try {
			this.config.save(this.file);
		} catch (IOException e) {
			throw new GunGameException(e.getMessage());
		}
	}

	@Override
	public boolean delete() throws InvalidLevelException {
		if (this.getLevelCount() < 1) {
			throw new InvalidLevelException("There is no level to remove.");
		}
		
		List<String> levels = this.config.getStringList("levels");
		levels.remove(levels.size() - 1);
		this.config.set("levels", levels);
		this.save();
		
		return true;
	}

	@Override
	public int getLevelCount() {
		return this.config.getStringList("levels").size();
	}

	@Override
	public ItemStack[] getLevel(int level) throws InvalidLevelException {
		if (level <= 0) {
			throw new InvalidLevelException("The level must be an integer greater than 0.");
		}
		
		if (level > this.getLevelCount()) {
			throw new InvalidLevelException("The maximum level is " + this.getLevelCount() + " (given: " + level + ").");
		}
		
		--level; // collection-operations are working with 0 as the first number!
			
		List<String> data = this.config.getStringList("levels");
		ItemStack[] items = ItemSerializer.deserializeInventory(data.get(level));
		
		return items;
	}
	
	private void addDefaults() {
		this.config.addDefault("levels", Arrays.asList());
	}

}
