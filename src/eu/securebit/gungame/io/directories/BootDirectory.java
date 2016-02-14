package eu.securebit.gungame.io.directories;

import java.io.File;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.exception.GunGameFixException;
import eu.securebit.gungame.io.abstracts.Directory;
import eu.securebit.gungame.ioutil.IOUtil;

public interface BootDirectory extends Directory {
	
	public static final String ERROR_MAIN = 				"Error-1600";
	
	public static final String ERROR_FILE = 				"Error-1610";
	
	public static final String ERROR_CREATE = 				"Error-1620";
	
	public static final String ERROR_BOOTDATA_FOLDER = 		"Error-1630";
	
	public static final String ERROR_BOOTDATA_CREATE = 		"Error-1640";
	
	public static final String ERROR_BOOTDATA_MALFORMED =	"Error-1650";
	
	public static final String ERROR_BOOTDATA_SAVE = 		"Error-1660";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("The bootfolder could not be loaded!", RootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorFile() {
		return new LayoutErrorFixable("The bootfolder is a file!", BootDirectory.ERROR_MAIN, (variables) -> {
			if (variables.length == 0) {
				IOUtil.delete(new File(Main.instance().getRootDirectory().getBootFolder().getAbsolutPath()));
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the bootfolder.");
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The bootfolder could not be created!", BootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorBootdataFolder() {
		return new LayoutErrorFixable("The '.bootdata.yml' in the bootfolder is a directory!", BootDirectory.ERROR_MAIN, (variables) -> {
			if (variables.length == 0) {
				Main.instance().getRootDirectory().getBootFolder().deleteBootData();
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the directory '.bootdata.yml'.");
	}
	
	public static LayoutError createErrorBootdataCreate() {
		return new LayoutError("The '.bootdata.yml' in the bootfolder could not be created!", BootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorBootdataMalformed() {
		return new LayoutErrorFixable("The '.bootdata.yml' in the bootfolder is malformed!", BootDirectory.ERROR_MAIN, (variables) -> {
			if (variables.length == 0) {
				Main.instance().getRootDirectory().getBootFolder().deleteBootData();
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the file '.bootdata.yml'.");
	}
	
	public static LayoutError createErrorBootdataSave() {
		return new LayoutError("The '.bootdata.yml' in the bootfolder could not be saved!", BootDirectory.ERROR_MAIN);
	}
	
	
	public abstract void setUsingFrameId(int id);
	
	public abstract void deleteBootData();
	
	public abstract int getUsingFrameId();
	
}
