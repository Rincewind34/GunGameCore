package eu.securebit.gungame.io.configs;

import org.bukkit.Location;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;

public interface FileGameConfig extends FileGunGameConfig {
	
	public static final String ERROR_MAIN = 				"Error-7200-VAR0";
	
	public static final String ERROR_LOAD = 				"Error-7210-VAR0";
	
	public static final String ERROR_FOLDER = 				"Error-7211-VAR0";
	
	public static final String ERROR_CREATE = 				"Error-7212-VAR0";

	public static final String ERROR_MALFORMED = 			"Error-7213-VAR0";
	
	public static final String ERROR_SPAWNID = 				"Error-7220-VAR0";
	
	public static final String ERROR_LEVELCOUNT = 			"Error-7230-VAR0";
	
	public static final String ERROR_LEVELCOUNT_GREATER = 	"Error-7231-VAR0";
	
	public static final String ERROR_LEVELCOUNT_SMALLER = 	"Error-7232-VAR0";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("In the gameconfigfile 'VAR0' occured an error!");
	}
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The gameconfigfile 'VAR0' could not be loaded!", FileGameConfig.ERROR_MAIN);
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
	
	public static LayoutError createErrorSpawnId() {
		return new LayoutErrorFixable("The next-spawn-id in the gameconfigfile 'VAR0' is already in use!", FileGameConfig.ERROR_MAIN, () -> {
			// TODO recalculte nextspawnid
		});
	}
	
	public static LayoutError createErrorLevelCount() {
		return new LayoutError("The startlevel in the gameconfigfile 'VAR0' is invalid!", FileGameConfig.ERROR_MAIN);
	}
	
	public static LayoutError createErrorLevelCountGreater() {
		return new LayoutErrorFixable("The startlevel in the gameconfigfile 'VAR0' has to be greater than 0!", FileGameConfig.ERROR_LEVELCOUNT, () -> {
			// TODO change value to 1
		});
	}
	
	public static LayoutError createErrorLevelCountSmaller() {
		return new LayoutErrorFixable("The startlevel in the gameconfigfile 'VAR0' has to be smaller than levelcount!", FileGameConfig.ERROR_LEVELCOUNT, () -> {
			// TODO change value to [<levelcount> - 1]
		});
	}
	
	
	public abstract void setEditMode(boolean enabled);
	
	public abstract void setMuted(boolean muted);
	
	public abstract void setStartLevel(int level);
	
	public abstract void setLocationLobby(Location loc);
	
	public abstract boolean isEditMode();
	
	public abstract boolean isMuted();
	
	public abstract int getStartLevel();
	
	public abstract int getMinPlayers();
	
	public abstract int getMaxPlayers();
	
	public abstract String getFileLevelsLocation();
	
	public abstract String getFileMessagesLocation();
	
	public abstract String getFileScoreboardLocation();
	
	public abstract String getFileOptionsLocation();
	
	public abstract Location getLocationLobby();
	
}
