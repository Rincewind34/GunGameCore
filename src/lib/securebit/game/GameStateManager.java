package lib.securebit.game;

import java.util.List;

public interface GameStateManager<G extends Game<?>> extends Iterable<GameState> {
	
	public abstract void next();
	
	public abstract void next(int count);
	
	public abstract void next(int count, boolean startStates);
	
	public abstract void skip(int count);
	
	public abstract void skipAll();
	
	public abstract void setRunning(boolean running);
	
	public abstract void add(GameState state);
	
	public abstract void remove(GameState state);
	
	public abstract void initDisabledState(GameState state);
	
	public abstract void initGame(G game);
	
	public abstract void destroy();
	
	public abstract void create();
	
	public abstract void create(boolean running);
	
	public abstract boolean isCreated();
	
	public abstract boolean isRunning();
	
	public abstract boolean hasNext();
	
	public abstract boolean isCurrent(Class<? extends GameState> stateClass);
	
	public abstract int getCurrentIndex();
	
	public abstract int getSize();
	
	public abstract GameState getDisabledState();
		
	public abstract GameState getCurrent();
	
	public abstract GameState getState(String name);
	
	public abstract G getGame();
	
	public abstract List<GameState> getAll();
	
	public abstract <T extends GameState> T getCurrent(Class<T> cls);
	
	@SuppressWarnings("serial")
	public static class GameStateException extends RuntimeException {
		
		public GameStateException(String msg) {
			super(msg);
		}
		
	}
	
}
