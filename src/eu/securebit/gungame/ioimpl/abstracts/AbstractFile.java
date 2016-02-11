package eu.securebit.gungame.ioimpl.abstracts;

import java.io.File;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.io.abstracts.SimpleFile;
import eu.securebit.gungame.ioutil.IOUtil;

public abstract class AbstractFile implements SimpleFile {
	
	protected File file;
	
	protected CraftErrorHandler handler;
	
	private ThrownError errorMain;
	private ThrownError errorFileType;
	private ThrownError errorCreate;
	
	public AbstractFile(File file, CraftErrorHandler handler, String errorMain, String errorFileType, String errorCreate) {
		this.file = file;
		this.handler = handler;
		
		this.errorMain = this.createError(errorMain);
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
			} catch (Exception e) {
				if (Main.DEBUG) {
					e.printStackTrace();
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
		
		return !this.handler.isErrorPresent(this.errorMain);
	}

	@Override
	public CraftErrorHandler getErrorHandler() {
		return this.handler;
	}
	
	@Override
	public ThrownError createError(String errorId) {
		return new ThrownError(errorId, IOUtil.preparePath(this.file.getAbsolutePath()));
	}
	
	protected ThrownError getErrorMain() {
		return this.errorMain;
	}
	
	protected ThrownError getErrorFileType() {
		return this.errorFileType;
	}
	
	protected ThrownError getErrorCreate() {
		return this.errorCreate;
	}
	
}
