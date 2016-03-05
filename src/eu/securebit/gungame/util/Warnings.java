package eu.securebit.gungame.util;

import eu.securebit.gungame.errorhandling.layouts.LayoutWarning;

public class Warnings {
	
	public static final String WARNING_GAME_MUTE = "Warning-11-VAR0";
	
	public static final String WARNING_GAME_EDITMODE = "Warning-12-VAR0";
	
	public static final String WARNING_GAME_WORLD = "Warning-13-VAR0";
	
	public static final String WARNING_OPTIONS = "Warning-21";
	
	public static LayoutWarning createWarningGameMute() {
		return new LayoutWarning("The mute-value for the game 'VAR0' could not be saved in the configfile!");
	}
	
	public static LayoutWarning createWarningGameEditMode() {
		return new LayoutWarning("The editmode-value for the game 'VAR0 could not be saved in the configfile!");
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
