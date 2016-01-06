package lib.securebit.game.defaults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lib.securebit.countdown.Countdown;
import lib.securebit.countdown.DefaultCountdown;
import lib.securebit.countdown.TimeListener;
import lib.securebit.game.Game;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.impl.CraftGameStateArena;
import lib.securebit.timer.AbstractTimer;
import lib.securebit.timer.Timer;
import lib.securebit.timer.Timer.TimerEntry;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class DefaultGameStateSpawns<G extends Game<? extends GamePlayer>> extends CraftGameStateArena<G> {
	
	private Map<Integer, Location> spawns;
	private Map<GamePlayer, Integer> players;
	
	private Countdown countdown;
	private Timer timer;
	
	private SpawnSpreading spreading;
	
	public DefaultGameStateSpawns(G game, List<Location> spawns, SpawnSpreading spreading, int countdownLength) {
		super(game);
		
		this.spawns = new HashMap<>();
		this.players = new HashMap<>();
		
		this.spreading = spreading;
		
		this.timer = new AbstractTimer(1);
		this.timer.addEntry(new TimerEntry(1, () -> {
			for (GamePlayer player : this.players.keySet()) {
				int spawnId = this.players.get(player);
				Location spawn = this.spawns.get(spawnId);
				
				if (player.getHandle().getLocation().distanceSquared(spawn) >= 0.5D) {
					player.getHandle().teleport(spawn);
				}
			}
		}));
		
		this.countdown = new DefaultCountdown(this.getGame().getPlugin(), countdownLength) {
			
			@Override
			public void onAnnounceTime(int secondsLeft) {
				String msg = DefaultGameStateSpawns.this.getMessageCountdown(secondsLeft);
				
				if (msg != null) {
					DefaultGameStateSpawns.this.getGame().broadcastMessage(msg);
				}
			}
			
			@Override
			public void onStop(int secondsSkipped) {
				DefaultGameStateSpawns.this.getGame().getManager().next();
			}
			
			@Override
			public boolean isAnnounceTime(int secondsLeft) {
				return DefaultGameStateSpawns.this.isCountdownAnnounceTime(secondsLeft);
			}
			
		};
		
		for (int i = 0; i < spawns.size(); i++) {
			this.spawns.put(i, spawns.get(i));
		}
		
		this.getSettings().setValue(StateSettings.BLOCK_BREAK, new ArrayList<>());
		this.getSettings().setValue(StateSettings.BLOCK_PLACE, new ArrayList<>());
		this.getSettings().setValue(StateSettings.PLAYER_DAMAGE_FIGHT, false);
		this.getSettings().setValue(StateSettings.PLAYER_DAMAGE_NATURAL, false);
		this.getSettings().setValue(StateSettings.PLAYER_FOODLEVEL_CHANGE, false);
	}
	
	@Override
	public void load() {
		super.load();
		
		List<GamePlayer> players = new ArrayList<>();
		
		for (GamePlayer player : this.getGame().getPlayers()) {
			if (this.intoSpreading(player)) {
				players.add(player);
			}
		}
		
		this.players = this.spreading.spreader.spread(this.spawns.size(), players);
	}
	
	@Override
	public String getName() {
		return "spawns";
	}
	
	@Override
	public void start() {
		this.timer.start(this.getGame().getPlugin());
		this.countdown.start();
	}

	@Override
	public void stop() {
		this.timer.stop();
		
		if (this.countdown.isRunning()) {
			this.countdown.stop();
		}
	}
	
	@Override
	protected void teleportPlayer(Player player) {
		player.teleport(this.spawns.get(this.players.get(this.getGame().getPlayer(player))));
	}
	
	public Countdown getCountdown() {
		return this.countdown;
	}
	
	public SpawnSpreading getSpreading() {
		return this.spreading;
	}
	
	protected boolean isCountdownAnnounceTime(int secondsLeft) {
		return TimeListener.defaultAnnounceTime(secondsLeft);
	}
	
	public abstract boolean intoSpreading(GamePlayer player);
	
	protected abstract String getMessageCountdown(int secondsleft);
	
	
	public static enum SpawnSpreading {
		
		LINE((spawnCount, players) -> {
			if (players.size() > spawnCount) {
				throw new SpreadException("There are more players to spread as spawns to match!");
			}
			
			Map<GamePlayer, Integer> result = new HashMap<>();
			
			for (int i = 0; i < players.size(); i++) {
				result.put(players.get(i), i);
			}
			
			return result;
		}),
		
		RANDOM((spawnCount, players) -> {
			Map<GamePlayer, Integer> result = new HashMap<>();
			Random random = new Random();
			
			for (GamePlayer player : players) {
				result.put(player, random.nextInt(spawnCount));
			}
			
			return result;
		});
		
		private Spreader spreader;
		
		private SpawnSpreading(Spreader spreader) {
			this.spreader = spreader;
		}
		
		public Spreader getSpreader() {
			return this.spreader;
		}
		
		
		public static interface Spreader {
			
			public abstract Map<GamePlayer, Integer> spread(int spawnCount, List<GamePlayer> players);
			
		}
		
		public static class SpreadException extends RuntimeException {

			private static final long serialVersionUID = 1L;
			
			public SpreadException(String msg) {
				super(msg);
			}
			
		}
		
	}
	
}
