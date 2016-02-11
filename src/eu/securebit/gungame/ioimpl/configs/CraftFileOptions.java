package eu.securebit.gungame.ioimpl.configs;

import java.io.File;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.io.configs.FileOptions;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileOptions extends CraftFileGunGameConfig implements FileOptions {
	
	public CraftFileOptions(File file, CraftErrorHandler handler) {
		super(file, handler, FileOptions.ERROR_LOAD, FileOptions.ERROR_FOLDER, FileOptions.ERROR_CREATE, FileOptions.ERROR_MALFORMED, "options");
		
		this.getDefaults().add(new ConfigDefault("option.reset-level", false, boolean.class));
		this.getDefaults().add(new ConfigDefault("option.autorespawn", true, boolean.class));
		this.getDefaults().add(new ConfigDefault("option.care-natural-death", true, boolean.class));
		this.getDefaults().add(new ConfigDefault("option.start-level", 1, int.class));
	}
	
	@Override
	public boolean isLevelResetAfterDeath() {
		this.checkReady();
		
		return super.config.getBoolean("option.reset-level");
	}

	@Override
	public boolean isAutoRespawn() {
		this.checkReady();
		
		return super.config.getBoolean("option.autorespawn");
	}

	@Override
	public boolean isLevelDowngradeOnNaturalDeath() {
		this.checkReady();
		
		return super.config.getBoolean("option.care-natural-death");
	}
	
	@Override
	public int getStartLevel() {
		this.checkReady();
		
		return super.config.getInt("option.start-level");
	}
	
	@Override
	public void setLevelResetAfterDeath(boolean enabled) {
		this.checkReady();
		
		super.config.set("option.reset-level", enabled);
		this.save();
	}

	@Override
	public void setAutoRespawn(boolean enabled) {
		this.checkReady();
		
		super.config.set("option.autorespawn", enabled);
		this.save();
	}

	@Override
	public void setLevelDowngradeOnNaturalDeath(boolean enabled) {
		this.checkReady();
		
		super.config.set("option.care-natural-death", enabled);
		this.save();
	}
	
	@Override
	public void setStartLevel(int level) {
		this.checkReady();
		
		super.config.set("option.start-level", level);
		this.save();
	}
	
}
