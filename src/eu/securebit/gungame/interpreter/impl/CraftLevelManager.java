package eu.securebit.gungame.interpreter.impl;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import eu.securebit.gungame.interpreter.LevelManager;
import eu.securebit.gungame.io.configs.FileLevels;

public class CraftLevelManager extends AbstractInterpreter<FileLevels> implements LevelManager {
	
	public CraftLevelManager(FileLevels file) {
		super(file);
	}
	
	@Override
	public void saveLevel(Player host, int levelId) {
		ItemStack[] content = new ItemStack[40];
		PlayerInventory inv = host.getInventory();
		
		int counter = 0;
		
		for (int i = 0; i < inv.getContents().length; i++) {
			content[counter++] = inv.getContents()[i];
		}
		
		for (int i = 0; i < inv.getArmorContents().length; i++) {
			content[counter++] = inv.getArmorContents()[i];
		}
		
		super.config.setLevel(levelId, content);
	}
	
	@Override
	public void equipPlayer(Player player, int levelId) {
		ItemStack[] items = super.config.getLevel(levelId);
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[] { null, null, null, null });
		player.getInventory().setContents(Arrays.copyOfRange(items, 0, 36));
		player.getInventory().setArmorContents(Arrays.copyOfRange(items, 36, 40));
		player.updateInventory();
	}
	
	@Override
	public boolean deleteHighestLevel() {
		return super.config.delete();
	}
	
	@Override
	public boolean exists(int levelId) {
		if (levelId < 1) {
			return false;
		}
		
		if (levelId > this.getLevelCount()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int getLevelCount() {
		return super.config.getLevelCount();
	}
	
	public FileLevels getFile() {
		return super.config;
	}
	
}
