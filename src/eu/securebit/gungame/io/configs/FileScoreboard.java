package eu.securebit.gungame.io.configs;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.exception.GunGameFixException;
import eu.securebit.gungame.ioutil.IOUtil;

public interface FileScoreboard extends FileGunGameConfig {
	
	public static final String ERROR_LOAD = 			"Error-7410-VAR0";
	
	public static final String ERROR_FOLDER =			"Error-7411-VAR0";
	
	public static final String ERROR_CREATE = 			"Error-7412-VAR0";
	
	public static final String ERROR_MALFORMED = 		"Error-7413-VAR0";
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The scoreboardfile 'VAR0' could not be loaded!");
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The scoreboardfile 'VAR0' is a directory!", FileScoreboard.ERROR_LOAD, (variables) -> {
			if (variables.length == 1) {
				IOUtil.delete(Main.instance().getRootDirectory().getFile(variables[0]));
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the directory 'VAR0'.");
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The scoreboardfile 'VAR0' could not be created!", FileScoreboard.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformed() {
		return new LayoutErrorFixable("The scoreboardfile 'VAR0' is malformed!", FileScoreboard.ERROR_LOAD, (variables) -> {
			if (variables.length == 1) {
				IOUtil.delete(Main.instance().getRootDirectory().getFile(variables[0]));
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the file 'VAR0'.");
	}
	
	
	public abstract void setScoreboardTitle(String title);
	
	public abstract boolean isScoreboardEnabled();
	
	public abstract String getScoreboardTitle();
	
	public abstract String getScoreboardFormat();
	
}
