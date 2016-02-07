package eu.securebit.gungame.ioimpl.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eu.securebit.gungame.errors.ErrorHandler;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.io.configs.FileOptions;
import eu.securebit.gungame.ioimpl.abstracts.AbstractConfig;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileOptions extends AbstractConfig implements FileOptions {
	
	private static final List<ConfigDefault> defaults = new ArrayList<>();
	
	static {
		CraftFileOptions.defaults.add(new ConfigDefault("options.reset-level", false, boolean.class));
		CraftFileOptions.defaults.add(new ConfigDefault("options.autorespawn", true, boolean.class));
		CraftFileOptions.defaults.add(new ConfigDefault("options.care-natural-death", true, boolean.class));
	}
	
	
	public CraftFileOptions(File file, ErrorHandler handler) {
		super(file, handler,
				FileOptions.ERROR_MAIN, FileOptions.ERROR_LOAD, FileOptions.ERROR_FOLDER, FileOptions.ERROR_CREATE, FileOptions.ERROR_MALFORMED);
	}
	
	@Override
	public boolean isLevelResetAfterDeath() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getBoolean("options.reset-level");
	}

	@Override
	public boolean isAutoRespawn() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getBoolean("options.autorespawn");
	}

	@Override
	public boolean isLevelDowngradeOnNaturalDeath() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getBoolean("options.care-natural-death");
	}

	@Override
	public void setLevelResetAfterDeath(boolean enabled) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		super.config.set("options.reset-level", enabled);
		this.save();
	}

	@Override
	public void setAutoRespawn(boolean enabled) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		super.config.set("options.autorespawn", enabled);
		this.save();
	}

	@Override
	public void setLevelDowngradeOnNaturalDeath(boolean enabled) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		super.config.set("options.care-natural-death", enabled);
		this.save();
	}
	
	@Override
	public void addDefaults() {
		for (ConfigDefault entry : CraftFileOptions.defaults) {
			super.config.addDefault(entry.getPath(), entry.getValue());
		}
	}
	
	@Override
	public String getAbsolutePath() {
		return super.file.getAbsolutePath();
	}
	
	@Override
	public String getIdentifier() {
		return "options";
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
