package eu.securebit.gungame.interpreter.impl;

import eu.securebit.gungame.interpreter.GameOptions;
import eu.securebit.gungame.io.configs.FileOptions;

public class CraftGameOptions extends AbstractInterpreter<FileOptions> implements GameOptions {
	
	public CraftGameOptions(FileOptions file) {
		super(file);
	}

	@Override
	public void autoRespawn(boolean value) {
		super.config.setAutoRespawn(value);
	}

	@Override
	public void careNaturalDeath(boolean value) {
		super.config.setLevelDowngradeOnNaturalDeath(value);
	}

	@Override
	public void levelReset(boolean value) {
		super.config.setLevelResetAfterDeath(value);
	}

	@Override
	public boolean autoRespawn() {
		return super.config.isAutoRespawn();
	}

	@Override
	public boolean careNaturalDeath() {
		return super.config.isLevelDowngradeOnNaturalDeath();
	}

	@Override
	public boolean levelReset() {
		return super.config.isLevelResetAfterDeath();
	}

}
