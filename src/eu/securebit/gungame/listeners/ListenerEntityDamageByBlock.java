package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

public class ListenerEntityDamageByBlock extends DefaultListener {
	
	public ListenerEntityDamageByBlock() {
		super(ListenerEntityDamageByBlock.class, EntityDamageByBlockEvent.getHandlerList());
	}

	@ListenerBundle(name = { "bundle.lobby", "bundle.grace", "bundle.end" })
	private static void onDamage(EntityDamageByBlockEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
		}
	}
	
}