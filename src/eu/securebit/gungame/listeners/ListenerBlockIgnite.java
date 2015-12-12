package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

public class ListenerBlockIgnite extends DefaultListener {
	
	public ListenerBlockIgnite() {
		super(ListenerBlockIgnite.class, BlockIgniteEvent.getHandlerList());
	}

	@ListenerBundle(name = { "bundle.grace", "bundle.ingame" })
	private static void onBlockIgnite(BlockIgniteEvent event) {
		if (event.getCause() == IgniteCause.SPREAD) {
			event.setCancelled(true);
		}
	}
	
}
