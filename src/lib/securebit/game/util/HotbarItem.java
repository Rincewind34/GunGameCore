package lib.securebit.game.util;

import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HotbarItem {
	
	private int slot;
	
	private ItemStack item;
	
	private Consumer<Player> action;
	
	public HotbarItem(int slot, ItemStack item, Consumer<Player> action) {
		this.slot = slot;
		this.item = item;
		this.action = action;
	}
	
	public int getSlot() {
		return this.slot;
	}
	
	public ItemStack getHandle() {
		return this.item;
	}
	
	public Consumer<Player> getAction() {
		return this.action;
	}
	
}
