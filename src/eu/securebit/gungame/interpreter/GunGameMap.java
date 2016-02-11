package eu.securebit.gungame.interpreter;

import java.util.List;

import org.bukkit.Location;

import eu.securebit.gungame.interpreter.impl.CraftGunGameMap;
import eu.securebit.gungame.io.configs.FileMap;

public interface GunGameMap extends Interpreter {
	
	public static GunGameMap create(FileMap file) {
		return new CraftGunGameMap(file);
	}
	
	
	public abstract void removeSpawnPoint(int spawnId);
	
	public abstract boolean containsSpawn(int spawnId);
	
	public abstract int addSpawnPoint(Location spawnPoint);
	
	public abstract int getSpawnPointCount();
	
	public abstract Location getSpawnPoint(int spawnId);

	public abstract List<Location> getSpawnPoints();
	
}
