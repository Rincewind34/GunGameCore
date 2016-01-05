package eu.securebit.gungame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import eu.securebit.gungame.GunGame;
import eu.securebit.gungame.GunGamePlayer;

public class ListenerPlayerDeath implements Listener {
	
	private GunGame gungame;
	
	public ListenerPlayerDeath(GunGame gungame) {
		this.gungame = gungame;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		GunGamePlayer player = this.gungame.getPlayer(event.getEntity());
		event.setDeathMessage("");

		if (player.getHandle().getKiller() != null) {
			this.downgrade(player);

			GunGamePlayer killer = this.gungame.getPlayer(player.getHandle().getKiller());
			
			int oldLevel = killer.getLevel();
			int newLevel = killer.incrementLevel();
			
			if (oldLevel == newLevel) {
				this.gungame.initWinner(killer);
				this.gungame.getManager().next();
			} else {
				this.gungame.broadcastMessage(this.gungame.getSettings().messages().getKillBroadcast(player.getHandle(), killer.getHandle()));
				
				if (this.gungame.getScoreboard().isEnabled()) {
					this.gungame.getScoreboard().update(killer.getHandle());
				}
			}
		} else {
			this.gungame.broadcastMessage(this.gungame.getSettings().messages().getDeathBroadcast(player.getHandle()));
			
			if (this.gungame.getSettings().isDowngradeOnNaturalDeath()) {
				this.downgrade(player);
			}
		}

		event.getDrops().clear();
		event.setDroppedExp(0);
		
		if (this.gungame.getSettings().isAutoRespawn()) {
			player.getHandle().spigot().respawn();
		}
	}
	
	private void downgrade(GunGamePlayer player) {
		if (this.gungame.getSettings().isLevelReset()) {
			player.resetLevel();
		} else {
			player.decrementLevel();
		}
		
		if (this.gungame.getScoreboard().isEnabled()) {
			this.gungame.getScoreboard().update(player.getHandle());
		}
	}
	
}
