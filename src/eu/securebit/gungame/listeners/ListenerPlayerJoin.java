package eu.securebit.gungame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.securebit.gungame.Main;

public class ListenerPlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage("");
		event.getPlayer().teleport(Main.instance().getFileConfig().getLocationLobby());
	}
	
}
