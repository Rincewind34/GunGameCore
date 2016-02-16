package eu.securebit.gungame;

import java.util.List;

import lib.securebit.InfoLayout;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.game.GunGame;

public interface Session {
	
	public abstract void boot();
	
	public abstract void boot(Output output);
	
	public abstract void reboot();
	
	public abstract void reboot(Output output);
	
	public abstract void shutdown();
	
	public abstract void shutdown(Output output);
	
	public abstract void shutdownGame(String name);
	
	public abstract void stageInformation(InfoLayout layout);
	
	public abstract void handleException(Throwable throwable);
	
	public abstract void handleException(Throwable throwable, Output output);
	
	public abstract void setDebugMode(boolean debugMode);
	
	public abstract boolean isFrameLoaded();
	
	public abstract boolean isFrameEnabled();
	
	public abstract boolean isDebugMode();
	
	public abstract boolean containsGame(String name);
	
	public abstract Frame getFrame();
	
	public abstract GunGame getGame(String name);
	
	public abstract GunGame restartGame(String name);
	
	public abstract GunGame createNewGameInstance(String name, GameBuilder gameBuilder);
	
	public abstract List<GunGame> getGames();
	
}
