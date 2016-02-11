package eu.securebit.gungame.io.configs;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;

public interface FileMap extends FileGunGameConfig {
	
	public static final String ERROR_LOAD = 			"Error-7610-VAR0";
	
	public static final String ERROR_FOLDER = 			"Error-7611-VAR0";
	
	public static final String ERROR_CREATE = 			"Error-7612-VAR0";
	
	public static final String ERROR_MALFORMED = 		"Error-7613-VAR0";
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The levelsfile 'VAR0' could not be loaded!");
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The levelsfile 'VAR0' is a directory!", FileMap.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The levelsfile 'VAR0' could not be created!", FileMap.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformed() {
		return new LayoutErrorFixable("The levelsfile 'VAR0' is malformed!", FileMap.ERROR_LOAD, () -> {
			// TODO fix path / delete
		});
	}
	
	public abstract void setNextSpawnId(int nextId);
	
	public abstract void setSpawns(Map<Integer, Location> spawns);
	
	public abstract int getNextSpawnId();
	
	public abstract String getMapName();
	
	public abstract List<String> getBuilders();
	
	public abstract Map<Integer, Location> getSpawns();
	
}
