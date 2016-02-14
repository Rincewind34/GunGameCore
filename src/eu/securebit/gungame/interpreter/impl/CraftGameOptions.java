package eu.securebit.gungame.interpreter.impl;

import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.interpreter.GameOptions;
import eu.securebit.gungame.io.configs.FileOptions;
import eu.securebit.gungame.util.Warnings;

public class CraftGameOptions extends AbstractInterpreter<FileOptions> implements GameOptions {
	
	public CraftGameOptions(FileOptions file, int levelCount) {
		super(file, GameOptions.ERROR_MAIN, GameOptions.ERROR_INTERPRET);
		
		if (this.wasSuccessful()) {
			int startLevel = file.getStartLevel();
			
			if (startLevel < 1) {
				this.getErrorHandler().throwError(this.createError(GameOptions.ERROR_LEVELCOUNT_SMALLER));
			}
			
			if (startLevel > levelCount) {
				this.getErrorHandler().throwError(this.createError(GameOptions.ERROR_LEVELCOUNT_GREATER));
			}
		}
	}
	
	public CraftGameOptions(FileOptions file, ThrownError cause) {
		super(file, GameOptions.ERROR_MAIN, GameOptions.ERROR_INTERPRET);
		
		if (this.wasSuccessful()) {
			this.getErrorHandler().throwError(Warnings.WARNING_OPTIONS, cause);
		}
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
	public void setStartLevel(int level) {
		super.config.setStartLevel(level);
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
	
	@Override
	public boolean premiumKick() {
		return super.config.isPremiumKickEnabled();
	}
	
	@Override
	public int getStartLevel() {
		return super.config.getStartLevel();
	}

	@Override
	public int getPremiumSlots() {
		return super.config.getLobbyPremiumSlots();
	}

	@Override
	public int getCountdownLength() {
		return super.config.getLobbyCountdownLength();
	}

}
