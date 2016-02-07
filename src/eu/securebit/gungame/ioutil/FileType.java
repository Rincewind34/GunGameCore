package eu.securebit.gungame.ioutil;

import java.io.File;

public enum FileType {
	
	FILE((file) -> {
		return file.isFile();
	}, (file) -> {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		file.createNewFile();
	}),
	
	DIRECTORY((file) -> {
		return file.isDirectory();
	}, (file) -> {
		file.mkdirs();
	});
	
	private FileChecker checker;
	
	private FileCreator creator;
	
	private FileType(FileChecker checker, FileCreator creator) {
		this.checker = checker;
		this.creator = creator;
	}
	
	public boolean checkFile(File file) {
		return this.checker.execute(file);
	}
	
	public void createFile(File file) throws Exception {
		this.creator.execute(file);
	}
	
	
	public static interface FileChecker {
		
		public abstract boolean execute(File file);
		
	}
	
	public static interface FileCreator {
		
		public abstract void execute(File file) throws Exception;
		
	}
	
}
