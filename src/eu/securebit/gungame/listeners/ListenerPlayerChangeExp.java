package eu.securebit.gungame.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import eu.securebit.gungame.game.CraftGunGame;

public class ListenerPlayerChangeExp implements Listener {

	private CraftGunGame gungame;
	
	public ListenerPlayerChangeExp(CraftGunGame gungame) {
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
