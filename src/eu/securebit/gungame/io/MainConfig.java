package eu.securebit.gungame.io;

import java.util.List;

import lib.securebit.io.FileManager;

import org.bukkit.Location;

public interface MainConfig extends FileManager, MessageFile, FileValidatable {

	// ---------- Read Access ---------- //
	
	public abstract boolean isEditMode();
	
	public abstract boolean isLevelResetAfterDeath();
	
	public abstract boolean isAutoRespawn();
	
	public abstract boolean isLevelDowngradeOnNaturalDeath();
	
	public abstract boolean isScoreboard();
	
	public abstract boolean isSpawn(int id);
	
	public abstract int getStartLevel();
	
	public abstract int getMinPlayers();
	
	public abstract int getMaxPlayers();
	
	public abstract String getScoreboardTitle();
	
	public abstract String getScoreboardFormat();
	
	public abstract Location getLocationLobby();
	
	public abstract Location getSpawnById(int id);
	
	public abstract List<Location> getSpawns();
	
	
	// ---------- Write Access ---------- //
	
	public abstract void setEditMode(boolean enabled);
	
	public abstract void setLevelResetAfterDeath(boolean enabled);
	
	public abstract void setAutoRespawn(boolean enabled);
	
	public abstract void setLevelDowngradeOnNaturalDeath(boolean enabled);
	
	public abstract void setScoreboard(boolean enabled);
	
	public abstract void setStartLevel(int level);
	
	public abstract void setScoreboardTitle(String title);
	
	public abstract void setScoreboardFormat(String format);
	
	public abstract void setLocationLobby(Location loc);
	
	public abstract void resetAllSpawns();
	
	public abstract boolean removeSpawn(int id);
	
	public abstract int addSpawn(Location loc);
}
