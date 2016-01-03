package eu.securebit.gungame.listeners;

import lib.securebit.game.StateTarget;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import eu.securebit.gungame.Permissions;

@StateTarget(states = { "grace", "ingame", "end" })
public class ListenerPlayerLogin implements Listener {
	
	@EventHandler
	public void onLoginIngame(PlayerLoginEvent event) {
		if (!event.getPlayer().hasPermission(Permissions.joinIngame())) {
			event.disallow(Result.KICK_OTHER, "The game is already running!");
		}
	}
	
}
