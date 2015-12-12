package eu.securebit.gungame.game.countdowns;

import lib.securebit.game.AbstractCountdown;
import lib.securebit.game.listener.CountdownListener;
import lib.securebit.game.listener.TimeListener;
import eu.securebit.gungame.Main;

public class CountdownEnd extends AbstractCountdown implements CountdownListener, TimeListener {

	public CountdownEnd() {
		super(Main.instance(), 20);
		
		this.addCountdownListener(this);
		this.addTimeListener(this);
	}

	@Override
	public void onStart(int secondsLeft) {
		
	}

	@Override
	public void onStop(int skippedSeconds) {
		if (skippedSeconds == 0) {
			Main.instance().getGameStateManager().next();
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
	public void onAnnounceTime(int secondsLeft) {
		Main.broadcast(Main.instance().getFileConfig().getMessageCountdownEnd(secondsLeft));
	}

}
