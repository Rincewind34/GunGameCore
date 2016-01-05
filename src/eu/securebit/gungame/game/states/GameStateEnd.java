package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateEnd;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;

public class GameStateEnd extends DefaultGameStateEnd {
	
	public GameStateEnd() {
		super(Main.instance().getGame(), Main.instance().getFileConfig().getLocationLobby(), 20);
		
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, null);
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, Main.instance().getFileConfig().getMessageQuit());
	}

	@Override
	public void start() {
		Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *End*");
		
		super.start();
		
		if (Main.instance().getGame().getWinner() == null) {
			int bestLevel = 0;
			Player bestPlayer = null;
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (Main.instance().getGame().getPlayer(player).getLevel() > bestLevel) {
					bestLevel = Main.instance().getGame().getPlayer(player).getLevel();
					bestPlayer = player;
				}
			}
			
			Main.instance().getGame().initWinner(bestPlayer);
		}
		
		Main.broadcast(Main.instance().getFileConfig().getMessageWinner(Main.instance().getGame().getWinner()));
	}

	@Override
	public void stop() {
		super.stop();
		
		Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *End*");
	}
	
	@Override
	public void unload() {
		super.unload();
		
		if (Main.DEBUG) {
			Bukkit.reload();
		} else {
			Bukkit.shutdown();
		}
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Winner: " + Main.instance().getGame().getWinner());
		layout.line("Seconds left: " + this.getCountdown().getSecondsLeft());
	}
	
	@Override
	public void updateScoreboard(GamePlayer player) {
		// TODO
	}

	@Override
	public String getName() {
		return "end";
	}

	@Override
	protected String getMessageNotJoinable() {
		return Messages.serverNotJoinable();
	}

	@Override
	protected String getMessageCountdown(int secondsLeft) {
		return Main.instance().getFileConfig().getMessageCountdownEnd(secondsLeft);
	}
	
	@EventHandler
	private void onRespawn(PlayerRespawnEvent event) {
		event.setRespawnLocation(Main.instance().getFileConfig().getLocationLobby());
	}
	
}
