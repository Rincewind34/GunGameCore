package eu.securebit.gungame.ioimpl.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.io.configs.FileScoreboard;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileScoreboard extends CraftFileGunGameConfig implements FileScoreboard {
	
	private static final List<ConfigDefault> defaults = new ArrayList<>();
	
	static {
		CraftFileScoreboard.defaults.add(new ConfigDefault("scoreboard.enabled", true, boolean.class));
		CraftFileScoreboard.defaults.add(new ConfigDefault("scoreboard.title", "&7===== &eGunGame &7=====", String.class));
		CraftFileScoreboard.defaults.add(new ConfigDefault("scoreboard.format", "&7${player}", String.class));
	}
	
	public CraftFileScoreboard(File file, CraftErrorHandler handler) {
		super(file, handler,
				FileScoreboard.ERROR_MAIN, FileScoreboard.ERROR_LOAD, FileScoreboard.ERROR_FOLDER, FileScoreboard.ERROR_CREATE, FileScoreboard.ERROR_MALFORMED, "scoreboard");
	}
	
	@Override
	public boolean isScoreboardEnabled() {
		this.checkAccessability();
		
		return super.config.getBoolean("scoreboard.enabled");
	}

	@Override
	public String getScoreboardTitle() {
		this.checkAccessability();
		
		return ChatColor.translateAlternateColorCodes('&', super.config.getString("scoreboard.title"));
	}

	@Override
	public String getScoreboardFormat() {
		this.checkAccessability();
		
		return super.config.getString("scoreboard.format");
	}
	
	public void addDefaults() {
		for (ConfigDefault entry : CraftFileScoreboard.defaults) {
			super.config.addDefault(entry.getPath(), entry.getValue());
		}
	}

	@Override
	public void validate() {
		for (ConfigDefault entry : CraftFileScoreboard.defaults) {
			if (!entry.validate(super.config)) {
				super.handler.throwError(this.createError(FileScoreboard.ERROR_MALFORMED));
				break;
			}
		}
		
		if (super.config.getString("scoreboard.title").length() >= 64) {
			super.handler.throwError(this.createError(FileScoreboard.ERROR_TITLE));
		}
		
		if (!super.config.getString("scoreboard.format").contains("${player}")) {
			super.handler.throwError(this.createError(FileScoreboard.ERROR_FORMAT));
		}
	}
	
}
