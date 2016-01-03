package eu.securebit.gungame.listeners;

import java.util.List;

import lib.securebit.game.StateTarget;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.util.Vector;

import eu.securebit.gungame.Main;

@StateTarget(states = { "ingame" })
public class ListenerPlayerRespawn implements Listener {
	
	public void onRespawnIngame(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		
		List<Location> spawns = Main.instance().getFileConfig().getSpawns();
		event.setRespawnLocation(spawns.get(Main.random().nextInt(spawns.size())));
		
		Bukkit.getScheduler().runTaskLater(Main.instance(), () -> {
			Main.instance().getGame().refresh(event.getPlayer());
			player.setVelocity(new Vector(0, 0, 0));
			
			String message = Main.instance().getFileConfig().getMessageRespawn(Main.instance().getGame().getCurrentLevel(player));
			
			if (message != null) {
				player.sendMessage(message);
			}
		}, 1L);
	}
	
}
