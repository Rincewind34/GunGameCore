package lib.securebit.game.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ListenerBlocks implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		event.setCancelled(true);
	}
	
}
