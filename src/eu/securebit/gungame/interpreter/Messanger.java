package eu.securebit.gungame.interpreter;

import org.bukkit.entity.Player;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.interpreter.impl.CraftMessanger;
import eu.securebit.gungame.io.configs.FileMessages;

public interface Messanger extends Interpreter {
	
	public static final String ERROR_MAIN = 				"Error-7220-VAR0";
	
	public static final String ERROR_INTERPRET = 				"Error-7221-VAR0";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("An error occured while interpreting the messages-file 'VAR0'!");
	}
	
	public static LayoutError createErrorInterpret() {
		return new LayoutError("Could not interpret the messages-file 'VAR0'!", Messanger.ERROR_MAIN);
	}
	
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
