package eu.securebit.gungame.interpreter;

import org.bukkit.entity.Player;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.interpreter.impl.CraftLevelManager;
import eu.securebit.gungame.io.configs.FileLevels;

public interface LevelManager extends Interpreter {
	
	public static final String ERROR_MAIN = 				"Error-7320-VAR0";
	
	public static final String ERROR_INTERPRET = 			"Error-7321-VAR0";
	
	public static final String ERROR_LEVELCOUNT = 			"Error-7322-VAR0";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("An error occured while interpreting the levels-file 'VAR0'!");
	}
	
	public static LayoutError createErrorInterpret() {
		return new LayoutError("Could not interpret the levels-file 'VAR0'!", LevelManager.ERROR_MAIN);
	}
	
	public static LevelManager create(FileLevels file) {
		return new CraftLevelManager(file);
	}
	
	public static LayoutError createErrorLevelCount() {
		return new LayoutError("The levelcount in the levelsfile 'VAR0' has to be greater than 0!", LevelManager.ERROR_MAIN);
	}
	
	
	public abstract void saveLevel(Player host, int levelId);

	public abstract void equipPlayer(Player player, int levelId);

	public abstract void deleteHighestLevel();

	public abstract boolean exists(int levelId);

	public abstract int getLevelCount();
	
}