package eu.securebit.gungame.ioimpl.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.io.configs.FileOptions;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileOptions extends CraftFileGunGameConfig implements FileOptions {
	
	private static final List<ConfigDefault> defaults = new ArrayList<>();
	
	static {
		CraftFileOptions.defaults.add(new ConfigDefault("option.reset-level", false, boolean.class));
		CraftFileOptions.defaults.add(new ConfigDefault("option.autorespawn", true, boolean.class));
		CraftFileOptions.defaults.add(new ConfigDefault("option.care-natural-death", true, boolean.class));
	}
	
	
	public CraftFileOptions(File file, CraftErrorHandler handler) {
		super(file, handler,
				FileOptions.ERROR_MAIN, FileOptions.ERROR_LOAD, FileOptions.ERROR_FOLDER, FileOptions.ERROR_CREATE, FileOptions.ERROR_MALFORMED, "options");
	}
	
	@Override
	public boolean isLevelResetAfterDeath() {
		this.checkAccessability();
		
		return super.config.getBoolean("option.reset-level");
	}

	@Override
	public boolean isAutoRespawn() {
		this.checkAccessability();
		
		return super.config.getBoolean("option.autorespawn");
	}

	@Override
	public boolean isLevelDowngradeOnNaturalDeath() {
		this.checkAccessability();
		
		return super.config.getBoolean("option.care-natural-death");
	}

	@Override
	public void setLevelResetAfterDeath(boolean enabled) {
		this.checkAccessability();
		
		super.config.set("option.reset-level", enabled);
		this.save();
	}

	@Override
	public void setAutoRespawn(boolean enabled) {
		this.checkAccessability();
		
		super.config.set("option.autorespawn", enabled);
		this.save();
	}

	@Override
	public void setLevelDowngradeOnNaturalDeath(boolean enabled) {
		this.checkAccessability();
		
		super.config.set("option.care-natural-death", enabled);
		this.save();
	}
	
	@Override
	public void addDefaults() {
		for (ConfigDefault entry : CraftFileOptions.defaults) {
			super.config.addDefault(entry.getPath(), entry.getValue());
		}
	}
	
	@Override
	public void validate() {
		for (ConfigDefault entry : CraftFileOptions.defaults) {
			if (!entry.validate(super.config)) {
				this.throwError(FileOptions.ERROR_MALFORMED);
				break;
			}
		}
	}
	
}
