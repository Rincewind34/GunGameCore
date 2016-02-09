package eu.securebit.gungame.exception;

import eu.securebit.gungame.io.configs.FileGunGameConfig;

@SuppressWarnings("serial")
public class GunGameInterpretException extends GunGameException {
	
	public GunGameInterpretException(FileGunGameConfig config) {
		super("Cannot interpret the " + config.getIdentifier() + "-file '" + config.getAbsolutePath() + "'!");
	}
	
}
