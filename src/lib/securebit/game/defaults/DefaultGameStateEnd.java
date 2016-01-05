package lib.securebit.game.defaults;

import lib.securebit.countdown.Countdown;
import lib.securebit.countdown.DefaultCountdown;
import lib.securebit.countdown.TimeListener;
import lib.securebit.game.Game;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.impl.CraftGameStateLobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import eu.securebit.gungame.Main;

public abstract class DefaultGameStateEnd extends CraftGameStateLobby {
	
	private Countdown countdown;
	
	public DefaultGameStateEnd(Game<? extends GamePlayer> game, Location lobby, int countdownLength) {
		super(game, lobby);
		
		this.countdown = new DefaultCountdown(this.getGame().getPlugin(), countdownLength) {
			
			@Override
			public void onAnnounceTime(int secondsLeft) {
				String msg = DefaultGameStateEnd.this.getMessageCountdown(secondsLeft);
				
				if (msg != null) {
					Bukkit.broadcastMessage(msg);
				}
			}
			
			@Override
			public void onStop(int secondsSkipped) {
				DefaultGameStateEnd.this.onCountdownStop();
			}
			
			@Override
			public boolean isAnnounceTime(int secondsLeft) {
				return DefaultGameStateEnd.this.isCountdownAnnounceTime(secondsLeft);
			}
		};
	}
	
	@Override
	public void load() {
		super.load();
		
		for (GamePlayer player : this.getGame().getPlayers()) {
			Main.instance().getGame().resetPlayer(player.getHandle());
			player.getHandle().teleport(this.getLobby());
		}
	}
	
	@Override
	public void start() {
		this.countdown.start();
	}

	@Override
	public void stop() {
		if (this.countdown.isRunning()) {
			this.countdown.stop(false);
		}
	}
	
	public Countdown getCountdown() {
		return this.countdown;
	}
	
	protected boolean isCountdownAnnounceTime(int secondsLeft) {
		return TimeListener.defaultAnnounceTime(secondsLeft);
	}
	
	protected void onCountdownStop() {
		this.getGame().getManager().setRunning(false);
	}
	
	protected abstract String getMessageNotJoinable();
	
	protected abstract String getMessageCountdown(int secondsLeft);

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		this.getGame().getPlayers().forEach((gameplayer) -> {
			event.disallow(Result.KICK_OTHER, this.getMessageNotJoinable());
			return;
		});
	}
	
}
