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
import eu.securebit.gungame.exception.GunGameIOException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.io.configs.FileLevels;
import eu.securebit.gungame.ioutil.ConfigUtil;
import eu.securebit.gungame.ioutil.DataUtil;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileGameConfig extends CraftFileGunGameConfig implements FileGameConfig {
	
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
				FileGameConfig.ERROR_MAIN, FileGameConfig.ERROR_LOAD, FileGameConfig.ERROR_FOLDER, FileGameConfig.ERROR_CREATE, FileGameConfig.ERROR_MALFORMED,
				"gameconfig");
		
		ConfigUtil.setLocation("location.lobby", world.getSpawnLocation(), CraftFileGameConfig.defaults);
	}
	
	@Override
	public boolean isEditMode() {
		this.checkAccessability();
		
		return super.config.getBoolean("editmode");
	}
	
	@Override
	public boolean isMuted() {
		this.checkAccessability();
		
		return super.config.getBoolean("muted");
	}
	
	@Override
	public int getStartLevel() {
		this.checkAccessability();
		
		return super.config.getInt("start-level");
	}
	
	@Override
	public int getMinPlayers() {
		this.checkAccessability();
		
		return super.config.getInt("playercount.minimal");
	}
	
	@Override
	public int getMaxPlayers() {
		this.checkAccessability();
		
		return super.config.getInt("playercount.maximal");
	}
	
	@Override
	public String getFileLevelsLocation() {
		this.checkAccessability();
		
		return super.config.getString("file.levels");
	}
	
	@Override
	public String getFileMessagesLocation() {
		this.checkAccessability();
		
		return super.config.getString("file.messages");
	}

	@Override
	public String getFileScoreboardLocation() {
		this.checkAccessability();
		
		return super.config.getString("file.scoreboard");
	}
	
	@Override
	public String getFileOptionsLocation() {
		this.checkAccessability();
		
		return super.config.getString("file.options");
	}
	
	@Override
	public Location getLocationLobby() {
		this.checkAccessability();
		
		return ConfigUtil.getLocation(super.config, "location.lobby");
	}
	
	@Override
	public Location getSpawnById(int id) {
		this.checkAccessability();
		
		for (String entry : super.config.getStringList("location.spawns")) {
			if (NumberConversions.toInt(DataUtil.getFromCSV(entry, 0)) == id) {
				return this.readSpawnLocation(entry).getValue();
			}
		}
		
		return null;
	}
	
	@Override
	public void setEditMode(boolean enabled) {
		this.checkAccessability();
		
		super.config.set("editmode", enabled);
		this.save();
	}
	
	@Override
	public void setMuted(boolean muted) {
		this.checkAccessability();
		
		super.config.set("muted", muted);
		this.save();
	}
	
	@Override
	public void setStartLevel(int level) {
		this.checkAccessability();
		
		super.config.set("start-level", level);
		this.save();
	}

	@Override
	public void setLocationLobby(Location loc) {
		this.checkAccessability();
		
		ConfigUtil.setLocation(super.config, "location.lobby", loc);
		this.save();
	}
	
	@Override
	public void addDefaults() {
		for (ConfigDefault entry : CraftFileGameConfig.defaults) {
			super.config.addDefault(entry.getPath(), entry.getValue());
		}
	}
	
	@Override
	public void setNextSpawnId(int nextId) {
		super.config.set("next-spawn-id", nextId);
		this.save();
	}

	@Override
	public void setSpawns(Map<Integer, Location> spawns) {
		List<String> list = new ArrayList<>();
		
		for (Entry<Integer, Location> entry : spawns.entrySet()) {
			list.add(this.createCSV(entry));
		}
		
		super.config.set("location.spawns", spawns);
		this.save();
	}

	@Override
	public int getNextSpawnId() {
		return super.config.getInt("next-spawn-id");
	}

	@Override
	public Map<Integer, Location> getSpawns() {
		List<String> list = super.config.getStringList("location.spawns");
		Map<Integer, Location> spawns = new HashMap<>();
		
		for (String csv : list) {
			spawns.put(this.readSpawnLocation(csv).getKey(), this.readSpawnLocation(csv).getValue());
		}
		
		return spawns;
	}
	
	@Override
	public void validate() {
		for (ConfigDefault entry : CraftFileGameConfig.defaults) {
			if (!entry.validate(super.config)) {
				super.handler.throwError(this.createError(FileGameConfig.ERROR_MALFORMED));
				break;
			}
		}
		
		{
			int nextId = this.getNextSpawnId();
			
			if (this.getSpawns().containsKey(nextId)) {
				super.handler.throwError(this.createError(FileGameConfig.ERROR_SPAWNID));
			}
		} {
			FileLevels fileLevels = Core.getRootDirectory().getLevelsFile(this.getFileLevelsLocation());
			
			if (fileLevels.isAccessable()) {
				int startLevel = super.config.getInt("start-level");
				int levelCount = fileLevels.getLevels().size();
				
				if (startLevel < 1) {
					super.handler.throwError(this.createError(FileGameConfig.ERROR_LEVELCOUNT_SMALLER));
				}
				
				if (startLevel > levelCount) {
					super.handler.throwError(this.createError(FileGameConfig.ERROR_LEVELCOUNT_GREATER));
				}
			} else {
				// TODO WARNING
			}
		}
	}
	
	private Entry<Integer, Location> readSpawnLocation(String csv) {
		int id = NumberConversions.toInt(DataUtil.getFromCSV(csv, 0));
		String worldName = DataUtil.getFromCSV(csv, 1);
		
		World world = Bukkit.getWorld(worldName);
		
		if (world == null) {
			throw GunGameIOException.unknownWorld(worldName);
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
	
	private String createCSV(Entry<Integer, Location> entry) {
		return DataUtil.toCSV(entry.getKey()
				, entry.getValue().getWorld().getName()
				, entry.getValue().getX()
				, entry.getValue().getY()
				, entry.getValue().getZ()
				, entry.getValue().getYaw()
				, entry.getValue().getPitch());
	}

}