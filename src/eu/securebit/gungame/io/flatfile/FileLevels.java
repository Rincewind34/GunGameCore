package eu.securebit.gungame.io.flatfile;

import java.util.Arrays;
import java.util.List;

import lib.securebit.io.AbstractFileManager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.securebit.gungame.exception.InvalidLevelException;
import eu.securebit.gungame.exception.MalformedConfigException;
import eu.securebit.gungame.io.LevelConfig;
import eu.securebit.gungame.io.util.ItemSerializer;

public class FileLevels extends AbstractFileManager implements LevelConfig {
	
	public FileLevels() {
		super("levels.yml");
	}

	@Override
	public void addDefaults() {
		super.config.addDefault("Levels", Arrays.asList());
	}
	
	
	// ------------------------------------ //
	// ---------- Implementation ---------- //
	// ------------------------------------ //
	
	
	@Override
	public void setItems(Player p, int level) throws InvalidLevelException {
		if (level <= 0) {
			throw new InvalidLevelException("The level must be an integer greater than 0.");
		}
		
		if (level > this.getLevelCount() + 1) {
			throw new InvalidLevelException(
					"Fragmentations are not allowed. Please select an level between 1 and " + (this.getLevelCount() + 1));
		}
		
		--level; // collection-operations are working with 0 as the first number!
				
		List<String> data = super.config.getStringList("Levels");
		if (level < data.size()) {
			data.set(level, ItemSerializer.serializeInventory(p.getInventory()));
		} else {
			data.add(ItemSerializer.serializeInventory(p.getInventory()));
		}
		
		super.config.set("Levels", data);
		this.save();
	}

	@Override
	public void give(Player p, int level) throws InvalidLevelException {
		if (level <= 0) {
			throw new InvalidLevelException("The level must be an integer greater than 0.");
		}
		
		if (level > this.getLevelCount()) {
			throw new InvalidLevelException("The maximum level is " + this.getLevelCount() + " (given: " + level + ").");
		}
		
		--level; // collection-operations are working with 0 as the first number!
			
		List<String> data = super.config.getStringList("Levels");
		ItemStack[] items = ItemSerializer.deserializeInventory(data.get(level));
		
		for (ItemStack item : items) {
			if (item == null) {
				continue;
			}
			
			ItemMeta meta = item.getItemMeta();
			
			if (meta == null) {
				continue;
			}
			
			meta.spigot().setUnbreakable(true);
			item.setItemMeta(meta);
		}
		
		p.getInventory().clear();
		p.getInventory().setArmorContents(new ItemStack[] { null, null, null, null });
		p.getInventory().setContents(Arrays.copyOfRange(items, 0, 36));
		p.getInventory().setArmorContents(Arrays.copyOfRange(items, 36, 40));
		p.updateInventory();
	}

	@Override
	public int getLevelCount() {
		return super.config.getStringList("Levels").size();
	}
	
	@Override
	public boolean delete() throws InvalidLevelException {
		if (this.getLevelCount() < 1) {
			throw new InvalidLevelException("There is no level to remove.");
		}
		
		List<String> levels = super.config.getStringList("Levels");
		levels.remove(levels.size() - 1);
		super.config.set("Levels", levels);
		this.save();
		return true;
	}
	
	
	// -------------------------------- //
	// ---------- Validation ---------- //
	// -------------------------------- //

	
	@Override
	public void validate() throws MalformedConfigException {
		if (this.getLevelCount() < 1) {
			throw new MalformedConfigException("You have to create at least one level!");
		}
		
		//CHECK STARTLEVEL
	}
	
}
