package eu.securebit.gungame.listeners;

import lib.securebit.game.StateTarget;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.securebit.gungame.Main;

@StateTarget(states = { "grace", "ingame", "end" })
public class ListenerPlayerQuit implements Listener {
	
	public void onQuit(PlayerQuitEvent event) {
		Main.broadcast(Main.instance().getFileConfig().getMessageQuit(event.getPlayer()));
		ListenerPlayerQuit.startCalculation(event.getPlayer(), 1L);
	}
	
	private static void startCalculation(Player player, long delay) {
		Bukkit.getScheduler().runTaskLater(Main.instance(), () -> {
			if (player == null || Bukkit.getPlayerExact(player.getName()) == null) {
				Main.instance().getGame().handleDisconnect(player);
			} else {
				if (delay >= 100L) {
					if (Main.DEBUG) {
						System.err.println("Unable to handle disconnect of player " + player.getName());
					}
				}
				
				if (delay >= 20L) {
					player.kickPlayer("");
				}
				
				ListenerPlayerQuit.startCalculation(player, delay * 2);
			}
		}, delay);
	}
	
}
