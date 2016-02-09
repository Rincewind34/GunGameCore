package eu.securebit.gungame.interpreter;

import org.bukkit.entity.Player;

import eu.securebit.gungame.interpreter.impl.CraftLevelManager;
import eu.securebit.gungame.io.configs.FileLevels;

public interface LevelManager extends Interpreter {
	
	public static LevelManager create(FileLevels file) {
		return new CraftLevelManager(file);
	}
	
	
	public abstract void saveLevel(Player host, int levelId);

	public abstract void equipPlayer(Player player, int levelId);

	public abstract boolean deleteHighestLevel();

	public abstract boolean exists(int levelId);

	public abstract int getLevelCount();
	
}