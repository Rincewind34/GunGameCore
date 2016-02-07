package eu.securebit.gungame.io.directories;

import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.io.abstracts.Directory;

public interface BootDirectory extends Directory {
	
	public static final String ERROR_MAIN = 				"1|006|000|000";
	
	public static final String ERROR_FILE = 				"1|006|001|000";
	
	public static final String ERROR_CREATE = 				"1|006|002|000";
	
	public static final String ERROR_BOOTDATA_FOLDER = 		"1|006|003|000";
	
	public static final String ERROR_BOOTDATA_CREATE = 		"1|006|004|000";
	
	public static final String ERROR_BOOTDATA_MALFORMED =	"1|006|005|000";
	
	public static final String ERROR_BOOTDATA_SAVE = 		"1|006|006|000";
	
	public static Error createErrorMain() {
		return new SimpleError("The bootfolder could not be loaded!", RootDirectory.ERROR_MAIN);
	}
	
	public static Error createErrorFile() {
		return new SimpleFixableError("The bootfolder is a file!", BootDirectory.ERROR_MAIN, () -> {
			// TODO delete
		});
	}
	
	public static Error createErrorCreate() {
		return new SimpleError("The bootfolder could not be created!", BootDirectory.ERROR_MAIN);
	}
	
	public static Error createErrorBootdataFolder() {
		return new SimpleFixableError("The '.bootdata.yml' in the bootfolder is a directory!", BootDirectory.ERROR_MAIN, () -> {
			// TODO delete
		});
	}
	
	public static Error createErrorBootdataCreate() {
		return new SimpleError("The '.bootdata.yml' in the bootfolder could not be created!", BootDirectory.ERROR_MAIN);
	}
	
	public static Error createErrorBootdataMalformed() {
		return new SimpleFixableError("The '.bootdata.yml' in the bootfolder is malformed!", BootDirectory.ERROR_MAIN, () -> {
			// TODO delete
		});
	}
	
	public static Error createErrorBootdataSave() {
		return new SimpleError("The '.bootdata.yml' in the bootfolder could not be saved!", BootDirectory.ERROR_MAIN);
	}
	
	
	public abstract void setUsingFrameId(int id);
	
	public abstract int getUsingFrameId();
	
}
