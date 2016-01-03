package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.defaults.DefaultGameStateLobby;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.Permissions;
import eu.securebit.gungame.Util;

public class GameStateEnd extends DefaultGameStateLobby {
	
	public GameStateEnd() {
		super(Main.instance().getGame(),
				Main.instance().getFileConfig().getLocationLobby(),
				Permissions.premium(), Permissions.teammember(),
				Main.instance().getFileConfig().getMaxPlayers(),
				Main.instance().getFileConfig().getMinPlayers(),
				20,
				false);
	}

	@Override
	public void start() {
		Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *End*");
		
		if (Main.instance().getGame().getWinner() == null) {
			int bestLevel = 0;
			Player bestPlayer = null;
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (Main.instance().getGame().getCurrentLevel(player) > bestLevel) {
					bestLevel = Main.instance().getGame().getCurrentLevel(player);
					bestPlayer = player;
				}
			}
			
			Main.instance().getGame().initWinner(bestPlayer);
		}
		
		Main.broadcast(Main.instance().getFileConfig().getMessageWinner(Main.instance().getGame().getWinner()));
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			Main.instance().getGame().resetPlayer(player);
			player.teleport(Main.instance().getFileConfig().getLocationLobby());
		}
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
	protected String getKickMessage(int levelKicked) {
		return null;
	}

	@Override
	protected String getMessageServerFull() {
		return null;
	}

	@Override
	protected String getMessageNotJoinable() {
		return Messages.serverNotJoinable();
	}

	@Override
	protected String getMessageCountdown(int secondsLeft) {
		return Main.instance().getFileConfig().getMessageCountdownEnd(secondsLeft);
	}
	
	@Override
	protected void onQuit(Player player) {
		super.onQuit(player);
		Util.startCalculation(player, 2);
	}
	
	@EventHandler
	private void onRespawn(PlayerRespawnEvent event) {
		event.setRespawnLocation(Main.instance().getFileConfig().getLocationLobby());
	}
	
}
