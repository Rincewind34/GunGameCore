package eu.securebit.gungame.ioimpl.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import eu.securebit.gungame.errors.ErrorHandler;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.exception.InvalidLevelException;
import eu.securebit.gungame.io.configs.FileLevels;
import eu.securebit.gungame.ioimpl.abstracts.AbstractConfig;
import eu.securebit.gungame.ioutil.ItemSerializer;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileLevels extends AbstractConfig implements FileLevels {
	
	private static final List<ConfigDefault> defaults = new ArrayList<>();
	
	static {
		CraftFileLevels.defaults.add(new ConfigDefault("levels", Arrays.asList(), null));
	}
	
	
	public CraftFileLevels(File file, ErrorHandler handler) {
		super(file, handler, FileLevels.ERROR_MAIN, FileLevels.ERROR_LOAD, FileLevels.ERROR_FOLDER, FileLevels.ERROR_CREATE, FileLevels.ERROR_MALFORMED);
	}
	
	@Override
	public void setLevel(int level, ItemStack[] items) throws InvalidLevelException {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		
		List<String> data = this.getLevels();
		
		if (level <= 0) {
			throw new InvalidLevelException("The level must be an integer greater than 0.");
		}
		
		if (level > data.size() + 1) {
			throw new InvalidLevelException("Fragmentations are not allowed. Please select an level between 1 and " + (data.size() + 1));
		}
		
		--level; // collection-operations are working with 0 as the first number!

		if (level < data.size()) {
			data.set(level, ItemSerializer.serializeInventory(items));
		} else {
			data.add(ItemSerializer.serializeInventory(items));
		}
		
		this.setLevels(data);
	}
	
	@Override
	public boolean delete() throws InvalidLevelException {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		
		if (this.getLevelCount() < 1) {
			throw new InvalidLevelException("There is no level to remove.");
		}
		
		List<String> levels = this.getLevels();
		levels.remove(levels.size() - 1);
		
		this.setLevels(levels);
		return true;
	}

	@Override
	public int getLevelCount() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		
		return this.getLevels().size();
	}

	@Override
	public ItemStack[] getLevel(int level) throws InvalidLevelException {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		
		if (level <= 0) {
			throw new InvalidLevelException("The level must be an integer greater than 0.");
		}
		
		if (level > this.getLevelCount()) {
			throw new InvalidLevelException("The maximum level is " + this.getLevelCount() + " (given: " + level + ").");
		}
		
		--level; // collection-operations are working with 0 as the first number!
			
		List<String> data = this.getLevels();
		ItemStack[] items = ItemSerializer.deserializeInventory(data.get(level));
		
		return items;
	}
	
	@Override
	public void addDefaults() {
		for (ConfigDefault entry : CraftFileLevels.defaults) {
			super.config.addDefault(entry.getPath(), entry.getValue());
		}
	}

	@Override
	public String getAbsolutePath() {
		return super.file.getAbsolutePath();
	}
	
	@Override
	public String getIdentifier() {
		return "levels";
	}
	
	@Override
	public void validate() {
		for (ConfigDefault entry : CraftFileLevels.defaults) {
			if (!entry.validate(super.config)) {
				this.throwError(FileLevels.ERROR_MALFORMED);
				break;
			}
		}
		
		if (this.getLevelCount() < 1) {
			this.throwError(FileLevels.ERROR_LEVELCOUNT);
		}
	}
	
	private List<String> getLevels() {
		return super.config.getStringList("levels");
	}
	
	private void setLevels(List<String> levels) {
		super.config.set("levels", levels);
		super.save();
	}

}
