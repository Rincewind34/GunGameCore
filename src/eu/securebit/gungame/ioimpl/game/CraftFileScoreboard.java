package eu.securebit.gungame.ioimpl.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import eu.securebit.gungame.errors.ErrorHandler;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.io.game.FileScoreboard;
import eu.securebit.gungame.ioimpl.abstracts.AbstractConfig;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileScoreboard extends AbstractConfig implements FileScoreboard {
	
	private static final List<ConfigDefault> defaults = new ArrayList<>();
	
	static {
		CraftFileScoreboard.defaults.add(new ConfigDefault("scoreboard.enabled", true, boolean.class));
		CraftFileScoreboard.defaults.add(new ConfigDefault("scoreboard.title", "&7===== &eGunGame &7=====", String.class));
		CraftFileScoreboard.defaults.add(new ConfigDefault("scoreboard.format", "&7${player}", String.class));
	}
	
	public CraftFileScoreboard(File file, ErrorHandler handler) {
		super(file, handler,
				FileScoreboard.ERROR_MAIN, FileScoreboard.ERROR_LOAD, FileScoreboard.ERROR_FOLDER, FileScoreboard.ERROR_CREATE, FileScoreboard.ERROR_MALFORMED);
	}
	
	@Override
	public boolean isScoreboardEnabled() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getBoolean("scoreboard.enabled");
	}

	@Override
	public String getScoreboardTitle() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return ChatColor.translateAlternateColorCodes('&', super.config.getString("scoreboard.title"));
	}

	@Override
	public String getScoreboardFormat() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getString("scoreboard.format");
	}
	
	public void addDefaults() {
		for (ConfigDefault entry : CraftFileScoreboard.defaults) {
			super.config.addDefault(entry.getPath(), entry.getValue());
		}
	}

	@Override
	public String getAbsolutePath() {
		return super.file.getAbsolutePath();
	}
	
	@Override
	public String getIdentifier() {
		return "scoreboard";
	}
	
	@Override
	public void validate() {
		for (ConfigDefault entry : CraftFileScoreboard.defaults) {
			if (!entry.validate(super.config)) {
				this.throwError(FileScoreboard.ERROR_MALFORMED);
				break;
			}
		}
		
		if (super.config.getString("scoreboard.title").length() >= 64) {
			this.throwError(FileScoreboard.ERROR_TITLE);
		}
		
		if (!super.config.getString("scoreboard.format").contains("${player}")) {
			this.throwError(FileScoreboard.ERROR_FORMAT);
		}
	}
	
}
