package lib.securebit.game.defaults;

import lib.securebit.game.AbstractCountdown;
import lib.securebit.game.Countdown;
import lib.securebit.game.GameState;
import lib.securebit.game.GameStateManager.Game;
import lib.securebit.game.listeners.ListenerBlocks;
import lib.securebit.game.listeners.ListenerDamage;
import lib.securebit.game.listeners.ListenerPlayer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import eu.securebit.gungame.Main;

public abstract class DefaultLobbyGameState extends GameState {
	
	protected Countdown countdown;
	
	private int minPlayer;
	private int maxPlayer;
	
	private Location lobby;
	
	private Game game;
	
	public DefaultLobbyGameState(Plugin plugin, int seconds, int minPlayer, int maxPlayer, Location lobby, Game game) {
		this.minPlayer = minPlayer;
		this.maxPlayer = maxPlayer;
		
		this.lobby = lobby;
		this.game = game;
		
		this.countdown = new DefaultCountdown(plugin, seconds) {
			
			@Override
			public void onAnnounceTime(int secondsLeft) {
				Bukkit.broadcastMessage(DefaultLobbyGameState.this.getCountdownMessage(this.getSecondsLeft()));
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
		for (Player player : Bukkit.getOnlinePlayers()) {
			this.game.resetPlayer(player);
			player.teleport(this.lobby);
		}
		
		this.handleJoin();
	}

	@Override
	public void onLeave() {
		if (this.countdown.isRunning()) {
			if (((AbstractCountdown) this.countdown).getSecondsLeft() != 0) {
				this.countdown.stop();
			}
		}
	}
	
	public final void handleJoin() {
		if (this.minPlayer <= Bukkit.getOnlinePlayers().size()) {
			if (!this.countdown.isRunning()) {
				this.countdown.restart();
			}
		}
	}
	
	protected abstract String getCountdownMessage(int secondsLeft);
	
	protected abstract String getQuitMessage(Player player);
	
	protected abstract String getJoinMessage(Player player);
	
	protected void onJoin(PlayerJoinEvent event) {}
	
	protected void onQuit(PlayerQuitEvent event) {}
	
	protected void clearPlayer(Player player) {
		player.setGameMode(GameMode.SURVIVAL);
		player.setHealth(20.0);
		player.setVelocity(new Vector(0, 0, 0));
		player.setFoodLevel(20);
		player.setExp(0.0F);
		player.setLevel(0);
		player.setFireTicks(0);
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[] { null, null, null, null });
		
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}
	
	@EventHandler
	public final void onLoginListener(PlayerLoginEvent event) {
		if (this.maxPlayer <= Bukkit.getOnlinePlayers().size()) {
			event.disallow(Result.KICK_FULL, "The server is full!");
		} else {
			event.allow();
		}
	}
	
	@EventHandler
	public final void onJoinListener(PlayerJoinEvent event) {
		event.setJoinMessage("");
		Bukkit.broadcastMessage(this.getJoinMessage(event.getPlayer()));
		
		event.getPlayer().teleport(this.lobby);
		
		this.clearPlayer(event.getPlayer());
		this.handleJoin();
		
		this.onJoin(event);
	}
	
	@EventHandler
	public final void onQuitListener(PlayerQuitEvent event) {
		event.setQuitMessage("");
		Bukkit.broadcastMessage(this.getQuitMessage(event.getPlayer()));
		
		this.onQuit(event);
	}
	
}
