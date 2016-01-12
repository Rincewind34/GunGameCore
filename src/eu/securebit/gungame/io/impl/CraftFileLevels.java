package eu.securebit.gungame.io.impl;

import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import eu.securebit.gungame.exception.InvalidLevelException;
import eu.securebit.gungame.exception.MalformedConfigException;
import eu.securebit.gungame.io.AbstractFileConfig;
import eu.securebit.gungame.io.FileLevels;
import eu.securebit.gungame.util.ItemSerializer;

public class CraftFileLevels extends AbstractFileConfig implements FileLevels {
	
	public CraftFileLevels(String path, String name) {
		super(path, name, "levels");
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

		List<String> data = super.config.getStringList("levels");
		
		if (level < data.size()) {
			data.set(level, ItemSerializer.serializeInventory(items));
		} else {
			data.add(ItemSerializer.serializeInventory(items));
		}
		
		super.config.set("levels", data);
		this.save();
	}
	
	@Override
	public boolean delete() throws InvalidLevelException {
		if (this.getLevelCount() < 1) {
			throw new InvalidLevelException("There is no level to remove.");
		}
		
		List<String> levels = super.config.getStringList("levels");
		levels.remove(levels.size() - 1);
		super.config.set("levels", levels);
		this.save();
		
		return true;
	}

	@Override
	public int getLevelCount() {
		return super.config.getStringList("levels").size();
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
			
		List<String> data = super.config.getStringList("levels");
		ItemStack[] items = ItemSerializer.deserializeInventory(data.get(level));
		
		return items;
	}
	
	@Override
	public void addDefaults() {
		super.config.addDefault("levels", Arrays.asList());
	}

}
