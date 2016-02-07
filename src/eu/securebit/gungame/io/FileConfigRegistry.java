package eu.securebit.gungame.io;

import java.util.List;

import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.directories.RootDirectory;

public interface FileConfigRegistry extends FileConfig {
	
	public static final String ERROR_MAIN = 				"1|003|000|000";
	
	public static final String ERROR_LOAD = 				"1|003|001|000";
	
	public static final String ERROR_FOLDER = 				"1|003|001|001";
	
	public static final String ERROR_CREATE = 				"1|003|001|002";
	
	public static final String ERROR_MALFORMED_STRUCTURE = 	"1|003|001|003";
	
	public static final String ERROR_MALFORMED_ENTRIES = 	"1|003|001|004";
	
	public static Error createErrorMain() {
		return new SimpleError("In the file '.configregistry' occured an error!", RootDirectory.ERROR_MAIN);
	}
	
	public static Error createErrorLoad() {
		return new SimpleError("The file '.configregistry' could not be loaded!", FileConfigRegistry.ERROR_MAIN);
	}
	
	public static Error createErrorFolder() {
		return new SimpleFixableError("The file '.configregistry' is a directory!", FileConfigRegistry.ERROR_LOAD, () -> {
			// TODO delete file
		});
	}
	
	public static Error createErrorCreate() {
		return new SimpleError("The file '.configregistry' could not be created!", FileConfigRegistry.ERROR_LOAD);
	}
	
	public static Error createErrorMalformedStructure() {
		return new SimpleFixableError("The yaml-file '.configregistry' is malformed!", FileConfigRegistry.ERROR_LOAD, () -> {
			// TODO delete file
		});
	}
	
	public static Error createErrorMalformedEntries() {
		return new SimpleFixableError("The entries in '.configregistry' are malformed!", FileConfigRegistry.ERROR_MAIN, () -> {
			// TODO delete file
		});
	}
	
	
	public abstract void add(String file, String type);
	
	public abstract void remove(String file);
	
	public abstract boolean contains(String file);
	
	public abstract String get(String file);
	
	public abstract List<String> getEntries();
	
}
