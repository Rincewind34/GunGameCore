package eu.securebit.gungame.io.configs;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.exception.GunGameFixException;
import eu.securebit.gungame.ioutil.IOUtil;

public interface FileOptions extends FileGunGameConfig {
	
	public static final String ERROR_LOAD = 			"Error-7510-VAR0";
	
	public static final String ERROR_FOLDER =			"Error-7511-VAR0";
	
	public static final String ERROR_CREATE = 			"Error-7512-VAR0";
	
	public static final String ERROR_MALFORMED = 		"Error-7513-VAR0";
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The optionsfile 'VAR0' could not be loaded!");
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The optionsfile 'VAR0' is a directory!", FileOptions.ERROR_LOAD, (variables) -> {
			if (variables.length == 1) {
				IOUtil.delete(Main.instance().getRootDirectory().getFile(variables[0]));
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the directory 'VAR0'.");
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The optionsfile 'VAR0' could not be created!", FileOptions.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformed() {
		return new LayoutErrorFixable("The optionsfile 'VAR0' is malformed!", FileOptions.ERROR_LOAD, (variables) -> {
			if (variables.length == 1) {
				IOUtil.delete(Main.instance().getRootDirectory().getFile(variables[0]));
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the file 'VAR0'.");
	}
	
	
	public abstract void setLevelResetAfterDeath(boolean enabled);
	
	public abstract void setAutoRespawn(boolean enabled);
	
	public abstract void setLevelDowngradeOnNaturalDeath(boolean enabled);
	
	public abstract void setStartLevel(int level);
	
	public abstract boolean isLevelResetAfterDeath();
	
	public abstract boolean isAutoRespawn();
	
	public abstract boolean isLevelDowngradeOnNaturalDeath();
	
	public abstract boolean isPremiumKickEnabled();
	
	public abstract int getStartLevel();
	
	public abstract int getLobbyCountdownLength();
	
	public abstract int getLobbyPremiumSlots();
	
}
