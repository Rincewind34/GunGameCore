package eu.securebit.gungame.framework;

import lib.securebit.InfoLayout;

import org.bukkit.Location;

import eu.securebit.gungame.game.GameOption;

public interface ActionInterface {
	
	public abstract void stageEditInformation(InfoLayout layout);
	
	public abstract void removeSpawn(int id);
	
	public abstract void setLobbyLocation(Location lobby);
	
	public abstract void setEditMode(boolean value);
	
	public abstract void setGameOption(GameOption option, boolean value);
	
	public abstract int addSpawn(Location loc);
	
}
