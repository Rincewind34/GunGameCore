package eu.securebit.gungame.interpreter.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.World;

import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.interpreter.GunGameMap;
import eu.securebit.gungame.io.configs.FileMap;

public class CraftGunGameMap extends AbstractInterpreter<FileMap> implements GunGameMap {
	
	private World world;

	public CraftGunGameMap(FileMap file, World world) {
		super(file, GunGameMap.ERROR_MAIN, GunGameMap.ERROR_INTERPRET);
		
		this.world = world;
		
		if (this.wasSuccessful()) {
			int nextId = super.config.getNextSpawnId();
			
			if (super.config.getSpawns().containsKey(nextId)) {
				this.getErrorHandler().throwError(this.createError(GunGameMap.ERROR_SPAWNID));
			}
			
			if (super.config.getSpawns().size() < 1) {
				this.getErrorHandler().throwError(this.createError(GunGameMap.ERROR_SPAWNCOUNT));
			}
		}
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
		if (spawnPoint.getWorld() != null && !spawnPoint.getWorld().equals(this.world)) {
			throw GunGameException.invalidWorld(this.world.getName());
		}
		
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
		return this.prepareLocation(super.config.getSpawns().get(spawnId));
	}
	
	@Override
	public List<Integer> getSpawnPointIds() {
		return super.config.getSpawns().keySet().stream().collect(Collectors.toList());
	}
	
	@Override
	public List<Location> getSpawnPoints() {
		List<Location> spawnPoints = super.config.getSpawns().values().stream().collect(Collectors.toList());
		
		for (Location spawnPoint : spawnPoints) {
			this.prepareLocation(spawnPoint);
		}
		
		return spawnPoints;
	}
	
	private void calculteNextSpawnId() {
		int newId = 0;
		
		while (this.containsSpawn(newId)) {
			newId = newId + 1;
		}
		
		super.config.setNextSpawnId(newId);
	}
	
	private Location prepareLocation(Location input) {
		input.setWorld(this.world);
		return input;
	}

}
