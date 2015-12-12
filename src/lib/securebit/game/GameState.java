package lib.securebit.game;

public interface GameState {
	
	public abstract Runnable getEnterListener();
	
	public abstract Runnable getLeaveListener();
	
}
