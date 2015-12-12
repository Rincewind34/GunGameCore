package eu.securebit.gungame.game.states;

import lib.securebit.game.AbstractCountdown;
import lib.securebit.game.Countdown;
import lib.securebit.game.GameState;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.countdowns.CountdownLobby;

public class GameStateLobby implements GameState {
	
	private Countdown countdown;
	
	public GameStateLobby() {
		this.countdown = new CountdownLobby();
	}
	
	@Override
	public Runnable getEnterListener() {
		return () -> {
			Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *Lobby*");
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				Main.resetPlayer(player);
				player.teleport(Main.instance().getFileConfig().getLocationLobby());
			}
			
			this.handleJoin();
			
			Main.instance().getListenerHandler().register("bundle.lobby");
		};
	}

	@Override
	public Runnable getLeaveListener() {
		return () -> {
			Main.instance().getListenerHandler().unregister("bundle.lobby");
			
			if (this.countdown.isRunning()) {
				if (this.getSecondsLeft() != 0) {
					this.countdown.stop();
				}
			}
			
			Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *Lobby*");
		};
	}
	
	public void handleJoin() {
		if (Main.instance().getFileConfig().getMinPlayers() <= Bukkit.getOnlinePlayers().size()) {
			if (!this.countdown.isRunning()) {
				this.countdown.restart();
			}
		}
	}
	
	public int getSecondsLeft() {
		return ((AbstractCountdown) this.countdown).getSecondsLeft();
	}

}
