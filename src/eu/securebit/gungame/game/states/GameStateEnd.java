package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.AbstractCountdown;
import lib.securebit.game.Countdown;
import lib.securebit.game.GameState;
import lib.securebit.game.defaults.DefaultCountdown;
import lib.securebit.game.listeners.ListenerBlocks;
import lib.securebit.game.listeners.ListenerDamage;
import lib.securebit.game.listeners.ListenerPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import eu.securebit.gungame.Main;

public class GameStateEnd extends GameState {
	
	private Countdown countdown;
	
	public GameStateEnd() {
		this.countdown = new DefaultCountdown(Main.instance(), 20) {
			
			@Override
			public void onAnnounceTime(int secondsLeft) {
				Main.broadcast(Main.instance().getFileConfig().getMessageCountdownEnd(secondsLeft));
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
				entity.teleport(Main.instance().getFileConfig().getLocationLobby());
			}
		});
	}
	
	@Override
	public void onEnter() {
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
		
		this.countdown.start();
	}

	@Override
	public void onLeave() {
		Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *End*");
		
		if (((AbstractCountdown) this.countdown).getSecondsLeft() != 0) {
			this.countdown.stop();
		}
		
		if (Main.DEBUG) {
			Bukkit.reload();
		} else {
			Bukkit.shutdown();
		}
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Winner: " + Main.instance().getGame().getWinner());
		layout.line("Seconds left: " + ((AbstractCountdown) this.countdown).getSecondsLeft());
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		event.setRespawnLocation(Main.instance().getFileConfig().getLocationLobby());
	}
	
}
