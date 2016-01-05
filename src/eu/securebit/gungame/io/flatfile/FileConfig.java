package eu.securebit.gungame.io.flatfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.securebit.io.AbstractFileManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.exception.MalformedConfigException;
import eu.securebit.gungame.io.MainConfig;
import eu.securebit.gungame.io.util.ConfigUtil;
import eu.securebit.gungame.io.util.DataUtil;

public class FileConfig extends AbstractFileManager implements MainConfig {

	public FileConfig() {
		super("config.yml");
	}

	@Override
	public void addDefaults() {
		// --- General Settings --- //
		super.config.addDefault("EditMode", true);
		super.config.addDefault("ResetLevelAfterDeath", false); // true = Level 1; false = CurrentLevel - 1
		super.config.addDefault("AutoRespawn", Main.DEBUG);
		super.config.addDefault("StartLevel", 1);
		super.config.addDefault("MinPlayers", Main.DEBUG ? 1 : 3);
		super.config.addDefault("MaxPlayers", Main.DEBUG ? 3 : 16);
		super.config.addDefault("LevelDowngradeOnNaturalDeath", true);
		super.config.addDefault("Scoreboard.Enabled", true);
		super.config.addDefault("Scoreboard.Title", "&7===== &eGunGame &7=====");
		super.config.addDefault("Scoreboard.Format", "&7${player}");
		
		// --- Message Settings --- //
		super.config.addDefault("Messages.Prefix", "&7[&eGunGame&7]");
		super.config.addDefault("Messages.LobbyCountdown", "&7The game starts in &e${time} &7seconds.");
		super.config.addDefault("Messages.GracePeriodStart", "&7All players are invulnerable for 15 seconds now.");
		super.config.addDefault("Messages.GraceCountdown", "&7The graceperiod ends in &e${time} &7seconds.");
		super.config.addDefault("Messages.GraceEnd", "&eDamage enabled - Fight!");
		super.config.addDefault("Messages.CountdownEnd", "&cServer shutdown in ${time} seconds.");
		super.config.addDefault("Messages.MapTeleport", "&7All players have been teleported to the map.");
		super.config.addDefault("Messages.KillBroadcast", "&e${victim} &7was killed by &e${killer}&7.");
		super.config.addDefault("Messages.NaturalDeath", "&7The player &e${victim} &7died.");
		super.config.addDefault("Messages.PlayerJoin", "&e${player} &7joined the game.");
		super.config.addDefault("Messages.PlayerQuit", "&e${player} &7left the game.");
		super.config.addDefault("Messages.Respawn", "&7You are now level &e${level}&7.");
		super.config.addDefault("Messages.Winner", "&e&lCongratulations! &r&e${winner} is the winner of this game!");
		
		// --- Locations --- //
		super.config.addDefault("Locations.SpawnList", Arrays.asList());
		ConfigUtil.setLocation(super.config, "Locations.Lobby", Bukkit.getWorlds().get(0).getSpawnLocation(), true);
		
		super.config.addDefault("LastSpawnID", 0);
	}

	@Override
	public boolean isEditMode() {
		return super.config.getBoolean("EditMode");
	}

	@Override
	public boolean isLevelResetAfterDeath() {
		return super.config.getBoolean("ResetLevelAfterDeath");
	}

	@Override
	public boolean isAutoRespawn() {
		return super.config.getBoolean("AutoRespawn");
	}

	@Override
	public boolean isLevelDowngradeOnNaturalDeath() {
		return super.config.getBoolean("LevelDowngradeOnNaturalDeath");
	}

	@Override
	public boolean isScoreboard() {
		return super.config.getBoolean("Scoreboard.Enabled");
	}
	

	@Override
	public boolean isSpawn(int id) {
		for (String entry : super.config.getStringList("Locations.SpawnList")) {
			int spawnId = NumberConversions.toInt(DataUtil.getFromCSV(entry, 0));
			if (spawnId == id) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int getStartLevel() {
		return super.config.getInt("StartLevel");
	}
	
	@Override
	public int getMinPlayers() {
		return super.config.getInt("MinPlayers");
	}
	
	@Override
	public int getMaxPlayers() {
		return super.config.getInt("MaxPlayers");
	}

	@Override
	public String getScoreboardTitle() {
		return ChatColor.translateAlternateColorCodes('&', super.config.getString("Scoreboard.Title"));
	}

	@Override
	public String getScoreboardFormat() {
		return super.config.getString("Scoreboard.Format");
	}

	@Override
	public Location getLocationLobby() {
		return ConfigUtil.getLocation(super.config, "Locations.Lobby");
	}
	
	@Override
	public Location getSpawnById(int id) {
		for (String entry : super.config.getStringList("Locations.SpawnList")) {
			if (NumberConversions.toInt(DataUtil.getFromCSV(entry, 0)) == id) {
				return this.readSpawnLocation(entry);
			}
		}
		
		return null;
	}

	@Override
	public List<Location> getSpawns() {
		List<Location> spawns = new ArrayList<>();
		// CSV (Comma Separated Value)
		for (String entry : super.config.getStringList("Locations.SpawnList")) {
			Location loc = this.readSpawnLocation(entry);
			if (loc == null) {
				continue;
			}
			
			spawns.add(loc);
		}
		
		return spawns;
	}

	@Override
	public void setEditMode(boolean enabled) {
		super.config.set("EditMode", enabled);
		this.save();
	}

	@Override
	public void setLevelResetAfterDeath(boolean enabled) {
		super.config.set("ResetLevelAfterDeath", enabled);
		this.save();
	}

	@Override
	public void setAutoRespawn(boolean enabled) {
		super.config.set("AutoRespawn", enabled);
		this.save();
	}

	@Override
	public void setLevelDowngradeOnNaturalDeath(boolean enabled) {
		super.config.set("LevelDowngradeOnNaturalDeath", enabled);
		this.save();
	}

	@Override
	public void setScoreboard(boolean enabled) {
		super.config.set("Scoreboard.Enabled", enabled);
		this.save();
	}

	@Override
	public void setStartLevel(int level) {
		super.config.set("StartLevel", level);
		this.save();
	}

	@Override
	public void setScoreboardTitle(String title) {
		super.config.set("Scoreboard.Title", title.replace("§", "&"));
		this.save();
	}

	@Override
	public void setScoreboardFormat(String format) {
		super.config.set("Scoreboard.Format", format);
		this.save();
	}

	@Override
	public void setLocationLobby(Location loc) {
		ConfigUtil.setLocation(super.config, "Locations.Lobby", loc, false);
		this.save();
	}

	@Override
	public void resetAllSpawns() {
		super.config.set("Locations.SpawnList", Arrays.asList());
		super.config.set("LastSpawnID", 0);
		this.save();
	}

	@Override
	public boolean removeSpawn(int id) {
		boolean success = false;
		List<String> spawnList = new ArrayList<>();
		for (String entry : super.config.getStringList("Locations.SpawnList")) {
			if (NumberConversions.toInt(DataUtil.getFromCSV(entry, 0)) == id) {
				success = true;
			} else {
				spawnList.add(entry);
			}
		}
		
		if (success) {
			super.config.set("Locations.SpawnList", spawnList);
			super.config.set("LastSpawnID", 0);
			this.save();
		}
		
		return success;
	}

	@Override
	public int addSpawn(Location loc) {
		int lastInsertId = super.config.getInt("LastSpawnID");
		List<String> spawnList = super.config.getStringList("Locations.SpawnList");
		spawnList.add(DataUtil.toCSV(++lastInsertId, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
		super.config.set("Locations.SpawnList", spawnList);
		super.config.set("LastSpawnID", lastInsertId);
		this.save();
		return lastInsertId;
	}
	
	
	// ------------------------------------------------ //
	// ---------- MessageFile Implementation ---------- //
	// ------------------------------------------------ //
	
	
	@Override
	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', super.config.getString("Messages.Prefix")) + " §r";
	}

	@Override
	public String getMessageLobbyCountdown(int seconds) {
		return this.replace(this.prepare(super.config.getString("Messages.LobbyCountdown")), "time", seconds);
	}

	@Override
	public String getMessageGraceStart() {
		return this.prepare(super.config.getString("Messages.GracePeriodStart"));
	}

	@Override
	public String getMessageGraceCountdown(int seconds) {
		return this.replace(this.prepare(super.config.getString("Messages.GraceCountdown")), "time", seconds);
	}

	@Override
	public String getMessageGraceEnd() {
		return this.prepare(super.config.getString("Messages.GraceEnd"));
	}

	@Override
	public String getMessageCountdownEnd(int seconds) {
		return this.replace(this.prepare(super.config.getString("Messages.CountdownEnd")), "time", seconds);
	}

	@Override
	public String getMessageMapTeleport() {
		return this.prepare(super.config.getString("Messages.MapTeleport"));
	}

	@Override
	public String getMessageKillBroadcast(Player killer, Player victim) {
		String msg = this.prepare(super.config.getString("Messages.KillBroadcast"));
		msg = this.replace(msg, "killer", killer.getName());
		msg = this.replace(msg, "victim", victim.getName());
		return msg;
	}

	@Override
	public String getMessageNaturalDeath(Player victim) {
		return this.replace(this.prepare(super.config.getString("Messages.NaturalDeath")), "victim", victim.getDisplayName());
	}

	@Override
	public String getMessageJoin() {
		return this.prepare(super.config.getString("Messages.PlayerJoin"));
	}

	@Override
	public String getMessageQuit() {
		return this.prepare(super.config.getString("Messages.PlayerQuit"));
	}

	@Override
	public String getMessageRespawn(int newLevel) {
		return this.replace(this.prepare(super.config.getString("Messages.Respawn")), "level", newLevel);
	}

	@Override
	public String getMessageWinner(Player winner) {
		return this.replace(this.prepare(super.config.getString("Messages.Winner")), "winner", winner.getName());
	}
	
	private String prepare(String str) {
		if (str == null) {
			return null;
		}
		
		return this.getPrefix() + ChatColor.translateAlternateColorCodes('&', str);
	}
	
	private String replace(String str, String variable, Object value) {
		if (str == null) {
			return str;
		}
		
		return str.replace("${" + variable + "}", String.valueOf(value));
	}
	
	private Location readSpawnLocation(String csv) {
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
		return new Location(world, x, y, z, yaw, pitch);
	}

	
	// -------------------------------- //
	// ---------- Validation ---------- //
	// -------------------------------- //
	
	
	@Override
	public void validate() throws MalformedConfigException {
		{
			String scoreboardFormat = super.config.getString("Scoreboard.Format");
			if (!scoreboardFormat.contains("${player}")) {
				throw new MalformedConfigException("Invalid scoreboard entry format!");
			}
		}
		
		{
			int lastInsertId = super.config.getInt("LastSpawnID");
			for (String entry : super.config.getStringList("Locations.SpawnList")) {
				if (NumberConversions.toInt(DataUtil.getFromCSV(entry, 0)) > lastInsertId) {
					throw new MalformedConfigException("LastSpawnID and Locations.SpawnList out of sync!");
				}
			}
		}
		
		{
			if (super.config.getString("Messages.Prefix").contains("§")) {
				throw new MalformedConfigException("The character '§' is not allowed in prefix!");
			}
		}
		
		{
			if (super.config.getString("Scoreboard.Title").length() >= 64) {
				throw new MalformedConfigException("The length of 'Scoreboard.Title' must be shorter than 64 characters!");
			}
		}
		
		{
			int startLevel = super.config.getInt("StartLevel");
			int levelCount = Main.instance().getFileLevels().getLevelCount();
			
			if (startLevel < 1) {
				throw new MalformedConfigException("The startlevel has to be greater than 0!");
			}
			
			if (startLevel > levelCount) {
				throw new MalformedConfigException("The startlevel has to be smaler than the levelcount (currently: " + levelCount + ", levelcount: " + levelCount + ")!");
			}
		}
	}
}