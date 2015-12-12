package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.securebit.gungame.Main;

public class ListenerPlayerQuit extends DefaultListener {
	
	public ListenerPlayerQuit() {
		super(ListenerPlayerQuit.class, PlayerQuitEvent.getHandlerList());
	}
	
	@ListenerBundle(name = { "bundle.all" })
	private static void onQuitGeneral(PlayerQuitEvent event) {
		event.setQuitMessage("");
	}
	
	@ListenerBundle(name = { "bundle.ingame" })
	private static void onQuitIngame(PlayerQuitEvent event) {
		Main.instance().getGame().handleDisconnect(event.getPlayer());
	}
	
	@ListenerBundle(name = { "bundle.grace", "bundle.ingame", "bundle.end" })
	private static void onQuitAfterLobby(PlayerQuitEvent event) {
		ListenerPlayerQuit.startCalculation(event.getPlayer(), 1L);
	}
	
	@ListenerBundle(name = { "bundle.lobby", "bundle.grace", "bundle.ingame", "bundle.end" })
	private static void onQuit(PlayerQuitEvent event) {
		Main.broadcast(Main.instance().getFileConfig().getMessageQuit(event.getPlayer()));
	}
	
	@ListenerBundle(name = { "bundle.edit" })
	private static void onQuitEdit(PlayerQuitEvent event) {
		Main.layout().broadcast("*" + event.getPlayer().getName() + "* left the server!");
	}
	
	
	private static void startCalculation(Player player, long delay) {
		Bukkit.getScheduler().runTaskLater(Main.instance(), () -> {
			if (player == null || Bukkit.getPlayerExact(player.getName()) == null) {
				Main.instance().getGame().calculateGameState();
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
