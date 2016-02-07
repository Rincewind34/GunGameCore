package eu.securebit.gungame.interpreter.impl;

import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.interpreter.GameOptions;
import eu.securebit.gungame.io.configs.FileOptions;

public class CraftGameOptions implements GameOptions {
	
	private FileOptions file;
	
	public CraftGameOptions(FileOptions file) {
		if (!file.isAccessable()) {
			throw new GunGameException("Cannot interpret options-file '" + file.getAbsolutePath() + "'!");
		}
		
		this.file = file;
	}

	@Override
	public void autoRespawn(boolean value) {
		this.file.setAutoRespawn(value);
	}

	@Override
	public void careNaturalDeath(boolean value) {
		this.file.setLevelDowngradeOnNaturalDeath(value);
	}

	@Override
	public void levelReset(boolean value) {
		this.file.setLevelResetAfterDeath(value);
	}

	@Override
	public boolean autoRespawn() {
		return this.file.isAutoRespawn();
	}

	@Override
	public boolean careNaturalDeath() {
		return this.file.isLevelDowngradeOnNaturalDeath();
	}

	@Override
	public boolean levelReset() {
		return this.file.isLevelResetAfterDeath();
	}

	public FileOptions getFile() {
		return this.file;
	}
	
}
