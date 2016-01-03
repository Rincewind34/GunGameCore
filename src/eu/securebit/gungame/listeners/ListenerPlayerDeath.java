package eu.securebit.gungame.listeners;

import lib.securebit.game.StateTarget;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import eu.securebit.gungame.GunGame;
import eu.securebit.gungame.GunGameScoreboard;
import eu.securebit.gungame.Main;

@StateTarget(states = { "ingame" })
public class ListenerPlayerDeath implements Listener {
	
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		GunGame game = Main.instance().getGame();
		
		event.setDeathMessage("");

		if (player.getKiller() != null) {
			this.downgrade(player, game);

			Player killer = player.getKiller();
			
			boolean win = game.addLevel(killer);
			
			if (win) {
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
			game.resetLevel(player);
		} else {
			game.removeLevel(player);
		}
		
		if (Main.instance().getFileConfig().isScoreboard()) {
			GunGameScoreboard.update(player);
		}
	}
	
}
