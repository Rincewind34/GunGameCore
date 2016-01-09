package eu.securebit.gungame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ListenerEntityDeath implements Listener {
	
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		event.getDrops().clear();
		event.setDroppedExp(0);
	}
	
}
