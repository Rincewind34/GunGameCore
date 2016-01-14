package eu.securebit.gungame.io;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;

import eu.securebit.gungame.io.util.FileValidatable;

public interface FileGameConfig extends FileValidatable {
	
	public abstract boolean isEditMode();
	
	public abstract boolean isMuted();
	
	public abstract boolean isLevelResetAfterDeath();
	
	public abstract boolean isAutoRespawn();
	
	public abstract boolean isLevelDowngradeOnNaturalDeath();
	
	public abstract boolean isSpawn(int id);
	
	public abstract int getStartLevel();
	
	public abstract int getMinPlayers();
	
	public abstract int getMaxPlayers();
	
	public abstract String getFileLevelsLocation();
	
	public abstract String getFileMessagesLocation();
	
	public abstract String getFileScoreboardLocation();
	
	public abstract Location getLocationLobby();
	
	public abstract Location getSpawnById(int id);
	
	public abstract List<Location> getSpawns();
	
	public abstract Map<Integer, Location> getSpawnsMap();
	
	
	// ---------- Write Access ---------- //
	
	public abstract void setEditMode(boolean enabled);
	
	public abstract void setMuted(boolean muted);
	
	public abstract void setLevelResetAfterDeath(boolean enabled);
	
	public abstract void setAutoRespawn(boolean enabled);
	
	public abstract void setLevelDowngradeOnNaturalDeath(boolean enabled);
	
	public abstract void setStartLevel(int level);
	
	public abstract void setLocationLobby(Location loc);
	
	public abstract void resetAllSpawns();
	
	public abstract boolean removeSpawn(int id);
	
	public abstract int addSpawn(Location loc);
	
}
