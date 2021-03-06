package eu.securebit.gungame.io.configs;

import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;

public interface FileScoreboard extends FileIdentifyable, FileConfig {
	
	public static final String ERROR_MAIN = 			"7|004|000|000-VAR";
	
	public static final String ERROR_LOAD = 			"7|004|001|000-VAR";
	
	public static final String ERROR_FOLDER =			"7|004|001|001-VAR";
	
	public static final String ERROR_CREATE = 			"7|004|001|002-VAR";
	
	public static final String ERROR_MALFORMED = 		"7|004|001|003-VAR";
	
	public static final String ERROR_TITLE = 			"7|004|002|000-VAR";
	
	public static final String ERROR_FORMAT = 			"7|004|003|000-VAR";
	
	public static Error createErrorMain() {
		return new SimpleError("In the scoreboardfile 'VAR' occured an error!");
	}
	
	public static Error createErrorLoad() {
		return new SimpleError("The scoreboardfile 'VAR' could not be loaded!", FileScoreboard.ERROR_MAIN);
	}
	
	public static Error createErrorFolder() {
		return new SimpleFixableError("The scoreboardfile 'VAR' is a directory!", FileScoreboard.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	public static Error createErrorCreate() {
		return new SimpleError("The scoreboardfile 'VAR' could not be created!", FileScoreboard.ERROR_LOAD);
	}
	
	public static Error createErrorMalformed() {
		return new SimpleFixableError("The scoreboardfile 'VAR' is malformed!", FileScoreboard.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	public static Error createErrorTitle() {
		return new SimpleFixableError("The title given by the scoreboardfile 'VAR' has to be shorter than 64 characters!", FileScoreboard.ERROR_MAIN, () -> {
			// TODO substring
		});
	}
	
	public static Error createErrorFormat() {
		return new SimpleError("The format given by the scoreboardfile 'VAR' has to contain '§{player}'!", FileScoreboard.ERROR_MAIN);
	}
	
	
	public abstract boolean isScoreboardEnabled();
	
	public abstract String getScoreboardTitle();
	
	public abstract String getScoreboardFormat();
	
}
