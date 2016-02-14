package eu.securebit.gungame.game;

import java.util.List;

import org.bukkit.Location;

import eu.securebit.gungame.interpreter.GameOptions;
import eu.securebit.gungame.interpreter.GunGameMap;
import eu.securebit.gungame.interpreter.GunGameScoreboard;
import eu.securebit.gungame.interpreter.LevelManager;
import eu.securebit.gungame.interpreter.Messanger;
import eu.securebit.gungame.io.configs.FileGameConfig;

public interface GunGame {
	
	public abstract void setEditMode(boolean value);
	
	public abstract void initWinner(GunGamePlayer player);
	
	public abstract void setLobbyLocation(Location lobby);
	
	public abstract void calculateGameState();
	
	public abstract boolean isEditMode();
	
	public abstract boolean isFileReady();
	
	public abstract boolean isReady();
	
	public abstract int getMinPlayerCount();
	
	public abstract String getName();
	
	public abstract Location getLobbyLocation();
	
	public abstract FileGameConfig getFileGameConfig();
	
	public abstract GunGameScoreboard getScoreboard();
	
	public abstract Messanger getMessanger();
	
	public abstract LevelManager getLevelManager();
	
	public abstract GameOptions getOptions();
	
	public abstract GunGameMap getMap();
	
	public abstract GameInterface getInterface();
	
	public abstract GunGamePlayer getWinner();
	
	public abstract List<GameCheck> getChecks();
	
}