package eu.securebit.gungame.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import eu.securebit.gungame.GunGame;
import eu.securebit.gungame.GunGameScoreboard;
import eu.securebit.gungame.Main;

public class ListenerPlayerDeath implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		GunGame game = Main.instance().getGame();
		
		event.setDeathMessage("");

		if (player.getKiller() != null) {
			this.downgrade(player, game);

			Player killer = player.getKiller();
			
			int oldLevel = game.getPlayer(killer).getLevel();
			int newLevel = game.getPlayer(killer).incrementLevel();
			
			if (oldLevel == newLevel) {
				Main.instance().getGame().initWinner(killer);
				Main.instance().getGameStateManager().next();
			} else {
				Main.broadcast(Main.instance().getFileConfig().getMessageKillBroadcast(killer, player));
				
				if (Main.instance().getFileConfig().isScoreboard()) {
					GunGameScoreboard.update(killer);
				}
			}
		} else {
			Main.broadcast(Main.instance().getFileConfig().getMessageNaturalDeath(player));
			
			if (Main.instance().getFileConfig().isLevelDowngradeOnNaturalDeath()) {
				this.downgrade(player, game);
			}
		}

		event.getDrops().clear();
		event.setDroppedExp(0);
		
		if (Main.instance().getFileConfig().isAutoRespawn()) {
			player.spigot().respawn();
		}
	}
	
	private void downgrade(Player player, GunGame game) {
		if (Main.instance().getFileConfig().isLevelResetAfterDeath()) {
			game.getPlayer(player).resetLevel();
		} else {
			game.getPlayer(player).decrementLevel();
		}
		
		if (Main.instance().getFileConfig().isScoreboard()) {
			GunGameScoreboard.update(player);
		}
	}
	
}
