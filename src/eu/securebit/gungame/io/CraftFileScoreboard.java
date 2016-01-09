package eu.securebit.gungame.io;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.exception.MalformedConfigException;
import eu.securebit.gungame.util.FileValidatable;

public class CraftFileScoreboard implements FileScoreboard {
	
	private File file;
	private FileConfiguration config;
	
	public CraftFileScoreboard(String path, String name) {
		this.file = FileValidatable.convert(path, name);
		this.config = YamlConfiguration.loadConfiguration(this.file);
		this.addDefaults();
		this.save();
	}
	
	@Override
	public void save() {
		this.config.options().copyDefaults(true);
		
		try {
			this.config.save(this.file);
		} catch (IOException e) {
			throw new GunGameException(e.getMessage());
		}
	}

	@Override
	public void validate() throws MalformedConfigException {
		if (this.config.getString("scoreboard.title").length() >= 64) {
			throw new MalformedConfigException("The length of 'scoreboard.title' must be shorter than 64 characters!");
		}
		
		if (!this.config.getString("scoreboard.format").contains("${player}")) {
			throw new MalformedConfigException("Invalid scoreboard entry format!");
		}
	}

	@Override
	public boolean isScoreboardEnabled() {
		return this.config.getBoolean("scoreboard.enabled");
	}

	@Override
	public String getScoreboardTitle() {
		return ChatColor.translateAlternateColorCodes('&', this.config.getString("scoreboard.title"));
	}

	@Override
	public String getScoreboardFormat() {
		return this.config.getString("scoreboard.format");
	}
	
	public void addDefaults() {
		this.config.addDefault("scoreboard.enabled", true);
		this.config.addDefault("scoreboard.title", "&7===== &eGunGame &7=====");
		this.config.addDefault("scoreboard.format", "&7${player}");
	}
	
}
