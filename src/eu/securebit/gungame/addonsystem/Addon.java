package eu.securebit.gungame.addonsystem;

import java.io.File;
import java.util.List;

import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;

public abstract class Addon {
	
	public static final String ERROR_INIT = "4000";
	
	public static String errorLoad(String addon) {
		return "5000-" + addon;
	}
	
	public static String errorLoadMainclass(String addon) {
		return "5100-" + addon;
	}
	
	public static String errorEnable(String addon) {
		return "6000-" + addon;
	}
	
	public static Error createErrorLoad(String addon) {
		return new SimpleError("The addon '" + addon + "' could not be loaded!", Addon.errorEnable(addon));
	}
	
	public static Error createErrorLoadMainclass(String addon) {
		return new SimpleError("No mainclass in the addon '" + addon + "' found!", Addon.errorLoad(addon));
	}
	
	public static Error createErrorEnable(String addon) {
		return new SimpleError("The addon '" + addon + "' could not be enabled!");
	}
	
	
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
	
	public abstract List<String> getDependencies();
	
	public abstract List<Integer> getIncompatibleFrames();
	
	
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
