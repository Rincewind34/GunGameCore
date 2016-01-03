 package eu.securebit.gungame.listeners;

import lib.securebit.game.StateTarget;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

@StateTarget(states = { "grace", "ingame" })
public class ListenerBlockIgnite implements Listener {
	
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event) {
		if (event.getCause() == IgniteCause.SPREAD) {
			event.setCancelled(true);
		}
	}
	
}
