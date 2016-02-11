package eu.securebit.gungame.util;

import eu.securebit.gungame.errorhandling.layouts.LayoutWarning;

public class Warnings {
	
	public static final String WARNING_GAME_MUTE = "10";
	
	public static final String WARNING_GAME_EDITMODE = "11";
	
	
	public static LayoutWarning createWarningGameMute() {
		return new LayoutWarning("The mute-value could not be saved in the configfile!");
	}
	
	public static LayoutWarning createWarningGameEditMode() {
		return new LayoutWarning("The editmode-value could not be saved in the configfile!");
	}
	
}
