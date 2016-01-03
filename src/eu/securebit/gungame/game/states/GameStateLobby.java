package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.AbstractCountdown;
import lib.securebit.game.defaults.DefaultLobbyGameState;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Util;

public class GameStateLobby extends DefaultLobbyGameState {
	
	public GameStateLobby() {
		super(Main.instance(), 120,
				Main.instance().getFileConfig().getMinPlayers(),
				Main.instance().getFileConfig().getMaxPlayers(),
				Main.instance().getFileConfig().getLocationLobby(),
				Main.instance().getGame());
	}

	@Override
	public void onEnter() {
		Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *Lobby*");
		
		super.onEnter();
	}

	@Override
	public void onLeave() {
		Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *Lobby*");
		
		super.onLeave();
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Enough players: " + Util.parseBoolean(Bukkit.getOnlinePlayers().size() >= Main.instance().getFileConfig().getMinPlayers(), layout));
		layout.line("Seconds left: " + ((AbstractCountdown) super.countdown).getSecondsLeft());
	}
	
	@Override
	protected String getCountdownMessage(int secondsLeft) {
		return Main.instance().getFileConfig().getMessageLobbyCountdown(secondsLeft);
	}

	@Override
	protected String getQuitMessage(Player player) {
		return Main.instance().getFileConfig().getMessageQuit(player);
	}

	@Override
	protected String getJoinMessage(Player player) {
		return Main.instance().getFileConfig().getMessageJoin(player);
	}
	
	@Override
	protected void onJoin(PlayerJoinEvent event) {
		Main.instance().getGame().resetPlayer(event.getPlayer());
		Main.broadcast(Main.instance().getFileConfig().getMessageJoin(event.getPlayer()));
	}
	
}
