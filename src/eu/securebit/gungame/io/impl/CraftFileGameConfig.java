package eu.securebit.gungame.io.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;

import eu.securebit.gungame.exception.MalformedConfigException;
import eu.securebit.gungame.io.FileGameConfig;
import eu.securebit.gungame.io.FileLevels;
import eu.securebit.gungame.io.util.AbstractFile;
import eu.securebit.gungame.io.util.ConfigUtil;
import eu.securebit.gungame.io.util.DataUtil;

public abstract class CraftFileGameConfig extends AbstractFile implements FileGameConfig {
	
	public CraftFileGameConfig(String path, String name, World world) {
		super(path, name);
		
		ConfigUtil.setLocation(super.config, "locations.lobby", world.getSpawnLocation(), true);
		this.save();
	}
	
	@Override
	public void addDefaults() {
		String dataFolder = "data" + File.separator;
		
		super.config.addDefault("editmode", true);
		super.config.addDefault("muted", false);
		super.config.addDefault("files.levels", dataFolder + "levels.yml");
		super.config.addDefault("files.messages", dataFolder + "messages.yml");
		super.config.addDefault("files.scoreboard", dataFolder + "scoreboard.yml");
		super.config.addDefault("options.reset-level", false);
		super.config.addDefault("options.autorespawn", true);
		super.config.addDefault("options.care-natural-death", true);
		super.config.addDefault("start-level", 1);
		super.config.addDefault("playercount.minimal", 1);
		super.config.addDefault("playercount.maximal", 3);
		super.config.addDefault("locations.spawns", Arrays.asList());
		super.config.addDefault("next-spawn-id", 0);
	}

	@Override
	public boolean isEditMode() {
		return super.config.getBoolean("editmode");
	}
	
	@Override
	public boolean isMuted() {
		return super.config.getBoolean("muted");
	}

	@Override
	public boolean isLevelResetAfterDeath() {
		return super.config.getBoolean("options.reset-level");
	}

	@Override
	public boolean isAutoRespawn() {
		return super.config.getBoolean("options.autorespawn");
	}

	@Override
	public boolean isLevelDowngradeOnNaturalDeath() {
		return super.config.getBoolean("options.care-natural-death");
	}

	@Override
	public boolean isSpawn(int id) {
		for (String entry : super.config.getStringList("locations.spawns")) {
			int spawnId = NumberConversions.toInt(DataUtil.getFromCSV(entry, 0));
			if (spawnId == id) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int getStartLevel() {
		return super.config.getInt("start-level");
	}
	
	@Override
	public int getMinPlayers() {
		return super.config.getInt("playercount.minimal");
	}
	
	@Override
	public int getMaxPlayers() {
		return super.config.getInt("playercount.maximal");
	}
	
	@Override
	public String getFileLevelsLocation() {
		return super.config.getString("files.levels");
	}
	
	@Override
	public String getFileMessagesLocation() {
		return super.config.getString("files.messages");
	}

	@Override
	public String getFileScoreboardLocation() {
		return super.config.getString("files.scoreboard");
	}
	
	@Override
	public Location getLocationLobby() {
		return ConfigUtil.getLocation(super.config, "locations.lobby");
	}
	
	@Override
	public Location getSpawnById(int id) {
		for (String entry : super.config.getStringList("locations.spawns")) {
			if (NumberConversions.toInt(DataUtil.getFromCSV(entry, 0)) == id) {
				return this.readSpawnLocation(entry).getValue();
			}
		}
		
		return null;
	}

	@Override
	public List<Location> getSpawns() {
		List<Location> spawns = new ArrayList<>();
		
		for (String entry : super.config.getStringList("locations.spawns")) {
			Location loc = this.readSpawnLocation(entry).getValue();
			if (loc == null) {
				continue;
			}
			
			spawns.add(loc);
		}
		
		return spawns;
	}
	
	@Override
	public Map<Integer, Location> getSpawnsMap() {
		Map<Integer, Location> spawns = new HashMap<>();
		
		for (String spawn : super.config.getStringList("locations.spawns")) {
			Entry<Integer, Location> entry = this.readSpawnLocation(spawn);
			
			if (entry.getValue() == null) {
				continue;
			}
			
			spawns.put(entry.getKey(), entry.getValue());
		}
		
		return spawns;
	}

	@Override
	public void setEditMode(boolean enabled) {
		super.config.set("editmode", enabled);
		this.save();
	}
	
	@Override
	public void setMuted(boolean muted) {
		super.config.set("muted", muted);
		this.save();
	}

	@Override
	public void setLevelResetAfterDeath(boolean enabled) {
		super.config.set("options.reset-level", enabled);
		this.save();
	}

	@Override
	public void setAutoRespawn(boolean enabled) {
		super.config.set("options.autorespawn", enabled);
		this.save();
	}

	@Override
	public void setLevelDowngradeOnNaturalDeath(boolean enabled) {
		super.config.set("options.care-natural-death", enabled);
		this.save();
	}

	@Override
	public void setStartLevel(int level) {
		super.config.set("start-level", level);
		this.save();
	}

	@Override
	public void setLocationLobby(Location loc) {
		ConfigUtil.setLocation(super.config, "locations.lobby", loc, false);
		this.save();
	}

	@Override
	public void resetAllSpawns() {
		super.config.set("locations.spawns", Arrays.asList());
		super.config.set("last-spawn-id", 0);
		this.save();
	}

	@Override
	public boolean removeSpawn(int id) {
		boolean success = false;
		List<String> spawnList = new ArrayList<>();
		
		for (String entry : super.config.getStringList("locations.spawns")) {
			if (NumberConversions.toInt(DataUtil.getFromCSV(entry, 0)) == id) {
				success = true;
			} else {
				spawnList.add(entry);
			}
		}
		
		if (success) {
			super.config.set("locations.spawns", spawnList);
			
			if (id < super.config.getInt("next-spawn-id")) {
				super.config.set("next-spawn-id", id);
			}
			
			this.save();
		}
		
		return success;
	}

	@Override
	public int addSpawn(Location loc) {
		int nextId = super.config.getInt("next-spawn-id");
		List<String> spawnList = super.config.getStringList("locations.spawns");
		
		spawnList.add(DataUtil.toCSV(nextId, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
		super.config.set("locations.spawns", spawnList);
		
		int newId = 0;
		
		while (this.isSpawn(newId)) {
			newId = newId + 1;
		}
		
		super.config.set("next-spawn-id", newId);
		this.save();
		
		return nextId;
	}
	
	private Entry<Integer, Location> readSpawnLocation(String csv) {
		int id = NumberConversions.toInt(DataUtil.getFromCSV(csv, 0));
		
		World world = Bukkit.getWorld(DataUtil.getFromCSV(csv, 1));
		
		if (world == null) {
			System.err.println("Reading spawn point with id " + id + " failure, because the world does not exists, SKIPPING");
			return null;
		}
		
		double x = NumberConversions.toDouble(DataUtil.getFromCSV(csv, 2));
		double y = NumberConversions.toDouble(DataUtil.getFromCSV(csv, 3));
		double z = NumberConversions.toDouble(DataUtil.getFromCSV(csv, 4));
		float yaw = NumberConversions.toFloat(DataUtil.getFromCSV(csv, 5));
		float pitch = NumberConversions.toFloat(DataUtil.getFromCSV(csv, 6));
		return new Entry<Integer, Location>() {
			
			@Override
			public Location setValue(Location value) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public Location getValue() {
				return new Location(world, x, y, z, yaw, pitch);
			}
			
			@Override
			public Integer getKey() {
				return id;
			}
		};
	}

	
	// -------------------------------- //
	// ---------- Validation ---------- //
	// -------------------------------- //
	
	
	@Override
	public void validate() throws MalformedConfigException {
		{
			int nextId = super.config.getInt("next-spawn-id");
			
			if (this.isSpawn(nextId)) {
				throw new MalformedConfigException("The next-spawn-id is already in use!");
			}
		} {
			int startLevel = super.config.getInt("start-level");
			int levelCount = this.getFileLevels().getLevelCount();
			
			if (startLevel < 1) {
				throw new MalformedConfigException("The startlevel has to be greater than 0!");
			}
			
			if (startLevel > levelCount) {
				throw new MalformedConfigException("The startlevel has to be smaler than the levelcount (currently: " + levelCount + ", levelcount: " + levelCount + ")!");
			}
		}
	}
	
	public abstract FileLevels getFileLevels();

}