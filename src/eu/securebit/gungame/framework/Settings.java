package eu.securebit.gungame.framework;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.securebit.gungame.util.Level;

public interface Settings {
	
	public abstract SettingsLobby lobby();
	
	public abstract SettingsMessages messages();
	
	public abstract boolean isScoreboardEnabled();
	
	public abstract boolean isLevelReset();
	
	public abstract boolean isDowngradeOnNaturalDeath();
	
	public abstract boolean isAutoRespawn();
	
	public abstract int getMinPlayerCount();
	
	public abstract int getMaxPlayerCount();
	
	public abstract String getUUID();
	
	public abstract String getScoreboardTitle();
	
	public abstract String getScoreboardFormat();
	
	public abstract Level getStartLevel();
	
	public abstract Map<Integer, Location> getSpawnPoints();
	
	public abstract Map<Integer, Level> getLevels();
	
	
	public static interface SettingsLobby {
		
		public abstract int getLobbyCountdownLength();
		
		public abstract Location getLobbyLocation();
		
		public abstract String getJoinMessage();
		
		public abstract String getQuitMessage();
		
		public abstract String getCountdownMessage(int secondsLeft);
		
	}
	
	public static interface SettingsMessages {
		
		public abstract String getCountdownGrace(int secondsLeft);
		
		public abstract String getCountdownEnd(int secondsLeft);
		
		public abstract String getMapTeleport();
		
		public abstract String getGracePeriodStarts();
		
		public abstract String getGracePeriodEnds();
		
		public abstract String getServerQuit();
		
		public abstract String getWinner(Player player);
		
		public abstract String getKillBroadcast(Player victim, Player killer);
		
		public abstract String getDeathBroadcast(Player victim);
		
		public abstract String getRespawn(int level);
		
	}
	
}
