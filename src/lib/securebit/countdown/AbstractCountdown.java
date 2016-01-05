package lib.securebit.countdown;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class AbstractCountdown implements Countdown {

	private BukkitTask task;
	private Plugin plugin;
	
	private int secondsLeft;
	private int startSeconds;
	
	private boolean running;
	
	private List<TimeListener> timeListeners;
	private List<TickListener> tickListeners;
	private List<CountdownListener> countdownListeners;
	
	public AbstractCountdown(Plugin plugin, int startSeconds) {
		if (plugin == null) {
			throw new NullPointerException("The plugin cannot be null!");
		}
		
		if (startSeconds <= 0) {
			throw new CountdownException("The startseconds can only be greater than null!");
		}
		
		this.startSeconds = startSeconds;
		this.timeListeners = new ArrayList<>();
		this.tickListeners = new ArrayList<>();
		this.countdownListeners = new ArrayList<>();
		
		this.startSeconds = startSeconds;
		this.secondsLeft = startSeconds;
		
		this.plugin = plugin;
	}
	
	@Override
	public void start() {
		if (this.isRunning()) {
			throw new CountdownException("The countdown is already running!");
		}
		
		this.running = true;
		
		this.countdownListeners.forEach(listener -> {
			listener.onStart(this.secondsLeft);
		});
		
		this.task = Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
			if (this.secondsLeft == 0) {
				this.stop();
				return;
			}
			
			this.tickListeners.forEach(listener -> {
				listener.onTickSecond(this.secondsLeft);
			});
			
			this.timeListeners.forEach((listener) -> {
				if (listener.isAnnounceTime(this.secondsLeft)) {
					listener.onAnnounceTime(this.secondsLeft);
				}
			});
			
			this.secondsLeft = this.secondsLeft - 1;
		}, 0L, 20L);
	}

	@Override
	public void restart() {
		if (this.isRunning()) {
			this.stop();
		}
		
		this.countdownListeners.forEach(listener -> {
			listener.onRestart(this.secondsLeft);
		});
		
		this.secondsLeft = this.startSeconds;
		this.start();
	}
	
	@Override
	public void stop() {
		this.stop(true);
	}
	
	@Override
	public void stop(boolean force) {
		if (!this.isRunning()) {
			throw new CountdownException("The countdown is not running!");
		}
		
		this.running = false;
		
		if (force) {
			this.countdownListeners.forEach(listener -> {
				listener.onStop(this.secondsLeft);
			});
		}
		
		this.task.cancel();
		this.task = null;
		this.secondsLeft = 0;
	}

	@Override
	public void interrupt() {
		this.countdownListeners.forEach(listener -> {
			listener.onInterrupt(this.secondsLeft);
		});
		
		this.task.cancel();
		this.task = null;
		this.running = false;
	}

	@Override
	public void addTimeListener(TimeListener listener) {
		this.timeListeners.add(listener);
	}
	
	@Override
	public void addTickListener(TickListener listener) {
		this.tickListeners.add(listener);
	}

	@Override
	public void addCountdownListener(CountdownListener listener) {
		this.countdownListeners.add(listener);
	}
	
	@Override
	public void setStartSeconds(int startSecounds) {
		this.startSeconds = startSecounds;
	}
	
	@Override
	public int getStartSeconds() {
		return this.startSeconds;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}
	
	@Override
	public int getSecondsLeft() {
		return this.secondsLeft;
	}
	
	@SuppressWarnings("serial")
	public static class CountdownException extends RuntimeException {
		
		public CountdownException(String msg) {
			super(msg);
		}
		
	}

}
