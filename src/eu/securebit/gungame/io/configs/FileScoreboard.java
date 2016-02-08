package eu.securebit.gungame.io.configs;

import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;

public interface FileScoreboard extends FileIdentifyable, FileConfig {
	
	public static final String ERROR_MAIN = 			"7400-VAR0";
	
	public static final String ERROR_LOAD = 			"7410-VAR0";
	
	public static final String ERROR_FOLDER =			"7411-VAR0";
	
	public static final String ERROR_CREATE = 			"7412-VAR0";
	
	public static final String ERROR_MALFORMED = 		"7413-VAR0";
	
	public static final String ERROR_TITLE = 			"7420-VAR0";
	
	public static final String ERROR_FORMAT = 			"7430-VAR0";
	
	public static Error createErrorMain() {
		return new SimpleError("In the scoreboardfile 'VAR0' occured an error!");
	}
	
	public static Error createErrorLoad() {
		return new SimpleError("The scoreboardfile 'VAR0' could not be loaded!", FileScoreboard.ERROR_MAIN);
	}
	
	public static Error createErrorFolder() {
		return new SimpleFixableError("The scoreboardfile 'VAR0' is a directory!", FileScoreboard.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	public static Error createErrorCreate() {
		return new SimpleError("The scoreboardfile 'VAR0' could not be created!", FileScoreboard.ERROR_LOAD);
	}
	
	public static Error createErrorMalformed() {
		return new SimpleFixableError("The scoreboardfile 'VAR0' is malformed!", FileScoreboard.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	public static Error createErrorTitle() {
		return new SimpleFixableError("The title given by the scoreboardfile 'VAR0' has to be shorter than 64 characters!", FileScoreboard.ERROR_MAIN, () -> {
			// TODO substring
		});
	}
	
	public static Error createErrorFormat() {
		return new SimpleError("The format given by the scoreboardfile 'VAR0' has to contain '§{player}'!", FileScoreboard.ERROR_MAIN);
	}
	
	
	public abstract boolean isScoreboardEnabled();
	
	public abstract String getScoreboardTitle();
	
	public abstract String getScoreboardFormat();
	
}
