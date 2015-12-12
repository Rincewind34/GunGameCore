package eu.securebit.gungame.io;

import lib.securebit.io.FileManager;

import org.bukkit.entity.Player;

import eu.securebit.gungame.exception.InvalidLevelException;

public interface LevelConfig extends FileManager, FileValidatable {

	public abstract void setItems(Player p, int level) throws InvalidLevelException;
	
	public abstract void give(Player p, int level) throws InvalidLevelException;
	
	public abstract boolean delete() throws InvalidLevelException;
	
	public abstract int getLevelCount();
}
