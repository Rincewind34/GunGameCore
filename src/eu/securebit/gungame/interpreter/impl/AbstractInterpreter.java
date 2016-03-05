package eu.securebit.gungame.interpreter.impl;

import eu.securebit.gungame.errorhandling.ErrorHandler;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.interpreter.Interpreter;
import eu.securebit.gungame.io.configs.FileGunGameConfig;

public abstract class AbstractInterpreter<T extends FileGunGameConfig> implements Interpreter {
	
	protected T config;
	
	private ThrownError errorMain;
	private ThrownError errorInterpret;
	
	private ErrorHandler handler;
	
	public AbstractInterpreter(T config, String errorMain, String errorInterpret) {
		this.config = config;
		this.handler = this.config.getErrorHandler();
		this.errorMain = this.createError(errorMain);
		this.errorInterpret = this.createError(errorInterpret);
		
		if (!this.config.isReady()) {
			this.handler.throwError(this.errorInterpret, this.config.getErrorLoad());
		}
	}

	@Override
	public boolean wasSuccessful() {
		return !this.handler.isErrorPresent(this.errorInterpret);
	}
	
	@Override
	public boolean isFinished() {
		return !this.handler.isErrorPresent(this.errorMain);
	}
	
	@Override
	public String getName() {
		return this.config.getIdentifier();
	}
	
	@Override
	public String getFailCause() {
		return this.handler.getCause(this.errorMain).getParsedObjectId();
	}
	
	@Override
	public ThrownError getErrorMain() {
		return this.errorMain;
	}
	
	@Override
	public ThrownError getErrorInterpret() {
		return this.errorInterpret;
	}
	
	@Override
	public ThrownError createError(String errorId) {
		return this.config.createError(errorId);
	}
	
	@Override
	public ErrorHandler getErrorHandler() {
		return this.handler;
	}
	
	public T getConfig() {
		return this.config;
	}
	
	public void checkSuccess() {
		if (!this.wasSuccessful()) {
			throw GunGameErrorPresentException.create();
		}
	}
	
}
