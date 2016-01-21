package lib.securebit.game;

import java.util.List;

import lib.securebit.InfoLayout;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public interface GameState extends Listener {
	
	/*
	 * GameState Lifecycle:
	 * 		-> load
	 * 			-> register listener (optional)
	 * 			-> start (optional)
	 * 			-> stop (optinal)
	 * 			-> unregister listener (optional)
	 * 		-> unload
	 * 
	 */
	
	public abstract void load();
	
	public abstract void registerListener(Plugin plugin);
	
	public abstract void start();
	
	public abstract void stop();
	
	public abstract void unregisterListener(Plugin plugin);
	
	public abstract void unload();
	
	public abstract void updateScoreboard(GamePlayer player);
	
	public abstract void stageInformation(InfoLayout layout);
	
	public abstract String getName();
	
	public abstract String getMotD();
	
	public abstract Settings getSettings();
	
	public abstract List<Listener> getListeners();
	
}
