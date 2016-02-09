package eu.securebit.gungame.io.configs;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;

public interface FileScoreboard extends FileGunGameConfig {
	
	public static final String ERROR_MAIN = 			"Error-7400-VAR0";
	
	public static final String ERROR_LOAD = 			"Error-7410-VAR0";
	
	public static final String ERROR_FOLDER =			"Error-7411-VAR0";
	
	public static final String ERROR_CREATE = 			"Error-7412-VAR0";
	
	public static final String ERROR_MALFORMED = 		"Error-7413-VAR0";
	
	public static final String ERROR_TITLE = 			"Error-7420-VAR0";
	
	public static final String ERROR_FORMAT = 			"Error-7430-VAR0";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("In the scoreboardfile 'VAR0' occured an error!");
	}
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The scoreboardfile 'VAR0' could not be loaded!", FileScoreboard.ERROR_MAIN);
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The scoreboardfile 'VAR0' is a directory!", FileScoreboard.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The scoreboardfile 'VAR0' could not be created!", FileScoreboard.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformed() {
		return new LayoutErrorFixable("The scoreboardfile 'VAR0' is malformed!", FileScoreboard.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	public static LayoutError createErrorTitle() {
		return new LayoutErrorFixable("The title given by the scoreboardfile 'VAR0' has to be shorter than 64 characters!", FileScoreboard.ERROR_MAIN, () -> {
			// TODO substring
		});
	}
	
	public static LayoutError createErrorFormat() {
		return new LayoutError("The format given by the scoreboardfile 'VAR0' has to contain '§{player}'!", FileScoreboard.ERROR_MAIN);
	}
	
	
	public abstract boolean isScoreboardEnabled();
	
	public abstract String getScoreboardTitle();
	
	public abstract String getScoreboardFormat();
	
}
