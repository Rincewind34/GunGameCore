package eu.securebit.gungame.ioimpl.abstracts;

import java.io.File;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errors.ErrorHandler;
import eu.securebit.gungame.io.abstracts.SimpleFile;

public abstract class AbstractFile implements SimpleFile {
	
	protected File file;
	
	protected ErrorHandler handler;
	
	private String errorMain;
	private String errorFileType;
	private String errorCreate;
	
	public AbstractFile(File file, ErrorHandler handler, String errorMain, String errorFileType, String errorCreate) {
		this.file = file;
		this.handler = handler;
		
		this.errorMain = errorMain;
		this.errorFileType = errorFileType;
		this.errorCreate = errorCreate;
	}
	
	@Override
	public void create() {
		if (this.file.exists() && !this.getFileType().checkFile(this.file)) {
			this.throwError(this.errorFileType);
			return;
		}
		
		if (!this.file.exists()) {
			try {
				this.getFileType().createFile(this.file);
			} catch (Exception e) {
				if (Main.DEBUG) {
					e.printStackTrace();
				}
				
				this.throwError(this.errorCreate);
				return;
			}
		}
	}

	@Override
	public boolean isReady() {
		if (!this.isCreated()) {
			return false;
		}
		
		return !this.handler.isErrorPresent(this.errorMain);
	}

	@Override
	public ErrorHandler getErrorHandler() {
		return this.handler;
	}
	
	protected void throwError(String error) {
		String path = this.file.getAbsolutePath();
		String index = "plugins/GunGame";
		
		if (path.contains(index)) {
			path = path.substring(path.lastIndexOf(index) + index.length(), path.length());
		}
		
		this.handler.throwError(error, null, path);
	}
	
	protected String getErrorMain() {
		return this.errorMain;
	}
	
	protected String getErrorFileType() {
		return this.errorFileType;
	}
	
	protected String getErrorCreate() {
		return this.errorCreate;
	}
	
}
