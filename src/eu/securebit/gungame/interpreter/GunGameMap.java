package eu.securebit.gungame.interpreter;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.exception.GunGameFixException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.interpreter.impl.CraftGunGameMap;
import eu.securebit.gungame.io.configs.FileMap;

public interface GunGameMap extends Interpreter {
	
	public static final String ERROR_MAIN = 				"Error-7620-VAR0";
	
	public static final String ERROR_INTERPRET = 			"Error-7621-VAR0";
	
	public static final String ERROR_SPAWNID = 				"Error-7622-VAR0";
	
	public static final String ERROR_SPAWNCOUNT = 			"Error-7623-VAR0";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("An error occured while interpreting the map-file 'VAR0'!");
	}
	
	public static LayoutError createErrorInterpret() {
		return new LayoutError("Could not interpret the map-file 'VAR0'!", GunGameMap.ERROR_MAIN);
	}
	
	public static LayoutError createErrorSpawnId() {
		return new LayoutErrorFixable("The next-spawn-id in the map-file 'VAR0' is already in use!", GunGameMap.ERROR_MAIN, (variables) -> {
			if (variables.length == 1) {
				FileMap map = Core.getRootDirectory().getMapFile(variables[0]);
				
				if (map.isReady()) {
					int newId = 0;
					
					while (map.getSpawns().containsKey(newId)) {
						newId = newId + 1;
					}
					
					map.setNextSpawnId(newId);
				} else {
					throw GunGameErrorPresentException.create();
				}
			} else {
				throw GunGameFixException.variables();
			}
		}, false, "This fix will recalculate the next-spawn-id in the map-file 'VAR0'.");
	}
	
	public static LayoutError createErrorSpawnCount() {
		return new LayoutError("The map-file 'VAR0' has to contain at least one spawnpoint!", GunGameMap.ERROR_MAIN);
	}
	
	public static GunGameMap create(FileMap file, World world) {
		return new CraftGunGameMap(file, world);
	}
	
	public static GunGameMap create(FileMap file, ThrownError failCause) {
		return new CraftGunGameMap(file, failCause);
	}
	
	
	public abstract void removeSpawnPoint(int spawnId);
	
	public abstract boolean containsSpawn(int spawnId);
	
	public abstract int addSpawnPoint(Location spawnPoint);
	
	public abstract int getSpawnPointCount();
	
	public abstract Location getSpawnPoint(int spawnId);
	
	public abstract List<Integer> getSpawnPointIds();
	
	public abstract List<Location> getSpawnPoints();
	
}
