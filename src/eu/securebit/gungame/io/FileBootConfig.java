package eu.securebit.gungame.io;

import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;
import eu.securebit.gungame.io.directories.RootDirectory;

public interface FileBootConfig extends FileIdentifyable, FileConfig {
	
	public static final String ERROR_MAIN = 		"1400";
	
	public static final String ERROR_LOAD = 		"1410";
	
	public static final String ERROR_FOLDER = 		"1411";
	
	public static final String ERROR_CREATE = 		"1412";
	
	public static final String ERROR_MALFORMED = 	"1413";
	
	public static Error createErrorMain() {
		return new SimpleError("In the 'bootconfig.yml' occured an error!", RootDirectory.ERROR_MAIN);
	}
	
	public static Error createErrorLoad() {
		return new SimpleError("In the 'bootconfig.yml' occured an error!", FileBootConfig.ERROR_MAIN);
	}
	
	public static Error createErrorFolder() {
		return new SimpleFixableError("The file 'bootconfig.yml' is a directory!", FileBootConfig.ERROR_LOAD, () -> {
			// TODO delete
		});
	}
	
	public static Error createErrorCreate() {
		return new SimpleError("The file 'bootconfig.yml' could not be created!", FileBootConfig.ERROR_LOAD);
	}
	
	public static Error createErrorMalformed() {
		return new SimpleFixableError("The file 'bootconfig.yml' is malformed!", FileBootConfig.ERROR_LOAD, () -> {
			// TODO delete
		});
	}
	
	
	public abstract String getBootFolder();
	
	public abstract String getFrameJar();
	
	public abstract String getColorSet();
	
}
