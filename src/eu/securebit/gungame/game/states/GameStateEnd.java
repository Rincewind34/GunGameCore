package eu.securebit.gungame.game.states;

import lib.securebit.game.AbstractCountdown;
import lib.securebit.game.Countdown;
import lib.securebit.game.GameState;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.countdowns.CountdownEnd;

public class GameStateEnd implements GameState {
	
	private Countdown countdown;
	
	public GameStateEnd() {
		this.countdown = new CountdownEnd();
	}
	
	@Override
	public Runnable getEnterListener() {
		return () -> {
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
				Main.resetPlayer(player);
				player.teleport(Main.instance().getFileConfig().getLocationLobby());
			}
			
			this.countdown.start();
			
			Main.instance().getListenerHandler().register("bundle.end");
		};
	}

	@Override
	public Runnable getLeaveListener() {
		return () -> {
			Main.instance().getListenerHandler().unregister("bundle.end");
			
			if (((AbstractCountdown) this.countdown).getSecondsLeft() != 0) {
				this.countdown.stop();
			}
			
			Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *End*");
			
			if (Main.DEBUG) {
				Bukkit.reload();
			} else {
				Bukkit.shutdown();
			}
		};
	}
	
	public int getSecondsLeft() {
		return ((AbstractCountdown) this.countdown).getSecondsLeft();
	}
	
}
