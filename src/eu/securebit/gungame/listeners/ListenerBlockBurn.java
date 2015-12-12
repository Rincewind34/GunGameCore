package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.event.block.BlockBurnEvent;

public class ListenerBlockBurn extends DefaultListener {

	public ListenerBlockBurn() {
		super(ListenerBlockBurn.class, BlockBurnEvent.getHandlerList());
	}
	
	@ListenerBundle(name = { "bundle.lobby", "bundle.grace", "bundle.ingame", "bundle.end" })
	private static void onBurn(BlockBurnEvent event) {
		event.setCancelled(true);
	}
	
}
