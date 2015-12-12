package eu.securebit.gungame.game.countdowns;

import lib.securebit.game.AbstractCountdown;
import lib.securebit.game.listener.CountdownListener;
import lib.securebit.game.listener.TimeListener;
import eu.securebit.gungame.Main;

public class CountdownGrace extends AbstractCountdown implements CountdownListener, TimeListener {

	public CountdownGrace() {
		super(Main.instance(), 15);
		
		this.addCountdownListener(this);
		this.addTimeListener(this);
	}

	@Override
	public void onStart(int secoundsLeft) {
		
	}

	@Override
	public void onStop(int skippedSeconds) {
		if (skippedSeconds == 0) {
			Main.instance().getGameStateManager().next();
		}
	}

	@Override
	public void onRestart(int startSecounds) {
		
	}

	@Override
	public void onInterrupt(int secondsLeft) {
		
	}

	@Override
	public boolean isAnnounceTime(int secondsLeft) {
		return TimeListener.defaultAnnounceTime(secondsLeft);
	}

	@Override
	public void onAnnounceTime(int secondsLeft) {
		Main.broadcast(Main.instance().getFileConfig().getMessageGraceCountdown(secondsLeft));
	}

}
