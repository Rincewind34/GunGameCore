package eu.securebit.gungame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import eu.securebit.gungame.game.CraftGunGame;

public class ListenerEntityDeath implements Listener {
	
	private CraftGunGame gungame;
	
	public ListenerEntityDeath(CraftGunGame gungame) {
		this.gungame = gungame;
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		if (event.getEntity().getKiller() != null) {
			this.gungame.getPlayers().forEach((player) -> {
				if (player.getHandle().equals(event.getEntity().getKiller())) {
					event.getDrops().clear();
					event.setDroppedExp(0);
					return;
				}
			});
		}
	}
	
}
