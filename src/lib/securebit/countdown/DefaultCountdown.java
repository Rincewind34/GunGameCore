package lib.securebit.countdown;

import org.bukkit.plugin.Plugin;

public abstract class DefaultCountdown extends AbstractCountdown implements CountdownListener, TimeListener {

	public DefaultCountdown(Plugin plugin, int seconds) {
		super(plugin, seconds);
		
		this.addCountdownListener(this);
		this.addTimeListener(this);
	}

	@Override
	public void onStart(int secoundsLeft) {
		
	}

	@Override
	public void onStop(int skippedSeconds) {
		
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

}
