package eu.securebit.gungame.interpreter;

import org.bukkit.entity.Player;

import eu.securebit.gungame.interpreter.impl.CraftMessanger;
import eu.securebit.gungame.io.configs.FileMessages;

public interface Messanger extends Interpreter {
	
	public static Messanger create(FileMessages file) {
		return new CraftMessanger(file);
	}
	
	public abstract String getJoinLobby();
	
	public abstract String getQuitLobby();
	
	public abstract String getCountdownLobby(int secondsLeft);
	
	public abstract String getCountdownGrace(int secondsLeft);
	
	public abstract String getCountdownEnd(int secondsLeft);
	
	public abstract String getCountdownLobbyCancle(int currentPlayers, int minimalPlayers);
	
	public abstract String getMapTeleport();
	
	public abstract String getGracePeriodStarts();
	
	public abstract String getGracePeriodEnds();
	
	public abstract String getServerQuit();
	
	public abstract String getWinner(Player geter);
	
	public abstract String getKillBroadcast(Player victim, Player killer);
	
	public abstract String getDeathBroadcast(Player victim);
	
	public abstract String getRespawn(int level);
	
}
