package eu.securebit.gungame.io.configs;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.exception.GunGameFixException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.ioutil.IOUtil;

public interface FileLevels extends FileGunGameConfig {
	
	public static final String ERROR_LOAD = 			"Error-7310-VAR0";
	
	public static final String ERROR_FOLDER = 			"Error-7311-VAR0";
	
	public static final String ERROR_CREATE = 			"Error-7312-VAR0";
	
	public static final String ERROR_MALFORMED = 		"Error-7313-VAR0";
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The levelsfile 'VAR0' could not be loaded!");
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The levelsfile 'VAR0' is a directory!", FileLevels.ERROR_LOAD, (variables) -> {
			if (variables.length == 1) {
				IOUtil.delete(Core.getRootDirectory().getFile(variables[0]));
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the directory 'VAR0'.");
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The levelsfile 'VAR0' could not be created!", FileLevels.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformed() {
		return new LayoutErrorFixable("The levelsfile 'VAR0' is malformed!", FileLevels.ERROR_LOAD, (variables) -> {
			if (variables.length == 1) {
				IOUtil.delete(Core.getRootDirectory().getFile(variables[0]));
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the file 'VAR0'.");
	}
	
	
	public abstract void setLevels(List<ItemStack[]> levels);
	
	public abstract List<ItemStack[]> getLevels();

}
