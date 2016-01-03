package eu.securebit.gungame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayerQuitGeneral implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage("");
	}
	
}
