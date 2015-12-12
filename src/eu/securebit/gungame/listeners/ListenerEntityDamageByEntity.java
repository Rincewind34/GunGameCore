package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ListenerEntityDamageByEntity extends DefaultListener {
	
	public ListenerEntityDamageByEntity() {
		super(ListenerEntityDamageByEntity.class, EntityDamageByEntityEvent.getHandlerList());
	}

	@ListenerBundle(name = { "bundle.lobby", "bundle.grace", "bundle.end" })
	private static void onDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
		}
	}
	
}