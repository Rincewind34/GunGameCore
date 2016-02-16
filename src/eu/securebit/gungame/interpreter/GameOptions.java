package eu.securebit.gungame.interpreter;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.exception.GunGameErrorPresentException;
import eu.securebit.gungame.exception.GunGameFixException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.interpreter.impl.CraftGameOptions;
import eu.securebit.gungame.io.configs.FileOptions;

public interface GameOptions extends Interpreter {
	
	public static final String ERROR_MAIN = 				"Error-7520-VAR0";
	
	public static final String ERROR_INTERPRET = 			"Error-7521-VAR0";
	
	public static final String ERROR_LEVELCOUNT_GREATER = 	"Error-7522-VAR0";
	
	public static final String ERROR_LEVELCOUNT_SMALLER = 	"Error-7523-VAR0";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("An error occured while interpreting the options-file 'VAR0'!");
	}
	
	public static LayoutError createErrorInterpret() {
		return new LayoutError("Could not interpret the options-file 'VAR0'!", GameOptions.ERROR_MAIN);
	}
	
	public static LayoutError createErrorLevelCountGreater() {
		return new LayoutErrorFixable("The startlevel in the options-file 'VAR0' has to be greater than 0!", GameOptions.ERROR_MAIN, (variables) -> {
			if (variables.length == 1) {
				FileOptions options = Core.getRootDirectory().getOptionsFile(variables[0]);
				
				if (options.isReady()) {
					options.setStartLevel(1);
				} else {
					throw GunGameErrorPresentException.create();
				}
			} else {
				throw GunGameFixException.variables();
			}
		}, false, "This fix will set the startlevel value in 'VAR0' to 1.");
	}
	
	public static LayoutError createErrorLevelCountSmaller() {
		return new LayoutErrorFixable("The startlevel in the options-file 'VAR0' has to be smaller than levelcount!", GameOptions.ERROR_MAIN, (variables) -> {
			if (variables.length == 1) {
				FileOptions options = Core.getRootDirectory().getOptionsFile(variables[0]);
				
				if (options.isReady()) {
					options.setStartLevel(1);
				} else {
					throw GunGameErrorPresentException.create();
				}
			} else {
				throw GunGameFixException.variables();
			}
		}, false, "This fix will set the startlevel value in 'VAR0' to 1.");
	}
	
	public static GameOptions create(FileOptions file, int levelcount) {
		return new CraftGameOptions(file, levelcount);
	}
	
	public static GameOptions create(FileOptions file, ThrownError failCause) {
		return new CraftGameOptions(file, failCause);
	}
	
	
	public abstract void autoRespawn(boolean value);
	
	public abstract void careNaturalDeath(boolean value);
	
	public abstract void levelReset(boolean value);
	
	public abstract void setStartLevel(int level);
	
	public abstract boolean autoRespawn();
	
	public abstract boolean careNaturalDeath();
	
	public abstract boolean levelReset();
	
	public abstract boolean premiumKick();
	
	public abstract int getStartLevel();
	
	public abstract int getPremiumSlots();
	
	public abstract int getCountdownLength();
	
}
