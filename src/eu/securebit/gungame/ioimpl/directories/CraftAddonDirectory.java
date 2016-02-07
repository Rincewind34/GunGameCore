package eu.securebit.gungame.ioimpl.directories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eu.securebit.gungame.errors.ErrorHandler;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.io.directories.AddonDirectory;
import eu.securebit.gungame.ioimpl.abstracts.AbstractDirectory;
import eu.securebit.gungame.ioutil.IOUtil;

public class CraftAddonDirectory extends AbstractDirectory implements AddonDirectory {
	
	public CraftAddonDirectory(File directory, ErrorHandler handler) {
		super(directory, handler, AddonDirectory.ERROR_MAIN, AddonDirectory.ERROR_FILE, AddonDirectory.ERROR_CREATE);
	}
	
	@Override
	public List<File> getAddonFiles() {
		if (!this.isReady()) {
			throw new GunGameErrorPresentException();
		}
		
		List<File> files = new ArrayList<>();
		
		for (File file : new File(this.getAbsolutPath()).listFiles()) {
			if (this.isJar(file)) {
				files.add(file);
			}
		}
		
		return files;
	}
	
	private boolean isJar(File file) {
		try {
			IOUtil.checkJarFile(file);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}


}
