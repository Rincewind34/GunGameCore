package eu.securebit.gungame.io.configs;

import org.bukkit.entity.Player;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;

public interface FileMessages extends FileGunGameConfig {
	
	public static final String ERROR_MAIN = 			"Error-7100-VAR0";
	
	public static final String ERROR_LOAD = 			"Error-7110-VAR0";
	
	public static final String ERROR_FOLDER =			"Error-7111-VAR0";
	
	public static final String ERROR_CREATE = 			"Error-7112-VAR0";
	
	public static final String ERROR_MALFORMED = 		"Error-7113-VAR0";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("In the messagefile 'VAR0' occured an error!");
	}
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The messagefile 'VAR0' could not be loaded!", FileMessages.ERROR_MAIN);
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The messagefile 'VAR0' is a directory!", FileMessages.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The messagefile 'VAR0' could not be created!", FileMessages.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformed() {
		return new LayoutErrorFixable("The messagefile 'VAR0' is malformed!", FileMessages.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	
	public abstract String getPrefix();
	
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
	
	public abstract String getWinner(Player player);
	
	public abstract String getKillBroadcast(Player victim, Player killer);
	
	public abstract String getDeathBroadcast(Player victim);
	
	public abstract String getRespawn(int level);
	
	public abstract String getMotDLobbyJoin();
	
	public abstract String getMotDLobbyPremium();
	
	public abstract String getMotDLobbyStaff();
	
	public abstract String getMotDLobbySpawns();
	
	public abstract String getMotDLobbyIngame();
	
	public abstract String getMotDLobbyEnd();
	
}
