package eu.securebit.gungame.io.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.io.ConfigError;
import eu.securebit.gungame.io.FileBootConfig;
import eu.securebit.gungame.util.ColorSet;

public class CraftFileBootConfig implements FileBootConfig {

	private final Plugin plugin;
	private File file;
	private FileConfiguration cfg;
	
	public CraftFileBootConfig(final Plugin plugin) {
		this.plugin = plugin;
		this.file = new File(this.plugin.getDataFolder(), "config.yml");
		this.cfg = null;
	}
	
	@Override
	public void initialize() {
		this.cfg = YamlConfiguration.loadConfiguration(this.file);
		this.cfg.addDefault("path-frame-file", "frames/frame_default.jar");
		this.cfg.addDefault("path-boot-folder", "frames/bootfolder");
		this.cfg.addDefault("color-set", "DEFAULT");
		this.cfg.options().copyDefaults(true);
		
		try {
			this.cfg.save(this.file);
		} catch (IOException e) {
			throw new GunGameException(e.getMessage());
		}
	}
	
	@Override
	public ConfigError[] validate() {
		List<ConfigError> status = new ArrayList<>();
		
		if (!this.cfg.isString("path-frame-file") || !this.cfg.isString("path-boot-folder") || !this.cfg.isString("color-set")) {
			status.add(ConfigError.CONFIG_MALFORMED);
		}
		
		if (this.getBootFolder() == null) {
			status.add(ConfigError.MISSING_BOOT_DIRECTORY);
		}
		
		if (this.getFrameJar() == null) {
			status.add(ConfigError.MISSING_FRAME_FILE);
		}
		
		return status.toArray(new ConfigError[status.size()]);
	}

	@Override
	public File getBootFolder() {
		File boot = new File(this.plugin.getDataFolder().getPath() + File.separator + this.cfg.getString("path-boot-folder"));
		
		if (boot.exists()) {
			if (!boot.isDirectory()) {
				return null;
			}
		} else {
			boot.mkdirs();
		}
		
		return boot;
	}

	@Override
	public File getFrameJar() {
		File jar = new File(this.plugin.getDataFolder().getPath() + File.separator + this.cfg.getString("path-frame-file"));
		
		if (!jar.exists() || !jar.isFile()) {
			return null;
		}
		
		return jar;
	}

	@Override
	public ColorSet getColorSet() {
		try {
			return ColorSet.valueOf(this.cfg.getString("color-set").toUpperCase());
		} catch (Exception ex) {
			if (Main.DEBUG) {
				ex.printStackTrace();
			}
			
			throw new GunGameException(ex.getMessage());
		}
	}

}
