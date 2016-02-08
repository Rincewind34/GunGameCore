package eu.securebit.gungame.io.configs;

import org.bukkit.entity.Player;

import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.io.abstracts.FileConfig;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;

public interface FileMessages extends FileIdentifyable, FileConfig {
	
	public static final String ERROR_MAIN = 			"7100-VAR0";
	
	public static final String ERROR_LOAD = 			"7110-VAR0";
	
	public static final String ERROR_FOLDER =			"7111-VAR0";
	
	public static final String ERROR_CREATE = 			"7112-VAR0";
	
	public static final String ERROR_MALFORMED = 		"7113-VAR0";
	
	public static Error createErrorMain() {
		return new SimpleError("In the messagefile 'VAR0' occured an error!");
	}
	
	public static Error createErrorLoad() {
		return new SimpleError("The messagefile 'VAR0' could not be loaded!", FileMessages.ERROR_MAIN);
	}
	
	public static Error createErrorFolder() {
		return new SimpleFixableError("The messagefile 'VAR0' is a directory!", FileMessages.ERROR_LOAD, () -> {
			// TODO fix path
		});
	}
	
	public static Error createErrorCreate() {
		return new SimpleError("The messagefile 'VAR0' could not be created!", FileMessages.ERROR_LOAD);
	}
	
	public static Error createErrorMalformed() {
		return new SimpleFixableError("The messagefile 'VAR0' is malformed!", FileMessages.ERROR_LOAD, () -> {
			// TODO fix path
		});
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
