package eu.securebit.gungame.ioimpl.abstracts;

import java.io.File;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.io.abstracts.SimpleFile;
import eu.securebit.gungame.ioutil.IOUtil;

public abstract class AbstractFile implements SimpleFile {
	
	protected File file;
	
	protected CraftErrorHandler handler;
	
	private String errorMain;
	private String errorFileType;
	private String errorCreate;
	
	public AbstractFile(File file, CraftErrorHandler handler, String errorMain, String errorFileType, String errorCreate) {
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
	public CraftErrorHandler getErrorHandler() {
		return this.handler;
	}
	
	protected void throwError(String error) {
		this.handler.throwError(new ThrownError(error, IOUtil.preparePath(this.file.getAbsolutePath())));
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
