package eu.securebit.gungame;

import java.util.List;

import org.bukkit.Location;

public interface Settings {
	
	public abstract SettingsLobby lobby();
	
	public abstract int getMinPlayerCount();
	
	public abstract int getMaxPlayerCount();
	
	public abstract List<Location> getSpawnPoints();
	
	public static interface SettingsLobby {
		
		public abstract int getLobbyCountdownLength();
		
		public abstract Location getLobbyLocation();
		
		public abstract String getJoinMessage();
		
		public abstract String getQuitMessage();
		
		public abstract String getCountdownMessage(int secondsLeft);
		
	}
	
}
