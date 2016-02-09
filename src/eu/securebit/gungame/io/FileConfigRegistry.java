package eu.securebit.gungame.io;

import java.util.List;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.directories.RootDirectory;

public interface FileConfigRegistry extends FileConfig {
	
	public static final String ERROR_MAIN = 				"1300";
	
	public static final String ERROR_LOAD = 				"1310";
	
	public static final String ERROR_FOLDER = 				"1311";
	
	public static final String ERROR_CREATE = 				"1312";
	
	public static final String ERROR_MALFORMED_STRUCTURE = 	"1313";
	
	public static final String ERROR_MALFORMED_ENTRIES = 	"1314";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("In the file '.configregistry' occured an error!", RootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The file '.configregistry' could not be loaded!", FileConfigRegistry.ERROR_MAIN);
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The file '.configregistry' is a directory!", FileConfigRegistry.ERROR_LOAD, () -> {
			// TODO delete file
		});
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The file '.configregistry' could not be created!", FileConfigRegistry.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformedStructure() {
		return new LayoutErrorFixable("The yaml-file '.configregistry' is malformed!", FileConfigRegistry.ERROR_LOAD, () -> {
			// TODO delete file
		});
	}
	
	public static LayoutError createErrorMalformedEntries() {
		return new LayoutErrorFixable("The entries in '.configregistry' are malformed!", FileConfigRegistry.ERROR_MAIN, () -> {
			// TODO delete file
		});
	}
	
	
	public abstract void add(String file, String type);
	
	public abstract void remove(String file);
	
	public abstract boolean contains(String file);
	
	public abstract String get(String file);
	
	public abstract List<String> getEntries();
	
}
