package eu.securebit.gungame.io;

import org.bukkit.inventory.ItemStack;

import eu.securebit.gungame.exception.InvalidLevelException;
import eu.securebit.gungame.io.impl.CraftFileLevels;
import eu.securebit.gungame.util.FileValidatable;

public interface FileLevels extends FileValidatable {
	
	public static FileLevels loadFile(String path, String name) {
		return new CraftFileLevels(path, name);
	}
	
	public abstract void setLevel(int level, ItemStack[] items) throws InvalidLevelException;
	
	public abstract boolean delete() throws InvalidLevelException;
	
	public abstract int getLevelCount();
	
	public abstract ItemStack[] getLevel(int level) throws InvalidLevelException;

}
