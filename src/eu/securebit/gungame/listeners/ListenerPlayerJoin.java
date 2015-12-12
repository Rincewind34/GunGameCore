package eu.securebit.gungame.listeners;

import lib.securebit.listener.DefaultListener;
import lib.securebit.listener.ListenerBundle;

import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.game.states.GameStateLobby;

public class ListenerPlayerJoin extends DefaultListener {
	
	public ListenerPlayerJoin() {
		super(ListenerPlayerJoin.class, PlayerJoinEvent.getHandlerList());
	}

	@ListenerBundle(name = { "bundle.all" })
	private static void onJoinGeneral(PlayerJoinEvent event) {
		event.setJoinMessage("");
		event.getPlayer().teleport(Main.instance().getFileConfig().getLocationLobby());
	}
	
	@ListenerBundle(name = { "bundle.lobby" })
	private static void onJoinIngame(PlayerJoinEvent event) {
		Main.resetPlayer(event.getPlayer());
		Main.broadcast(Main.instance().getFileConfig().getMessageJoin(event.getPlayer()));
		((GameStateLobby) Main.instance().getGameStateManager().getCurrent()).handleJoin();
	}
	
	@ListenerBundle(name = { "bundle.edit" })
	private static void onJoinEdit(PlayerJoinEvent event) {
		event.getPlayer().setGameMode(GameMode.CREATIVE);
		event.getPlayer().sendMessage(Messages.maintenance());
		
		Main.layout().broadcast("*" + event.getPlayer().getName() + "* joined the server!");
	}
	
}
