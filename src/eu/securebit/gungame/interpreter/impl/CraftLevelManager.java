package eu.securebit.gungame.interpreter.impl;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import eu.securebit.gungame.exception.GunGameLevelException;
import eu.securebit.gungame.interpreter.LevelManager;
import eu.securebit.gungame.io.configs.FileLevels;

public class CraftLevelManager extends AbstractInterpreter<FileLevels> implements LevelManager {
	
	public CraftLevelManager(FileLevels file) {
		super(file, LevelManager.ERROR_MAIN, LevelManager.ERROR_INTERPRET);
		
		if (this.wasSuccessful()) {
			if (this.getLevelCount() < 1) {
				this.getErrorHandler().throwError(this.createError(LevelManager.ERROR_LEVELCOUNT));
			}
		}
	}
	
	@Override
	public void saveLevel(Player host, int levelId) {
		if (levelId <= 0) {
			throw new GunGameLevelException("The level must be an integer greater than 0.");
		}
		
		if (levelId > this.getLevelCount() + 1) {
			throw new GunGameLevelException("Fragmentations are not allowed. Please select an level between 1 and " + (this.getLevelCount() + 1));
		}
		
		ItemStack[] content = new ItemStack[40];
		PlayerInventory inv = host.getInventory();
		
		int counter = 0;
		
		for (int i = 0; i < inv.getContents().length; i++) {
			content[counter++] = inv.getContents()[i];
		}
		
		for (int i = 0; i < inv.getArmorContents().length; i++) {
			content[counter++] = inv.getArmorContents()[i];
		}
		
		List<ItemStack[]> levels = super.config.getLevels();
		
		if (levelId < levels.size()) {
			levels.set(levelId, content);
		} else {
			levels.add(content);
		}
		
		super.config.setLevels(levels);
	}
	
	@Override
	public void equipPlayer(Player player, int levelId) {
		ItemStack[] items = super.config.getLevels().get(levelId);
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[] { null, null, null, null });
		player.getInventory().setContents(Arrays.copyOfRange(items, 0, 36));
		player.getInventory().setArmorContents(Arrays.copyOfRange(items, 36, 40));
		player.updateInventory();
	}
	
	@Override
	public void deleteHighestLevel() {
		if (this.getLevelCount() < 1) {
			throw new GunGameLevelException("There is no level to remove.");
		}
		
		List<ItemStack[]> levels = super.config.getLevels();
		levels.remove(levels.size() - 1);
		
		super.config.setLevels(levels);
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
		return super.config.getLevels().size();
	}
	
}
