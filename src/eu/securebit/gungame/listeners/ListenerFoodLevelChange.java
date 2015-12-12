package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.event.entity.FoodLevelChangeEvent;

public class ListenerFoodLevelChange extends DefaultListener {
	
	public ListenerFoodLevelChange() {
		super(ListenerFoodLevelChange.class, FoodLevelChangeEvent.getHandlerList());
	}

	@ListenerBundle(name = { "bundle.lobby", "bundle.grace", "bundle.ingame", "bundle.end" })
	private static void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
	
}
