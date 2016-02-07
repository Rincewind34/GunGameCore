package eu.securebit.gungame.io.directories;

import java.io.File;
import java.util.List;

import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.io.abstracts.Directory;

public interface AddonDirectory extends Directory {
	
	public static final String ERROR_MAIN = 	"1|005|000|000";
	
	public static final String ERROR_FILE = 	"1|005|001|000";
	
	public static final String ERROR_CREATE = 	"1|005|002|000";
	
	public static Error createErrorMain() {
		return new SimpleError("The addondirectory could not be loaded!", RootDirectory.ERROR_MAIN);
	}
	
	public static Error createErrorFile() {
		return new SimpleFixableError("The addondirectory is a file!", AddonDirectory.ERROR_MAIN, () -> {
			// TODO delete
		});
	}
	
	public static Error createErrorCreate() {
		return new SimpleError("The addondirectory could not be created!", AddonDirectory.ERROR_MAIN);
	}
	
	
	public abstract List<File> getAddonFiles();
	
}
