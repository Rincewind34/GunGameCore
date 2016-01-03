package lib.securebit.timer;

import java.util.List;

import lib.securebit.timer.Timer.TimerEntry;

import org.bukkit.plugin.Plugin;

public interface Timer extends Iterable<TimerEntry> {
	
	public abstract int getPeriod();
	
	public abstract void start(Plugin plugin);
	
	public abstract void stop();
	
	public abstract void interrupt();
	
	public abstract void addEntry(TimerEntry entry);
	
	public abstract void removeEntry(TimerEntry entry);
	
	public abstract boolean isRunning();
	
	public abstract List<TimerEntry> getEntries();
	
	
	public static class TimerEntry {
		
		private int cycles;
		
		private int periods;
		
		private Runnable runnable;
		
		public TimerEntry(int periods, Runnable runnable) {
			if (periods <= 0) {
				periods = 1;
			}
			
			this.cycles = 0;
			this.periods = periods;
			this.runnable = runnable;
		}
		
		public int getPeriods() {
			return this.periods;
		}
		
		public void tick() {
			this.cycles = cycles + 1;
			
			if (this.cycles == this.periods) {
				this.reset();
				this.runnable.run();
			}
		}
		
		public void reset() {
			this.cycles = 0;
		}
		
	}
	
}
