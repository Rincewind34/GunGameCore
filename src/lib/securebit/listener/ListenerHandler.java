package lib.securebit.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import eu.securebit.gungame.Main;

public class ListenerHandler {

	private List<Listener> listeners;
	
	protected static final Plugin plugin = Main.instance();
	
	public ListenerHandler() {
		this.listeners = new ArrayList<>();
	}
	
	public void register(String name) {
		for (Listener listener : this.listeners) {
			if (listener instanceof DefaultListener) {
				((DefaultListener) listener).enableBundle(name);
			}
		}
	}
	
	public void unregister(String name) {
		for (Listener listener : this.listeners) {
			if (listener instanceof DefaultListener) {
				((DefaultListener) listener).disableBundle(name);
			}
		}
	}
	
	public void create() {
		for (Listener listener : this.listeners) {
			Bukkit.getPluginManager().registerEvents(listener, ListenerHandler.plugin);
		}
	}
	
	public void destroy() {
		for (Listener listener : this.listeners) {
			HandlerList.unregisterAll(listener);
		}
	}
	
	public void add(Listener listener) {
		this.listeners.add(listener);
	}
	
	@Override
	public String toString() {
		String result = "[";
		
		for (Listener listener : this.listeners) {
			result = result + listener.getClass().getName() + (listener instanceof DefaultListener ? ":" + ((DefaultListener) listener).getEnabledBundles().toString() : "") + ",";
		}
		
		return result.substring(0, result.length() - 1) + "]";
	}
	
}
