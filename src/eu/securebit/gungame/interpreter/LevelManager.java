package eu.securebit.gungame.interpreter;

import org.bukkit.entity.Player;

public interface LevelManager {

	public abstract void createNewLevel(Player host, int levelId);

	public abstract void equipPlayer(Player player, int levelId);

	public abstract boolean deleteHighestLevel();

	public abstract boolean exists(int levelId);

	public abstract int getLevelCount();

}