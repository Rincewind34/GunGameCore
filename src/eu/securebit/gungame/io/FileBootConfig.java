package eu.securebit.gungame.io;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.exception.GunGameFixException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;
import eu.securebit.gungame.io.directories.RootDirectory;

public interface FileBootConfig extends FileIdentifyable, FileConfig {
	
	public static final String ERROR_LOAD = 		"Error-1410";
	
	public static final String ERROR_FOLDER = 		"Error-1411";
	
	public static final String ERROR_CREATE = 		"Error-1412";
	
	public static final String ERROR_MALFORMED = 	"Error-1413";
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("In the 'bootconfig.yml' occured an error!", RootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The file 'bootconfig.yml' is a directory!", FileBootConfig.ERROR_LOAD, (variables) -> {
			if (variables.length == 0) {
				Core.getRootDirectory().deleteBootConfig();
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the directory 'bootconfig.yml'.");
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The file 'bootconfig.yml' could not be created!", FileBootConfig.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformed() {
		return new LayoutErrorFixable("The file 'bootconfig.yml' is malformed!", FileBootConfig.ERROR_LOAD, (variables) -> {
			if (variables.length == 0) {
				Core.getRootDirectory().deleteBootConfig();
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the directory 'bootconfig.yml'.");
	}
	
	
	public abstract void setColorSet(String colorset);
	
	public abstract void setDebugMode(boolean debug);
	
	public abstract boolean isDebugMode();
	
	public abstract String getBootFolder();
	
	public abstract String getFrameJar();
	
	public abstract String getColorSet();
	
}
