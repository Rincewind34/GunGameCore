package eu.securebit.gungame.util;

import eu.securebit.gungame.errorhandling.layouts.LayoutWarning;

public class Warnings {
	
	public static final String WARNING_GAME_MUTE = "Warning-11";
	
	public static final String WARNING_GAME_EDITMODE = "Warning-12";
	
	public static final String WARNING_OPTIONS = "Warning-21";
	
	public static final String ERROR_FRAME_DISABLE = "Warning-31";
	
	public static final String ERROR_ADDON_DISABLE = "Warning-32-VAR0";
	
	
	public static LayoutWarning createWarningGameMute() {
		return new LayoutWarning("The mute-value could not be saved in the configfile!");
	}
	
	public static LayoutWarning createWarningGameEditMode() {
		return new LayoutWarning("The editmode-value could not be saved in the configfile!");
	}
	
	public static LayoutWarning createWarningOptions() {
		return new LayoutWarning("The value 'start-level' could not be checked!");
	}
	
	public static LayoutWarning createErrorFile() {
		return new LayoutWarning("The file is not accessable!");
	}
	
	public static LayoutWarning createErrorFrameDisable() {
		return new LayoutWarning("The frame could not be disabled!");
	}
	
	public static LayoutWarning createErrorAddonDisable() {
		return new LayoutWarning("The addon 'VAR0' could not be disabled!");
	}
	
}
