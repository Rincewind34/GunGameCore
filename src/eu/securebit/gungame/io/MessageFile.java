package eu.securebit.gungame.io;

import org.bukkit.entity.Player;

public interface MessageFile {

	public abstract String getPrefix();
	
	public abstract String getMessageLobbyCountdown(int seconds);
	
	public abstract String getMessageGraceStart();
	
	public abstract String getMessageGraceCountdown(int seconds);
	
	public abstract String getMessageGraceEnd();
	
	public abstract String getMessageCountdownEnd(int seconds);
	
	public abstract String getMessageMapTeleport();
	
	public abstract String getMessageKillBroadcast(Player killer, Player victim);
	
	public abstract String getMessageNaturalDeath(Player victim);
	
	public abstract String getMessageJoin(Player player);
	
	public abstract String getMessageQuit(Player player);
	
	public abstract String getMessageRespawn(int newLevel);
	
	public abstract String getMessageWinner(Player winner);
}
