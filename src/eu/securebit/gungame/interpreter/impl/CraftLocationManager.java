package eu.securebit.gungame.interpreter.impl;

import java.util.List;

import org.bukkit.Location;

import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.interpreter.LocationManager;
import eu.securebit.gungame.io.configs.FileGameConfig;

public class CraftLocationManager implements LocationManager {
	
	private FileGameConfig file;
	
	public CraftLocationManager(FileGameConfig file) {
		if (!file.isAccessable()) {
			throw new GunGameException("Cannot interpret config-file '" + file.getAbsolutePath() + "'!");
		}
		
		this.file = file;
	}
	
	@Override
	public void setLobbyLocation(Location lobby) {
		this.file.setLocationLobby(lobby);
	}

	@Override
	public void removeSpawnPoint(int spawnId) {
		this.file.removeSpawn(spawnId);
	}

	@Override
	public boolean containsSpawn(int spawnId) {
		return this.file.isSpawn(spawnId);
	}

	@Override
	public int addSpawnPoint(Location spawnPoint) {
		return this.file.addSpawn(spawnPoint);
	}

	@Override
	public int getSpawnPointCount() {
		return this.file.getSpawns().size();
	}

	@Override
	public Location getSpawnPoint(int spawnId) {
		return this.file.getSpawnById(spawnId);
	}

	@Override
	public Location getLobbyLocation() {
		return this.file.getLocationLobby();
	}
	
	@Override
	public List<Location> getSpawnPoints() {
		return this.file.getSpawns();
	}
	
	public FileGameConfig getFile() {
		return this.file;
	}

}
