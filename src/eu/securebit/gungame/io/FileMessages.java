package eu.securebit.gungame.io;

import org.bukkit.entity.Player;

import eu.securebit.gungame.io.impl.CraftFileMessages;
import eu.securebit.gungame.io.util.FileValidatable;

public interface FileMessages extends FileValidatable {
	
	public static FileMessages loadFile(String path, String name) {
		return new CraftFileMessages(path, name);
	}
	
	public abstract String getPrefix();
	
	public abstract String getJoinLobby();
	
	public abstract String getQuitLobby();
	
	public abstract String getCountdownLobby(int secondsLeft);
	
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
	
	public abstract String getMotD(String gamestate);
	
}
