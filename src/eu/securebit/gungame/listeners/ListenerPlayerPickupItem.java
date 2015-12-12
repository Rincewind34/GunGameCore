package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.event.player.PlayerPickupItemEvent;

public class ListenerPlayerPickupItem extends DefaultListener {

	public ListenerPlayerPickupItem() {
		super(ListenerPlayerPickupItem.class, PlayerPickupItemEvent.getHandlerList());
	}
	
	@ListenerBundle(name = { "bundle.lobby", "bundle.grace", "bundle.ingame", "bundle.end" })
	private static void onBreak(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}
	
}
