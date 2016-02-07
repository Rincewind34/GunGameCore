package eu.securebit.gungame.io.configs;

import org.bukkit.inventory.ItemStack;

import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.exception.InvalidLevelException;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;

public interface FileLevels extends FileIdentifyable, FileConfig {
	
	public static final String ERROR_MAIN = 			"7|003|000|000-VAR";
	
	public static final String ERROR_LOAD = 			"7|003|001|000-VAR";
	
	public static final String ERROR_FOLDER = 			"7|003|001|001-VAR";
	
	public static final String ERROR_CREATE = 			"7|003|001|002-VAR";
	
	public static final String ERROR_MALFORMED = 		"7|003|001|003-VAR";
	
	public static final String ERROR_LEVELCOUNT = 		"7|003|002|000-VAR";
	
	public static Error createErrorMain() {
		return new SimpleError("In the levelsfile 'VAR' occured an error!");
	}
	
	public static Error createErrorLoad() {
		return new SimpleError("The levelsfile 'VAR' could not be loaded!");
	}
	
	public static Error createErrorFolder() {
		return new SimpleFixableError("The levelsfile 'VAR' is a directory!", FileLevels.ERROR_MAIN, () -> {
			// TODO fix path
		});
	}
	
	public static Error createErrorCreate() {
		return new SimpleError("The levelsfile 'VAR' could not be created!", FileLevels.ERROR_LOAD);
	}
	
	public static Error createErrorMalformed() {
		return new SimpleFixableError("The levelsfile 'VAR' is malformed!", FileLevels.ERROR_LOAD, () -> {
			// TODO fix path / delete
		});
	}
	
	public static Error createErrorLevelCount() {
		return new SimpleError("The levelcount in the levelsfile 'VAR' has to be greater than 0!", FileLevels.ERROR_MAIN);
	}
	
	
	public abstract void setLevel(int level, ItemStack[] items) throws InvalidLevelException;
	
	public abstract boolean delete() throws InvalidLevelException;
	
	public abstract int getLevelCount();
	
	public abstract ItemStack[] getLevel(int level) throws InvalidLevelException;

}
