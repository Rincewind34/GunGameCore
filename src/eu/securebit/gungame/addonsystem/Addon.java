package eu.securebit.gungame.addonsystem;

import java.io.File;
import java.util.List;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;

public abstract class Addon {
		
	public static final String ERROR_INIT = 						"Error-4000";
	
	public static final String ERROR_LOAD = 						"Error-5000-VAR0";
	
	public static final String ERROR_ENABLE = 						"Error-6000-VAR0";
	
	public static final String ERROR_ENABLE_DEPENCIES = 			"Error-6100-VAR0";
	
	public static final String ERROR_ENABLE_FRAME = 				"Error-6200-VAR0";
	
	public static final String ERROR_ENABLE_FRAME_REQUIRED = 		"Error-6210-VAR0";
	
	public static final String ERROR_ENABLE_FRAME_INCOMPATIBLE = 	"Error-6220-VAR0";
	
	public static LayoutError createErrorInit() {
		return new LayoutError("The addonloading could not be initialized!");
	}
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The addon (file: 'VAR0') could not be loaded!");
	}
	
	public static LayoutError createErrorEnable() {
		return new LayoutError("The addon (file: 'VAR0') could not be enabled!");
	}
	
	public static LayoutError createErrorDepencies() {
		return new LayoutError("The addon (file: 'VAR0') requiers the plugin 'VAR1'!", Addon.ERROR_ENABLE);
	}
	
	public static LayoutError createErrorFrame() {
		return new LayoutError("The addon (file: 'VAR0') could not be enabled bcause of the frame!", Addon.ERROR_ENABLE);
	}
	
	public static LayoutError createErrorFrameRequired() {
		return new LayoutError("The addon (file: 'VAR0') requiers a enabled frame!", Addon.ERROR_ENABLE_FRAME);
	}
	
	public static LayoutError createErrorFrameIncompatible() {
		return new LayoutError("The addon (file: 'VAR0') is incompatible with the enabled frame!", Addon.ERROR_ENABLE_FRAME);
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
	
	public abstract boolean requiresFrame();
	
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
