package eu.securebit.gungame.interpreter.impl;

import java.util.List;

import org.bukkit.Location;

import eu.securebit.gungame.interpreter.LocationManager;
import eu.securebit.gungame.io.configs.FileGameConfig;

public class CraftLocationManager extends AbstractInterpreter<FileGameConfig> implements LocationManager {
	
	public CraftLocationManager(FileGameConfig file) {
		super(file);
	}
	
	@Override
	public void setLobbyLocation(Location lobby) {
		super.config.setLocationLobby(lobby);
	}

	@Override
	public void removeSpawnPoint(int spawnId) {
		super.config.removeSpawn(spawnId);
	}

	@Override
	public boolean containsSpawn(int spawnId) {
		return super.config.isSpawn(spawnId);
	}

	@Override
	public int addSpawnPoint(Location spawnPoint) {
		return super.config.addSpawn(spawnPoint);
	}

	@Override
	public int getSpawnPointCount() {
		return super.config.getSpawns().size();
	}

	@Override
	public Location getSpawnPoint(int spawnId) {
		return super.config.getSpawnById(spawnId);
	}

	@Override
	public Location getLobbyLocation() {
		return super.config.getLocationLobby();
	}
	
	@Override
	public List<Location> getSpawnPoints() {
		return super.config.getSpawns();
	}
	
	public FileGameConfig getFile() {
		return super.config;
	}

}
