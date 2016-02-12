package eu.securebit.gungame.io.configs;

import org.bukkit.Location;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;

public interface FileGameConfig extends FileGunGameConfig {
	
	public static final String ERROR_LOAD = 				"Error-7110-VAR0";
	
	public static final String ERROR_FOLDER = 				"Error-7111-VAR0";
	
	public static final String ERROR_CREATE = 				"Error-7112-VAR0";

	public static final String ERROR_MALFORMED = 			"Error-7113-VAR0";
	
	
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The gameconfigfile 'VAR0' could not be loaded!");
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The gameconfigfile 'VAR0' is a directory!", FileGameConfig.ERROR_LOAD, () -> {
			// TODO delete
		});
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The gameconfigfile 'VAR0' could not be created!", FileGameConfig.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformed() {
		return new LayoutErrorFixable("The gameconfigfile 'VAR0' is malformed!", FileGameConfig.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	
	public abstract void setEditMode(boolean enabled);
	
	public abstract void setMuted(boolean muted);
	
	public abstract void setLocationLobby(Location loc);
	
	public abstract boolean isEditMode();
	
	public abstract boolean isMuted();
	
	public abstract int getMinPlayers();
	
	public abstract int getMaxPlayers();
	
	public abstract String getFileLevelsLocation();
	
	public abstract String getFileMessagesLocation();
	
	public abstract String getFileScoreboardLocation();
	
	public abstract String getFileOptionsLocation();
	
	public abstract String getFileMapLocation();
	
	public abstract Location getLocationLobby();
	
}
