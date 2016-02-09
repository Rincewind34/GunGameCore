package eu.securebit.gungame.exception;

import eu.securebit.gungame.io.configs.FileGunGameConfig;

@SuppressWarnings("serial")
public class GunGameInterpretException extends GunGameException {
	
	public static GunGameInterpretException create(FileGunGameConfig config) {
		return new GunGameInterpretException(config);
	}
	
	protected GunGameInterpretException(FileGunGameConfig config) {
		super("Cannot interpret the " + config.getIdentifier() + "-file '" + config.getAbsolutePath() + "'!");
	}
	
}
