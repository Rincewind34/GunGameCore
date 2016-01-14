package eu.securebit.gungame.addonsystem;

import java.io.File;

public abstract class Addon {
	
	private File dataFolder;
	
	public final void enable(AddonProperties properties) {
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
	
	public abstract String getVersion();
	
	public abstract String getName();
	
	
	public static class AddonProperties {
		
		private final File dataFolder;
		
		public AddonProperties(File dataFolder) {
			this.dataFolder = dataFolder;
		}
		
		public File getDataFolder() {
			return this.dataFolder;
		}
	}
	
}
