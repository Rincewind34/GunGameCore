package eu.securebit.gungame.ioimpl.configs;

import java.io.File;

import org.bukkit.ChatColor;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.io.configs.FileScoreboard;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileScoreboard extends CraftFileGunGameConfig implements FileScoreboard {
	
	public CraftFileScoreboard(File file, CraftErrorHandler handler) {
		super(file, handler, FileScoreboard.ERROR_LOAD, FileScoreboard.ERROR_FOLDER, FileScoreboard.ERROR_CREATE, FileScoreboard.ERROR_MALFORMED, "scoreboard");
		
		this.getDefaults().add(new ConfigDefault("scoreboard.enabled", true, boolean.class));
		this.getDefaults().add(new ConfigDefault("scoreboard.title", "&7===== &eGunGame &7=====", String.class));
		this.getDefaults().add(new ConfigDefault("scoreboard.format", "&7${player}", String.class));
	}
	
	@Override
	public void setScoreboardTitle(String title) {
		this.checkReady();
		
		super.config.set("scoreboard.title", title);
		this.save();
	}
	
	@Override
	public boolean isScoreboardEnabled() {
		this.checkReady();
		
		return super.config.getBoolean("scoreboard.enabled");
	}

	@Override
	public String getScoreboardTitle() {
		this.checkReady();
		
		return ChatColor.translateAlternateColorCodes('&', super.config.getString("scoreboard.title"));
	}

	@Override
	public String getScoreboardFormat() {
		this.checkReady();
		
		return super.config.getString("scoreboard.format");
	}
	
}
