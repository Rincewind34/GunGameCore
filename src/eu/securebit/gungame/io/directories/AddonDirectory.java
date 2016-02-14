package eu.securebit.gungame.io.directories;

import java.io.File;
import java.util.List;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.exception.GunGameFixException;
import eu.securebit.gungame.io.abstracts.Directory;
import eu.securebit.gungame.ioutil.IOUtil;

public interface AddonDirectory extends Directory {
	
	public static final String ERROR_MAIN = 	"Error-1500";
	
	public static final String ERROR_FILE = 	"Error-1510";
	
	public static final String ERROR_CREATE = 	"Error-1520";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("The addondirectory could not be loaded!", RootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorFile() {
		return new LayoutErrorFixable("The addondirectory is a file!", AddonDirectory.ERROR_MAIN, (variables) -> {
			if (variables.length == 0) {
				IOUtil.delete(new File(Main.instance().getRootDirectory().getAddonDirectory().getAbsolutPath()));
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the file 'plugins/GunGame/addons'.");
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The addondirectory could not be created!", AddonDirectory.ERROR_MAIN);
	}
	
	
	public abstract List<File> getAddonFiles();
	
}
