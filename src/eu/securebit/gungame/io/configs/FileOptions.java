package eu.securebit.gungame.io.configs;

import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;

public interface FileOptions extends FileIdentifyable, FileConfig {
	
	public static final String ERROR_MAIN = 			"7500-VAR0";
	
	public static final String ERROR_LOAD = 			"7510-VAR0";
	
	public static final String ERROR_FOLDER =			"7511-VAR0";
	
	public static final String ERROR_CREATE = 			"7512-VAR0";
	
	public static final String ERROR_MALFORMED = 		"7513-VAR0";
	
	public static Error createErrorMain() {
		return new SimpleError("In the optionsfile 'VAR0' occured an error!");
	}
	
	public static Error createErrorLoad() {
		return new SimpleError("The optionsfile 'VAR0' could not be loaded!", FileOptions.ERROR_MAIN);
	}
	
	public static Error createErrorFolder() {
		return new SimpleFixableError("The optionsfile 'VAR0' is a directory!", FileOptions.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	public static Error createErrorCreate() {
		return new SimpleError("The optionsfile 'VAR0' could not be created!", FileOptions.ERROR_LOAD);
	}
	
	public static Error createErrorMalformed() {
		return new SimpleFixableError("The optionsfile 'VAR0' is malformed!", FileOptions.ERROR_LOAD, () -> {
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
