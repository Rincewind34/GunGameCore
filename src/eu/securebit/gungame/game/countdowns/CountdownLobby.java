package eu.securebit.gungame.game.countdowns;

import lib.securebit.game.AbstractCountdown;
import lib.securebit.game.listener.CountdownListener;
import lib.securebit.game.listener.TickListener;
import lib.securebit.game.listener.TimeListener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;

public class CountdownLobby extends AbstractCountdown implements CountdownListener, TimeListener, TickListener {

//	private final TitleTimings titleTimings;
	
	public CountdownLobby() {
		super(Main.instance(), 120);
		
		this.addCountdownListener(this);
		this.addTimeListener(this);
		this.addTickListener(this);
		
//		this.titleTimings = new TitleTimings(0, 0, 18);
	}

	@Override
	public void onStart(int secondsLeft) {
		
	}

	@Override
	public void onStop(int skippedSeconds) {
		if (skippedSeconds == 0) {
			if (Bukkit.getOnlinePlayers().size() < Main.instance().getFileConfig().getMinPlayers()) {
				// TODO message missing
			} else {
				Main.instance().getGameStateManager().next();
			}
		}
	}

	@Override
	public void onRestart(int startSeconds) {
		
	}

	@Override
	public void onInterrupt(int secondsLeft) {
		
	}

	@Override
	public boolean isAnnounceTime(int secondsLeft) {
		return TimeListener.defaultAnnounceTime(secondsLeft);
	}
	
	@Override
	public void onTickSecond(int secondsLeft) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.setLevel(secondsLeft);
//			if (secondsLeft <= 5) {
//				new Title().setTitle("§e" + Integer.toString(secondsLeft)).setTimings(this.titleTimings).send(player);
//			}
		}
	}

	@Override
	public void onAnnounceTime(int secoundsLeft) {		
		Main.broadcast(Main.instance().getFileConfig().getMessageLobbyCountdown(secoundsLeft));
	}

}
