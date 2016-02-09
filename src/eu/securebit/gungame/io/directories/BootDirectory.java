package eu.securebit.gungame.io.directories;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.io.abstracts.Directory;

public interface BootDirectory extends Directory {
	
	public static final String ERROR_MAIN = 				"1600";
	
	public static final String ERROR_FILE = 				"1610";
	
	public static final String ERROR_CREATE = 				"1620";
	
	public static final String ERROR_BOOTDATA_FOLDER = 		"1630";
	
	public static final String ERROR_BOOTDATA_CREATE = 		"1640";
	
	public static final String ERROR_BOOTDATA_MALFORMED =	"1650";
	
	public static final String ERROR_BOOTDATA_SAVE = 		"1660";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("The bootfolder could not be loaded!", RootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorFile() {
		return new LayoutErrorFixable("The bootfolder is a file!", BootDirectory.ERROR_MAIN, () -> {
			// TODO delete
		});
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The bootfolder could not be created!", BootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorBootdataFolder() {
		return new LayoutErrorFixable("The '.bootdata.yml' in the bootfolder is a directory!", BootDirectory.ERROR_MAIN, () -> {
			// TODO delete
		});
	}
	
	public static LayoutError createErrorBootdataCreate() {
		return new LayoutError("The '.bootdata.yml' in the bootfolder could not be created!", BootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorBootdataMalformed() {
		return new LayoutErrorFixable("The '.bootdata.yml' in the bootfolder is malformed!", BootDirectory.ERROR_MAIN, () -> {
			// TODO delete
		});
	}
	
	public static LayoutError createErrorBootdataSave() {
		return new LayoutError("The '.bootdata.yml' in the bootfolder could not be saved!", BootDirectory.ERROR_MAIN);
	}
	
	
	public abstract void setUsingFrameId(int id);
	
	public abstract int getUsingFrameId();
	
}
