package eu.securebit.gungame.io.configs;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.exception.GunGameFixException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.ioutil.IOUtil;

public interface FileMessages extends FileGunGameConfig {
	
	public static final String ERROR_LOAD = 			"Error-7210-VAR0";
	
	public static final String ERROR_FOLDER =			"Error-7211-VAR0";
	
	public static final String ERROR_CREATE = 			"Error-7212-VAR0";
	
	public static final String ERROR_MALFORMED = 		"Error-7213-VAR0";
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("The messagefile 'VAR0' could not be loaded!");
	}
	
	public static LayoutError createErrorFolder() {
		return new LayoutErrorFixable("The messagefile 'VAR0' is a directory!", FileMessages.ERROR_LOAD, (variables) -> {
			if (variables.length == 1) {
				IOUtil.delete(Core.getRootDirectory().getFile(variables[0]));
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the directory 'VAR0'.");
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The messagefile 'VAR0' could not be created!", FileMessages.ERROR_LOAD);
	}
	
	public static LayoutError createErrorMalformed() {
		return new LayoutErrorFixable("The messagefile 'VAR0' is malformed!", FileMessages.ERROR_LOAD, (variables) -> {
			if (variables.length == 1) {
				IOUtil.delete(Core.getRootDirectory().getFile(variables[0]));
			} else {
				throw GunGameFixException.variables();
			}
		}, true, "This fix will delete the file 'VAR0'.");
	}
	
	
	public abstract String getPrefix();
	
	public abstract String getJoinLobby();
	
	public abstract String getQuitLobby();
	
	public abstract String getCountdownLobby();
	
	public abstract String getCountdownGrace();
	
	public abstract String getCountdownEnd();
	
	public abstract String getCountdownLobbyCancle();
	
	public abstract String getMapTeleport();
	
	public abstract String getGracePeriodStarts();
	
	public abstract String getGracePeriodEnds();
	
	public abstract String getServerQuit();
	
	public abstract String getWinner();
	
	public abstract String getKillBroadcast();
	
	public abstract String getDeathBroadcast();
	
	public abstract String getRespawn();
	
	public abstract String getMotDLobbyJoin();
	
	public abstract String getMotDLobbyPremium();
	
	public abstract String getMotDLobbyStaff();
	
	public abstract String getMotDGrace();
	
	public abstract String getMotDIngame();
	
	public abstract String getMotDEnd();
	
	public abstract String getMotDMaintendance();
	
	public abstract String getKickGameRunning();
	
	public abstract String getKickPremium();
	
	public abstract String getKickStaff();
	
	public abstract String getKickLobbyFull();
	
	public abstract String getKickNotJoinable();
	
	public abstract String getKickMaintendance();
	
}
