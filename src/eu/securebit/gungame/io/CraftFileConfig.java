package eu.securebit.gungame.io;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CraftFileConfig implements FileConfig {

	private final Plugin plugin;
	private File file;
	private FileConfiguration cfg;
	
	public CraftFileConfig(final Plugin plugin) {
		this.plugin = plugin;
		this.file = new File(this.plugin.getDataFolder(), "config.yml");
		this.cfg = null;
	}
	
	@Override
	public void initialize() {
		this.cfg = YamlConfiguration.loadConfiguration(this.file);
		this.cfg.addDefault("path-frame-file", "frames/frame_default.jar");
		this.cfg.addDefault("path-boot-folder", "frames/default");
		this.cfg.options().copyDefaults(true);
		
		try {
			this.cfg.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean isValid() {
		try {
			return this.getBootFolder() != null && this.getFrameJar() != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public File getBootFolder() {
		File boot = new File(this.plugin.getDataFolder().getPath() + File.separator + this.cfg.getString("path-boot-folder"));
		if (!boot.exists() || !boot.isDirectory()) {
			boot.mkdirs();
			return null;
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

}