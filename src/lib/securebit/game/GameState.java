package lib.securebit.game;

import java.util.ArrayList;
import java.util.List;

import lib.securebit.InfoLayout;

import org.bukkit.event.Listener;

public abstract class GameState implements Listener {
	
	private List<Listener> listeners = new ArrayList<>();
	
	public final List<Listener> getListeners() {
		return this.listeners;
	}
	
	public abstract void onEnter();
	
	public abstract void onLeave();
	
	public void stageInformation(InfoLayout layout) {
		
	}
	
}
