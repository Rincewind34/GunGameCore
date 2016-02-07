package eu.securebit.gungame.io.directories;

import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.io.abstracts.Directory;

public interface BootDirectory extends Directory {
	
	public static final String ERROR_MAIN = 				"1600";
	
	public static final String ERROR_FILE = 				"1610";
	
	public static final String ERROR_CREATE = 				"1620";
	
	public static final String ERROR_BOOTDATA_FOLDER = 		"1630";
	
	public static final String ERROR_BOOTDATA_CREATE = 		"1640";
	
	public static final String ERROR_BOOTDATA_MALFORMED =	"1650";
	
	public static final String ERROR_BOOTDATA_SAVE = 		"1660";
	
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
