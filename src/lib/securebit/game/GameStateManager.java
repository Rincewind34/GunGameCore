package lib.securebit.game;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public interface GameStateManager extends Iterable<GameState> {
	
	public abstract <T extends GameState> T getCurrent(Class<T> cls);
	
	public abstract List<GameState> getStates();
	
	public abstract GameState getState(String name);
	
	public abstract GameState getCurrent();
	
	public abstract GameState getDisabledState();
	
	public abstract Game getGame();
	
	public abstract String getCurrentName();
	
	public abstract boolean isRunning();
	
	public abstract boolean isCreated();
	
	public abstract boolean hasNext();
	
	public abstract void addGameState(GameState state);
	
	public abstract void initDisabledState(GameState state);
	
	public abstract void initGame(Game game);
	
	public abstract void setRunning(boolean running);
	
	public abstract void next();
	
	public abstract void next(int indexTo);
	
	public abstract void back();
	
	public abstract void back(int indexTo);
	
	public abstract void skip(int count);
	
	public abstract void skipAll();
	
	public abstract void create();
	
	public abstract void create(boolean running);
	
	public abstract void addListener(Listener listener);
	
	public abstract int getCurrentIndex();
	
	
	@SuppressWarnings("serial")
	public static class GameStateException extends RuntimeException {
		
		public GameStateException(String msg) {
			super(msg);
		}
		
	}
	
	public static interface Game {
		
		public abstract boolean isReady();
		
		public abstract void resetPlayer(Player player);
		
	}
	
}
