package eu.securebit.gungame.ioimpl.abstracts;

import java.io.File;

import eu.securebit.gungame.errorhandling.ErrorHandler;
import eu.securebit.gungame.io.abstracts.Directory;
import eu.securebit.gungame.ioutil.FileType;

public abstract class AbstractDirectory extends AbstractFile implements Directory {
	
	public AbstractDirectory(File file, ErrorHandler handler, String errorMain, String errorFile, String errorCreate) {
		super(file, handler, errorMain, errorFile, errorCreate);
	}

	@Override
	public boolean isCreated() {
		return super.file.exists();
	}
	
	@Override
	public FileType getFileType() {
		return FileType.DIRECTORY;
	}
	
	@Override
	public String getAbsolutPath() {
		return super.file.getAbsolutePath();
	}
	
	@Override
	public String getRelativPath() {
		return super.file.getPath();
	}

}
