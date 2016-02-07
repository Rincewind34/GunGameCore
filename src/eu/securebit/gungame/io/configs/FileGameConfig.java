package eu.securebit.gungame.io.configs;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;

import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;

public interface FileGameConfig extends FileIdentifyable, FileConfig {
	
	public static final String ERROR_MAIN = 				"7200-VAR";
	
	public static final String ERROR_LOAD = 				"7210-VAR";
	
	public static final String ERROR_FOLDER = 				"7211-VAR";
	
	public static final String ERROR_CREATE = 				"7212-VAR";

	public static final String ERROR_MALFORMED = 			"7213-VAR";
	
	public static final String ERROR_SPAWNID = 				"7220-VAR";
	
	public static final String ERROR_LEVELCOUNT = 			"7230-VAR";
	
	public static final String ERROR_LEVELCOUNT_GREATER = 	"7231-VAR";
	
	public static final String ERROR_LEVELCOUNT_SMALLER = 	"7232-VAR";
	
	public static Error createErrorMain() {
		return new SimpleError("In the gameconfigfile 'VAR' occured an error!");
	}
	
	public static Error createErrorLoad() {
		return new SimpleError("The gameconfigfile 'VAR' could not be loaded!", FileGameConfig.ERROR_MAIN);
	}
	
	public static Error createErrorFolder() {
		return new SimpleFixableError("The gameconfigfile 'VAR' is a directory!", FileGameConfig.ERROR_LOAD, () -> {
			// TODO delete
		});
	}
	
	public static Error createErrorCreate() {
		return new SimpleError("The gameconfigfile 'VAR' could not be created!", FileGameConfig.ERROR_LOAD);
	}
	
	public static Error createErrorMalformed() {
		return new SimpleFixableError("The gameconfigfile 'VAR' is malformed!", FileGameConfig.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	public static Error createErrorSpawnId() {
		return new SimpleFixableError("The next-spawn-id in the gameconfigfile 'VAR' is already in use!", FileGameConfig.ERROR_MAIN, () -> {
			// TODO recalculte nextspawnid
		});
	}
	
	public static Error createErrorLevelCount() {
		return new SimpleError("The startlevel in the gameconfigfile 'VAR' is invalid!", FileGameConfig.ERROR_MAIN);
	}
	
	public static Error createErrorLevelCountGreater() {
		return new SimpleFixableError("The startlevel in the gameconfigfile 'VAR' has to be greater than 0!", FileGameConfig.ERROR_LEVELCOUNT, () -> {
			// TODO change value to 1
		});
	}
	
	public static Error createErrorLevelCountSmaller() {
		return new SimpleFixableError("The startlevel in the gameconfigfile 'VAR' has to be smaller than levelcount!", FileGameConfig.ERROR_LEVELCOUNT, () -> {
			// TODO change value to [<levelcount> - 1]
		});
	}
	
	
	public abstract boolean isEditMode();
	
	public abstract boolean isMuted();
	
	public abstract boolean isSpawn(int id);
	
	public abstract int getStartLevel();
	
	public abstract int getMinPlayers();
	
	public abstract int getMaxPlayers();
	
	public abstract String getFileLevelsLocation();
	
	public abstract String getFileMessagesLocation();
	
	public abstract String getFileScoreboardLocation();
	
	public abstract String getFileOptionsLocation();
	
	public abstract Location getLocationLobby();
	
	public abstract Location getSpawnById(int id);
	
	public abstract List<Location> getSpawns();
	
	public abstract Map<Integer, Location> getSpawnsMap();
	
	public abstract void setEditMode(boolean enabled);
	
	public abstract void setMuted(boolean muted);
	
	public abstract void setStartLevel(int level);
	
	public abstract void setLocationLobby(Location loc);
	
	public abstract void resetAllSpawns();
	
	public abstract boolean removeSpawn(int id);
	
	public abstract int addSpawn(Location loc);
	
}
