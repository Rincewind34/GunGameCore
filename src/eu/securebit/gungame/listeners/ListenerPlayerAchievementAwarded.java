package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.event.player.PlayerAchievementAwardedEvent;

public class ListenerPlayerAchievementAwarded extends DefaultListener {

	public ListenerPlayerAchievementAwarded() {
		super(ListenerPlayerAchievementAwarded.class, PlayerAchievementAwardedEvent.getHandlerList());
	}
	
	@ListenerBundle(name = "bundle.all")
	private static void onAwarded(PlayerAchievementAwardedEvent event) {
		event.setCancelled(true);
	}

}
