package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.event.player.PlayerExpChangeEvent;

public class ListenerExpChange extends DefaultListener {

	public ListenerExpChange() {
		super(ListenerExpChange.class, PlayerExpChangeEvent.getHandlerList());
	}

	@ListenerBundle(name = { "bundle.all" })
	private static void onExpChange(PlayerExpChangeEvent event) {
		event.setAmount(0);
	}
}
