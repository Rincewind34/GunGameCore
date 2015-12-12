package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class ListenerGameModeChange extends DefaultListener {

	public ListenerGameModeChange() {
		super(ListenerGameModeChange.class, PlayerGameModeChangeEvent.getHandlerList());
	}
	
	@ListenerBundle(name = { "bundle.edit" })
	private static void onChange(PlayerGameModeChangeEvent event) {
		System.out.println(event.getNewGameMode());
	}

}
