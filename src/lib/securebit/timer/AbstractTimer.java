package lib.securebit.timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class AbstractTimer implements Timer {
	
	private List<TimerEntry> entries;
	
	private BukkitTask task;
	
	private int period;
	
	private boolean running;
	
	public AbstractTimer(int period) {
		this.entries = new ArrayList<>();
		this.period = period;
	}
	
	@Override
	public Iterator<TimerEntry> iterator() {
		return this.entries.iterator();
	}
	
	@Override
	public int getPeriod() {
		return this.period;
	}
	
	@Override
	public void start(Plugin plugin) {
		if (this.isRunning()) {
			throw new RuntimeException("The timer is running!");
		}
		
		this.running = true;
		
		this.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
			for (TimerEntry entry : this) {
				entry.tick();
			}
		}, 0L, this.period);
	}

	@Override
	public void stop() {
		this.interrupt();
		
		for (TimerEntry entry : this) {
			entry.reset();
		}
	}

	@Override
	public void interrupt() {
		if (!this.isRunning()) {
			throw new RuntimeException("The timer is not running!");
		}
		
		this.task.cancel();
		this.task = null;
		this.running = false;
	}

	@Override
	public void addEntry(TimerEntry entry) {
		this.entries.add(entry);
	}

	@Override
	public void removeEntry(TimerEntry entry) {
		this.entries.remove(entry);
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}

	@Override
	public List<TimerEntry> getEntries() {
		return Collections.unmodifiableList(this.entries);
	}

}
