package eu.securebit.gungame.interpreter;

import java.util.List;

import org.bukkit.Location;

import eu.securebit.gungame.interpreter.impl.CraftLocationManager;
import eu.securebit.gungame.io.configs.FileGameConfig;

public interface LocationManager extends Interpreter {
	
	public static LocationManager create(FileGameConfig file) {
		return new CraftLocationManager(file);
	}
	
	
	public abstract void setLobbyLocation(Location lobby);
	
	public abstract void removeSpawnPoint(int spawnId);
	
	public abstract boolean containsSpawn(int spawnId);
	
	public abstract int addSpawnPoint(Location spawnPoint);
	
	public abstract int getSpawnPointCount();
	
	public abstract Location getSpawnPoint(int spawnId);

	public abstract Location getLobbyLocation();
	
	public abstract List<Location> getSpawnPoints();
	
}
