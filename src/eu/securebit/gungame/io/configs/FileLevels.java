package eu.securebit.gungame.io.configs;

import org.bukkit.inventory.ItemStack;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.exception.InvalidLevelException;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;

public interface FileLevels extends FileIdentifyable, FileConfig {
	
	public static final String ERROR_MAIN = 			"Error-7300-VAR0";
	
	public static final String ERROR_LOAD = 			"Error-7310-VAR0";
	
	public static final String ERROR_FOLDER = 			"Error-7311-VAR0";
	
	public static final String ERROR_CREATE = 			"Error-7312-VAR0";
	
	public static final String ERROR_MALFORMED = 		"Error-7313-VAR0";
	
	public static final String ERROR_LEVELCOUNT = 		"Error-7320-VAR0";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("In the levelsfile 'VAR0' occured an error!");
	}
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The levelsfile 'VAR0' could not be loaded!");
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The levelsfile 'VAR0' is a directory!", FileLevels.ERROR_MAIN, () -> {
			// TODO fix path
		});
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The levelsfile 'VAR0' could not be created!", FileLevels.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformed() {
		return new LayoutErrorFixable("The levelsfile 'VAR0' is malformed!", FileLevels.ERROR_LOAD, () -> {
			// TODO fix path / delete
		});
	}
	
	public static LayoutError createErrorLevelCount() {
		return new LayoutError("The levelcount in the levelsfile 'VAR0' has to be greater than 0!", FileLevels.ERROR_MAIN);
	}
	
	
	public abstract void setLevel(int level, ItemStack[] items) throws InvalidLevelException;
	
	public abstract boolean delete() throws InvalidLevelException;
	
	public abstract int getLevelCount();
	
	public abstract ItemStack[] getLevel(int level) throws InvalidLevelException;

}
