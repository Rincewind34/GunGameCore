package eu.securebit.gungame.io.impl;

import org.bukkit.ChatColor;

import eu.securebit.gungame.exception.MalformedConfigException;
import eu.securebit.gungame.io.FileScoreboard;
import eu.securebit.gungame.io.util.AbstractFileConfig;

public class CraftFileScoreboard extends AbstractFileConfig implements FileScoreboard {
	
	public CraftFileScoreboard(String path, String name) {
		super(path, name, "scoreboard");
	}
	
	@Override
	public void validate() throws MalformedConfigException {
		if (super.config.getString("scoreboard.title").length() >= 64) {
			throw new MalformedConfigException("The length of 'scoreboard.title' must be shorter than 64 characters!");
		}
		
		if (!super.config.getString("scoreboard.format").contains("${player}")) {
			throw new MalformedConfigException("Invalid scoreboard entry format!");
		}
	}

	@Override
	public boolean isScoreboardEnabled() {
		return super.config.getBoolean("scoreboard.enabled");
	}

	@Override
	public String getScoreboardTitle() {
		return ChatColor.translateAlternateColorCodes('&', super.config.getString("scoreboard.title"));
	}

	@Override
	public String getScoreboardFormat() {
		return super.config.getString("scoreboard.format");
	}
	
	public void addDefaults() {
		super.config.addDefault("scoreboard.enabled", true);
		super.config.addDefault("scoreboard.title", "&7===== &eGunGame &7=====");
		super.config.addDefault("scoreboard.format", "&7${player}");
	}
	
}
