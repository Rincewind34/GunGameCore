package eu.securebit.gungame.framework;

import java.util.Map;

import org.bukkit.Location;

import eu.securebit.gungame.io.FileLevels;
import eu.securebit.gungame.io.FileMessages;
import eu.securebit.gungame.io.FileScoreboard;

public interface Settings {
	
	public abstract SettingsLocations locations();
	
	public abstract SettingsOptions options();
	
	public abstract SettingsFiles files();
	
	public abstract String getUUID();
	
	
	public static interface SettingsLocations {
		
		public abstract Location getLobbyLocation();
		
		public abstract Map<Integer, Location> getSpawnPoints();
		
	}
	
	public static interface SettingsOptions {
		
		public abstract boolean isLevelReset();
		
		public abstract boolean isDowngradeOnNaturalDeath();
		
		public abstract boolean isAutoRespawn();
		
		public abstract int getMinPlayerCount();
		
		public abstract int getMaxPlayerCount();
		
		public abstract int getLobbyCountdownLength();
		
		public abstract int getStartLevel();
		
	}
	
	public static interface SettingsFiles {
		
		public abstract FileMessages getMessages();
		
		public abstract FileScoreboard getScoreboard();
		
		public abstract FileLevels getLevels();
		
	}
	
}