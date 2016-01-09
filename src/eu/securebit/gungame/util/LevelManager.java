package eu.securebit.gungame.util;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import eu.securebit.gungame.game.GunGame;

public class LevelManager {
	
	public static void save(GunGame gungame, int id, Player player) {
		ItemStack[] content = new ItemStack[40];
		PlayerInventory inv = player.getInventory();
		
		int counter = id;
		
		for (int i = 0; i < inv.getContents().length; i++) {
			content[counter++] = inv.getContents()[i];
		}
		
		for (int i = 0; i < inv.getArmorContents().length; i++) {
			content[counter++] = inv.getArmorContents()[i];
		}
	}
	
	public static void equip(GunGame gungame, int id, Player player) {
		ItemStack[] items = gungame.getSettings().files().getLevels().getLevel(id);
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[] { null, null, null, null });
		player.getInventory().setContents(Arrays.copyOfRange(items, 0, 36));
		player.getInventory().setArmorContents(Arrays.copyOfRange(items, 36, 40));
		player.updateInventory();
	}
	
	public static boolean deleteLevel(GunGame gungame) {
		return gungame.getSettings().files().getLevels().delete();
	}
	
	public static boolean isInvalid(GunGame gungame, int id) {
		return id < 1 || id > LevelManager.getCount(gungame);
	}
	
	public static int getCount(GunGame gungame) {
		return gungame.getSettings().files().getLevels().getLevelCount();
	}
	
	public static int getStartLevel(GunGame gungame) {
		return gungame.getSettings().options().getStartLevel();
	}
	
}
