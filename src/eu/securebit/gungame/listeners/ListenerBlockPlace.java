package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;

public class ListenerBlockPlace extends DefaultListener {

	public ListenerBlockPlace() {
		super(ListenerBlockPlace.class, BlockPlaceEvent.getHandlerList());
	}
	
	@ListenerBundle(name = { "bundle.lobby", "bundle.grace", "bundle.ingame", "bundle.end" })
	private static void onPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType() == Material.WEB ||
				event.getBlock().getType() == Material.FIRE) {
			return;
		}
		
		event.setCancelled(true);
	}
	
}
