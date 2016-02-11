package eu.securebit.gungame.interpreter.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Location;

import eu.securebit.gungame.interpreter.GunGameMap;
import eu.securebit.gungame.io.configs.FileMap;

public class CraftGunGameMap extends AbstractInterpreter<FileMap> implements GunGameMap {
	
	public CraftGunGameMap(FileMap file) {
		super(file);
	}
	
	@Override
	public void removeSpawnPoint(int spawnId) {
		Map<Integer, Location> spawns = super.config.getSpawns();
		spawns.remove(spawnId);
		
		super.config.setSpawns(spawns);
		this.calculteNextSpawnId();
	}

	@Override
	public boolean containsSpawn(int spawnId) {
		for (int targetId : super.config.getSpawns().keySet()) {
			if (targetId == spawnId) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int addSpawnPoint(Location spawnPoint) {
		int nextId = super.config.getNextSpawnId();
		
		Map<Integer, Location> spawns = super.config.getSpawns();
		spawns.put(nextId, spawnPoint);
		
		super.config.setSpawns(spawns);
		this.calculteNextSpawnId();
		
		return nextId;
	}

	@Override
	public int getSpawnPointCount() {
		return super.config.getSpawns().size();
	}

	@Override
	public Location getSpawnPoint(int spawnId) {
		return super.config.getSpawns().get(spawnId);
	}

	@Override
	public List<Location> getSpawnPoints() {
		return super.config.getSpawns().values().stream().collect(Collectors.toList());
	}
	
	private void calculteNextSpawnId() {
		int newId = 0;
		
		while (this.containsSpawn(newId)) {
			newId = newId + 1;
		}
		
		super.config.setNextSpawnId(newId);
	}

}
