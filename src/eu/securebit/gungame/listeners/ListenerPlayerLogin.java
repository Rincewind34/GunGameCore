package eu.securebit.gungame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import eu.securebit.gungame.Permissions;

public class ListenerPlayerLogin implements Listener {
	
	@EventHandler
	public void onLoginIngame(PlayerLoginEvent event) { // TODO GameSTateArena
		if (!event.getPlayer().hasPermission(Permissions.joinIngame())) {
			event.disallow(Result.KICK_OTHER, "The game is already running!"); //TODO Message
		}
	}
	
}
