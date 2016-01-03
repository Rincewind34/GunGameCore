package eu.securebit.gungame.game.states;

import java.util.List;

import lib.securebit.InfoLayout;
import lib.securebit.game.AbstractCountdown;
import lib.securebit.game.Countdown;
import lib.securebit.game.GameState;
import lib.securebit.game.defaults.DefaultCountdown;
import lib.securebit.game.listeners.ListenerBlocks;
import lib.securebit.game.listeners.ListenerDamage;
import lib.securebit.game.listeners.ListenerPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import eu.securebit.gungame.GunGameScoreboard;
import eu.securebit.gungame.Main;

public class GameStateGrace extends GameState {
	
	private Countdown countdown;
	
	public GameStateGrace() {
		this.countdown = new DefaultCountdown(Main.instance(), 15) {
			
			@Override
			public void onAnnounceTime(int secondsLeft) {
				Main.broadcast(Main.instance().getFileConfig().getMessageGraceCountdown(secondsLeft));
			}
		};
		
		this.getListeners().add(new ListenerBlocks());
		this.getListeners().add(new ListenerPlayer());
		this.getListeners().add(new ListenerDamage() {
			
			@Override
			public void onInWall(EntityDamageEvent event) {
				this.teleport(event.getEntity());
			}
			
			@Override
			public void onInVoid(EntityDamageEvent event) {
				this.teleport(event.getEntity());
			}
			
			private void teleport(Entity entity) {
				List<Location> spawns = Main.instance().getFileConfig().getSpawns();
				entity.teleport(spawns.get(Main.random().nextInt(spawns.size())));
			}
		});
	}
	
	@Override
	public void onEnter() {
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
		
		Main.instance().getGame().calculateGameState();
	}

	@Override
	public void onLeave() {
		Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *Grace*");
		
		if (((AbstractCountdown) this.countdown).getSecondsLeft() != 0) {
			this.countdown.stop();
		}
		
		Bukkit.broadcastMessage(Main.instance().getFileConfig().getMessageGraceEnd());
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Seconds left: " + ((AbstractCountdown) this.countdown).getSecondsLeft());
	}
	
}
