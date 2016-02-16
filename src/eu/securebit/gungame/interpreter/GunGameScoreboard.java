package eu.securebit.gungame.interpreter;

import org.bukkit.entity.Player;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.exception.GunGameFixException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.interpreter.impl.CraftGunGameScoreboard;
import eu.securebit.gungame.io.configs.FileScoreboard;

public interface GunGameScoreboard extends Interpreter {
	
	public static final String ERROR_MAIN = 				"Error-7420-VAR0";
	
	public static final String ERROR_INTERPRET = 			"Error-7421-VAR0";
	
	public static final String ERROR_TITLE = 				"Error-7422-VAR0";
	
	public static final String ERROR_FORMAT = 				"Error-7423-VAR0";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("An error occured while interpreting the scoreboard-file 'VAR0'!");
	}
	
	public static LayoutError createErrorInterpret() {
		return new LayoutError("Could not interpret the scoreboard-file 'VAR0'!", GunGameScoreboard.ERROR_MAIN);
	}
	
	public static LayoutError createErrorTitle() {
		return new LayoutErrorFixable("The title given by the scoreboardfile 'VAR0' has to be shorter than 64 characters!", GunGameScoreboard.ERROR_MAIN, (variables) -> {
			if (variables.length == 1) {
				FileScoreboard scoreboard = Core.getRootDirectory().getScoreboardFile(variables[0]);
				
				if (scoreboard.isReady()) {
					scoreboard.setScoreboardTitle(scoreboard.getScoreboardTitle().substring(0, 64));
				} else {
					throw GunGameErrorPresentException.create();
				}
			} else {
				throw GunGameFixException.variables();
			}
		}, false, "This fix will trim the title to 64 chars in the map-file 'VAR0'.");
	}
	
	public static LayoutError createErrorFormat() {
		return new LayoutError("The format given by the scoreboardfile 'VAR0' has to contain '§{player}'!", GunGameScoreboard.ERROR_MAIN);
	}
	
	public static GunGameScoreboard create(FileScoreboard file, GunGame gungame) {
		return new CraftGunGameScoreboard(gungame, file);
	}
	
	public abstract void setup();

	public abstract void update(Player player);

	public abstract void updateAll();

	public abstract void delete();

	public abstract void create();

	public abstract void clearFromPlayers();

	public abstract void refresh();

	public abstract boolean exists();

	public abstract boolean isEnabled();
	
}