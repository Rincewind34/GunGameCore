package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.event.block.BlockBreakEvent;

public class ListenerBlockBreak extends DefaultListener {

	public ListenerBlockBreak() {
		super(ListenerBlockBreak.class, BlockBreakEvent.getHandlerList());
	}
	
	@ListenerBundle(name = { "bundle.lobby", "bundle.grace", "bundle.ingame", "bundle.end" })
	private static void onBreak(BlockBreakEvent event) {
		event.setCancelled(true);
	}
	
}
