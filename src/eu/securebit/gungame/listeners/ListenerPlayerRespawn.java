package eu.securebit.gungame.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.util.Vector;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.game.GunGamePlayer;
import eu.securebit.gungame.util.Util;

public class ListenerPlayerRespawn implements Listener {
	
	private GunGame gungame;
	
	public ListenerPlayerRespawn(GunGame gungame) {
		this.gungame = gungame;
	}
	
	@EventHandler
	public void onRespawnIngame(PlayerRespawnEvent event) {
		GunGamePlayer player = this.gungame.getPlayer(event.getPlayer());
		
		List<Location> spawns = Util.getSpawns(this.gungame);
		event.setRespawnLocation(spawns.get(Main.random().nextInt(spawns.size())));
		
		Bukkit.getScheduler().runTaskLater(Main.instance(), () -> {
			player.refreshLevel();
			player.getHandle().setVelocity(new Vector(0, 0, 0));
			
			String message = this.gungame.getSettings().messages().getRespawn(player.getLevel());
			
			if (message != null) {
				player.getHandle().sendMessage(message);
			}
		}, 1L);
	}
	
}
