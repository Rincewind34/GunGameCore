package eu.securebit.gungame.ioimpl.configs;

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

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.io.configs.FileLevels;
import eu.securebit.gungame.ioimpl.abstracts.AbstractConfig;
import eu.securebit.gungame.ioutil.ConfigUtil;
import eu.securebit.gungame.ioutil.DataUtil;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileGameConfig extends AbstractConfig implements FileGameConfig {
	
	private static final List<ConfigDefault> defaults = new ArrayList<>();
	
	static {
		String dataFolder = "data" + File.separator;
		
		CraftFileGameConfig.defaults.add(new ConfigDefault("editmode", true, boolean.class));
		CraftFileGameConfig.defaults.add(new ConfigDefault("muted", false, boolean.class));
		CraftFileGameConfig.defaults.add(new ConfigDefault("file.levels", dataFolder + "levels.yml", String.class));
		CraftFileGameConfig.defaults.add(new ConfigDefault("file.messages", dataFolder + "messages.yml", String.class));
		CraftFileGameConfig.defaults.add(new ConfigDefault("file.scoreboard", dataFolder + "scoreboard.yml", String.class));
		CraftFileGameConfig.defaults.add(new ConfigDefault("file.options", dataFolder + "options.yml", String.class));
		CraftFileGameConfig.defaults.add(new ConfigDefault("start-level", 1, int.class));
		CraftFileGameConfig.defaults.add(new ConfigDefault("playercount.minimal", 1, int.class));
		CraftFileGameConfig.defaults.add(new ConfigDefault("playercount.maximal", 3, int.class));
		CraftFileGameConfig.defaults.add(new ConfigDefault("location.spawns", Arrays.asList(), null));
		CraftFileGameConfig.defaults.add(new ConfigDefault("next-spawn-id", 0, int.class));
	}
	
	
	public CraftFileGameConfig(File file, CraftErrorHandler handler, World world) {
		super(file, handler,
				FileGameConfig.ERROR_MAIN, FileGameConfig.ERROR_LOAD, FileGameConfig.ERROR_FOLDER, FileGameConfig.ERROR_CREATE, FileGameConfig.ERROR_MALFORMED);
		
		ConfigUtil.setLocation("location.lobby", world.getSpawnLocation(), CraftFileGameConfig.defaults);
	}
	
	@Override
	public boolean isEditMode() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getBoolean("editmode");
	}
	
	@Override
	public boolean isMuted() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getBoolean("muted");
	}

	@Override
	public boolean isSpawn(int id) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		for (String entry : super.config.getStringList("location.spawns")) {
			int spawnId = NumberConversions.toInt(DataUtil.getFromCSV(entry, 0));
			if (spawnId == id) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int getStartLevel() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getInt("start-level");
	}
	
	@Override
	public int getMinPlayers() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getInt("playercount.minimal");
	}
	
	@Override
	public int getMaxPlayers() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getInt("playercount.maximal");
	}
	
	@Override
	public String getFileLevelsLocation() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getString("file.levels");
	}
	
	@Override
	public String getFileMessagesLocation() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getString("file.messages");
	}

	@Override
	public String getFileScoreboardLocation() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getString("file.scoreboard");
	}
	
	@Override
	public String getFileOptionsLocation() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return super.config.getString("file.options");
	}
	
	@Override
	public Location getLocationLobby() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		return ConfigUtil.getLocation(super.config, "location.lobby");
	}
	
	@Override
	public Location getSpawnById(int id) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		for (String entry : super.config.getStringList("location.spawns")) {
			if (NumberConversions.toInt(DataUtil.getFromCSV(entry, 0)) == id) {
				return this.readSpawnLocation(entry).getValue();
			}
		}
		
		return null;
	}

	@Override
	public List<Location> getSpawns() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		List<Location> spawns = new ArrayList<>();
		
		for (String entry : super.config.getStringList("location.spawns")) {
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
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		Map<Integer, Location> spawns = new HashMap<>();
		
		for (String spawn : super.config.getStringList("location.spawns")) {
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
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		super.config.set("editmode", enabled);
		this.save();
	}
	
	@Override
	public void setMuted(boolean muted) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		super.config.set("muted", muted);
		this.save();
	}
	
	@Override
	public void setStartLevel(int level) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		super.config.set("start-level", level);
		this.save();
	}

	@Override
	public void setLocationLobby(Location loc) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		ConfigUtil.setLocation(super.config, "location.lobby", loc);
		this.save();
	}

	@Override
	public void resetAllSpawns() {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		super.config.set("location.spawns", Arrays.asList());
		super.config.set("last-spawn-id", 0);
		this.save();
	}

	@Override
	public boolean removeSpawn(int id) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		boolean success = false;
		List<String> spawnList = new ArrayList<>();
		
		for (String entry : super.config.getStringList("location.spawns")) {
			if (NumberConversions.toInt(DataUtil.getFromCSV(entry, 0)) == id) {
				success = true;
			} else {
				spawnList.add(entry);
			}
		}
		
		if (success) {
			super.config.set("location.spawns", spawnList);
			
			if (id < super.config.getInt("next-spawn-id")) {
				super.config.set("next-spawn-id", id);
			}
			
			this.save();
		}
		
		return success;
	}

	@Override
	public int addSpawn(Location loc) {
		if (!this.isAccessable()) {
			throw new GunGameErrorPresentException();
		}
		
		int nextId = super.config.getInt("next-spawn-id");
		List<String> spawnList = super.config.getStringList("location.spawns");
		
		spawnList.add(DataUtil.toCSV(nextId, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
		super.config.set("location.spawns", spawnList);
		
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
			throw new GunGameException("Error while reading the world! Does it exist?");
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
	
	@Override
	public void addDefaults() {
		for (ConfigDefault entry : CraftFileGameConfig.defaults) {
			super.config.addDefault(entry.getPath(), entry.getValue());
		}
	}
	
	@Override
	public String getAbsolutePath() {
		return super.file.getAbsolutePath();
	}
	
	@Override
	public String getIdentifier() {
		return "gameconfig";
	}
	
	@Override
	public void validate() {
		for (ConfigDefault entry : CraftFileGameConfig.defaults) {
			if (!entry.validate(super.config)) {
				this.throwError(FileGameConfig.ERROR_MALFORMED);
				break;
			}
		}
		
		{
			int nextId = super.config.getInt("next-spawn-id");
			
			if (this.isSpawn(nextId)) {
				this.throwError(FileGameConfig.ERROR_SPAWNID);
			}
		} {
			FileLevels fileLevels = Core.getRootDirectory().getLevelsFile(this.getFileLevelsLocation());
			
			if (fileLevels.isAccessable()) {
				int startLevel = super.config.getInt("start-level");
				int levelCount = fileLevels.getLevelCount();
				
				if (startLevel < 1) {
					this.throwError(FileGameConfig.ERROR_LEVELCOUNT_SMALLER);
				}
				
				if (startLevel > levelCount) {
					this.throwError(FileGameConfig.ERROR_LEVELCOUNT_GREATER);
				}
			} else {
				// TODO WARNING
			}
		}

	}

}