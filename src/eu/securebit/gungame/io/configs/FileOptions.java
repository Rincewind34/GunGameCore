package eu.securebit.gungame.io.configs;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;

public interface FileOptions extends FileIdentifyable, FileConfig {
	
	public static final String ERROR_MAIN = 			"Error-7500-VAR0";
	
	public static final String ERROR_LOAD = 			"Error-7510-VAR0";
	
	public static final String ERROR_FOLDER =			"Error-7511-VAR0";
	
	public static final String ERROR_CREATE = 			"Error-7512-VAR0";
	
	public static final String ERROR_MALFORMED = 		"Error-7513-VAR0";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("In the optionsfile 'VAR0' occured an error!");
	}
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The optionsfile 'VAR0' could not be loaded!", FileOptions.ERROR_MAIN);
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The optionsfile 'VAR0' is a directory!", FileOptions.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The optionsfile 'VAR0' could not be created!", FileOptions.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformed() {
		return new LayoutErrorFixable("The optionsfile 'VAR0' is malformed!", FileOptions.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	
	public abstract boolean isLevelResetAfterDeath();
	
	public abstract boolean isAutoRespawn();
	
	public abstract boolean isLevelDowngradeOnNaturalDeath();
	
	public abstract void setLevelResetAfterDeath(boolean enabled);
	
	public abstract void setAutoRespawn(boolean enabled);
	
	public abstract void setLevelDowngradeOnNaturalDeath(boolean enabled);
	
}
