package eu.securebit.gungame.game.states;

import java.util.List;

import lib.securebit.game.AbstractCountdown;
import lib.securebit.game.Countdown;
import lib.securebit.game.GameState;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.securebit.gungame.GunGameScoreboard;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.countdowns.CountdownGrace;

public class GameStateGrace implements GameState {
	
	private Countdown countdown;
	
	public GameStateGrace() {
		this.countdown = new CountdownGrace();
	}
	
	@Override
	public Runnable getEnterListener() {
		return () -> {
			Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *Grace*");
			
			this.countdown.start();
			
			List<Location> spawns = Main.instance().getFileConfig().getSpawns();
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.teleport(spawns.get(Main.random().nextInt(spawns.size())));
				Main.instance().getGame().insertPlayer(player);
			}
			
			if (Main.instance().getFileConfig().isScoreboard()) {
				GunGameScoreboard.setup();
			}
			
			Main.broadcast(Main.instance().getFileConfig().getMessageMapTeleport());
			Main.broadcast(Main.instance().getFileConfig().getMessageGraceStart());
			
			Main.instance().getListenerHandler().register("bundle.grace");
			
			Main.instance().getGame().calculateGameState();
		};
	}

	@Override
	public Runnable getLeaveListener() {
		return () -> {
			Main.instance().getListenerHandler().unregister("bundle.grace");
			
			if (((AbstractCountdown) this.countdown).getSecondsLeft() != 0) {
				this.countdown.stop();
			}
			
			Bukkit.broadcastMessage(Main.instance().getFileConfig().getMessageGraceEnd());
			
			Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *Grace*");
		};
	}
	
	public int getSecondsLeft() {
		return ((AbstractCountdown) this.countdown).getSecondsLeft();
	}
	
}
