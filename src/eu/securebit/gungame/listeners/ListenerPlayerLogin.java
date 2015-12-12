package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Permissions;

public class ListenerPlayerLogin extends DefaultListener {
	
	public ListenerPlayerLogin() {
		super(ListenerPlayerLogin.class, PlayerLoginEvent.getHandlerList());
	}

	@ListenerBundle(name = { "bundle.lobby" })
	private static void onLogin(PlayerLoginEvent event) {
		if (Main.instance().getFileConfig().getMaxPlayers() <= Bukkit.getOnlinePlayers().size()) {
			event.disallow(Result.KICK_FULL, "The server is full!");
		} else {
			event.allow();
		}
	}
	
	@ListenerBundle(name = { "bundle.grace", "bundle.ingame", "bundle.end" })
	private static void onLoginIngame(PlayerLoginEvent event) {
		if (!event.getPlayer().hasPermission(Permissions.joinIngame())) {
			event.disallow(Result.KICK_OTHER, "The game is already running!");
		}
	}
	
	@ListenerBundle(name = { "bundle.edit" })
	private static void onLoginEdit(PlayerLoginEvent event) {
		if (!event.getPlayer().hasPermission(Permissions.joinIngame())) {
			event.disallow(Result.KICK_OTHER, "The server is currently down for maintenance!");
		}
	}
	
}
