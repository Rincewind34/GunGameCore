package eu.securebit.gungame.ioimpl.abstracts;

import java.io.File;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.ErrorHandler;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.io.abstracts.SimpleFile;
import eu.securebit.gungame.ioutil.IOUtil;

public abstract class AbstractFile implements SimpleFile {
	
	protected File file;
	
	protected ErrorHandler handler;
	
	private ThrownError errorLoad;
	private ThrownError errorFileType;
	private ThrownError errorCreate;
	
	public AbstractFile(File file, ErrorHandler handler, String errorLoad, String errorFileType, String errorCreate) {
		this.file = file;
		this.handler = handler;
		
		this.errorLoad = this.createError(errorLoad);
		this.errorFileType = this.createError(errorFileType);
		this.errorCreate = this.createError(errorCreate);
	}
	
	@Override
	public void create() {
		if (this.file.exists() && !this.getFileType().checkFile(this.file)) {
			this.handler.throwError(this.errorFileType);
			return;
		}
		
		if (!this.file.exists()) {
			try {
				this.getFileType().createFile(this.file);
			} catch (Exception ex) {
				if (Main.DEBUG) {
					ex.printStackTrace();
				}
				
				this.handler.throwError(this.errorCreate);
				return;
			}
		}
	}
	
	@Override
	public void checkReady() {
		if (!this.isReady()) {
			throw GunGameErrorPresentException.create();
		}
	}
	
	@Override
	public boolean isReady() {
		if (!this.isCreated()) {
			return false;
		}
		
		return !this.handler.isErrorPresent(this.errorLoad);
	}
	
	@Override
	public String getFailCause() {
		return this.handler.getCause(this.errorLoad).getParsedObjectId();
	}
	
	@Override
	public String getName() {
		return this.file.getName();
	}

	@Override
	public ErrorHandler getErrorHandler() {
		return this.handler;
	}
	
	@Override
	public ThrownError createError(String errorId) {
		return new ThrownError(errorId, IOUtil.preparePath(this.file.getAbsolutePath()));
	}
	
	@Override
	public ThrownError getErrorLoad() {
		return this.errorLoad;
	}
	
	@Override
	public ThrownError getErrorFileType() {
		return this.errorFileType;
	}
	
	@Override
	public ThrownError getErrorCreate() {
		return this.errorCreate;
	}
	
}
