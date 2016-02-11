package eu.securebit.gungame.interpreter.impl;

import eu.securebit.gungame.exception.GunGameInterpretException;
import eu.securebit.gungame.interpreter.Interpreter;
import eu.securebit.gungame.io.configs.FileGunGameConfig;

public class AbstractInterpreter<T extends FileGunGameConfig> implements Interpreter {
	
	protected T config;
	
	public AbstractInterpreter(T config) {
		if (!config.isReady()) {
			throw GunGameInterpretException.create(config);
		}
		
		this.config = config;
	}

	@Override
	public boolean wasSuccessful() {
		return this.config != null;
	}
	
	public T getConfig() {
		return this.config;
	}
	
}
