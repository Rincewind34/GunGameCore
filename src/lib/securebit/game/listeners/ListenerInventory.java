package lib.securebit.game.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ListenerInventory implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		event.setCancelled(true);
	}
	
}
