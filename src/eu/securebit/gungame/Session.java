package eu.securebit.gungame;

import java.io.File;
import java.util.List;

import eu.securebit.gungame.game.GameInterface;
import eu.securebit.gungame.game.GunGame;

public interface Session {
	
	public abstract void boot();
	
	public abstract void reboot();
	
	public abstract void createNewGameInstance(File file, GameInterface gameInterface);
	
	public abstract void reloadGame(GunGame gungame);
	
	public abstract void restartGame(GunGame gungame);
	
	public abstract void shutdownGame(GunGame gungame);
	
	public abstract boolean isFrameLoaded();
	
	public abstract boolean isFrameEnabled();
	
	public abstract List<GunGame> getGames();
	
}
