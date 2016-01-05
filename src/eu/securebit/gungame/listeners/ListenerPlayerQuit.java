package eu.securebit.gungame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.securebit.gungame.Main;

public class ListenerPlayerQuit implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage("");
		
		Main.instance().getGame().quitPlayer(event.getPlayer());
	}
	
}
