package eu.securebit.gungame.io;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;
import eu.securebit.gungame.io.directories.RootDirectory;

public interface FileBootConfig extends FileIdentifyable, FileConfig {
	
	public static final String ERROR_MAIN = 		"1400";
	
	public static final String ERROR_LOAD = 		"1410";
	
	public static final String ERROR_FOLDER = 		"1411";
	
	public static final String ERROR_CREATE = 		"1412";
	
	public static final String ERROR_MALFORMED = 	"1413";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("In the 'bootconfig.yml' occured an error!", RootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("In the 'bootconfig.yml' occured an error!", FileBootConfig.ERROR_MAIN);
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The file 'bootconfig.yml' is a directory!", FileBootConfig.ERROR_LOAD, () -> {
			// TODO delete
		});
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The file 'bootconfig.yml' could not be created!", FileBootConfig.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformed() {
		return new LayoutErrorFixable("The file 'bootconfig.yml' is malformed!", FileBootConfig.ERROR_LOAD, () -> {
			// TODO delete
		});
	}
	
	
	public abstract String getBootFolder();
	
	public abstract String getFrameJar();
	
	public abstract String getColorSet();
	
}
