package eu.securebit.gungame.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import eu.securebit.gungame.game.GunGame;

public class ListenerPlayerChangeExp implements Listener {

	private GunGame gungame;
	
	public ListenerPlayerChangeExp(GunGame gungame) {
		this.gungame = gungame;
	}
	
	@EventHandler
	public void onExpChange(PlayerExpChangeEvent event) {
		Player player = event.getPlayer();
		if (this.gungame.getPlayer(player) != null) {
			event.setAmount(0);
		}
	}
}
