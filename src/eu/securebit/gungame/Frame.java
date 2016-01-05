package eu.securebit.gungame;

import java.io.File;

import org.bukkit.entity.Player;

public abstract class Frame {
	
	private File dataFolder;
	
	public final void enable(FrameProperties properties) {
		this.dataFolder = properties.getDataFolder();
		this.onEnable();
	}
	
	public final void disable() {
		this.onDisable();
	}
	
	public final File getDataFolder() {
		return this.dataFolder;
	}
	
	public abstract void onEnable();
	
	public abstract void onDisable();
	
	public abstract boolean isInGame(Player player);
	
	public abstract String getVersion();
	
	public abstract String getName();
	
	public abstract GunGame getGame(Player player);
	
	
	public static class FrameProperties {
		
		private final File dataFolder;
		
		public FrameProperties(File dataFolder) {
			this.dataFolder = dataFolder;
		}
		
		public File getDataFolder() {
			return this.dataFolder;
		}
	}
}
