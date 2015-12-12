package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.event.player.PlayerDropItemEvent;

public class ListenerPlayerDropItem extends DefaultListener {

	public ListenerPlayerDropItem() {
		super(ListenerPlayerDropItem.class, PlayerDropItemEvent.getHandlerList());
	}
	
	@ListenerBundle(name = { "bundle.lobby", "bundle.grace", "bundle.ingame", "bundle.end" })
	private static void onDropItem(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}
	
}
