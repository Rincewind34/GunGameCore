package lib.securebit.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameStateManager {
	
	private List<GameState> states;
	private GameState disabledState;
	
	private Game game;
	
	private int index;
	private int backup;
	
	private boolean created;
	
	public GameStateManager() {
		this.states = new ArrayList<GameState>();
	}
	
	public List<GameState> getStates() {
		return Collections.unmodifiableList(this.states);
	}
	
	public void initGame(Game game) {
		if (this.isCreated()) {
			throw new GameStateException("The manager was already created!");
		}
		
		this.game = game;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public GameState getCurrent() {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		if (this.isRunning()) {
			return this.states.get(this.index);
		} else {
			return this.disabledState;
		}
	}
	
	public GameState getDisabledState() {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		return this.disabledState;
	}
	
	public int getIndex() {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		return this.index;
	}
	
	public void create() {
		if (!this.isCreated()) {
			if (this.states.size() < 1) {
				throw new GameStateException("The manager has to contain at least one gamestate!");
			}
			
			if (this.disabledState == null) {
				throw new GameStateException("The manager has to have a disabled-state!");
			}
			
			if (this.game == null) {
				throw new GameStateException("The manager has to have a game-instance");
			}
			
			this.index = -1;
			this.backup = 0;
			this.created = true;
			this.disabledState.getEnterListener().run();
		} else {
			throw new GameStateException("The manager was already created!");
		}
	}
	
	public void addState(GameState state) {
		if (this.isCreated()) {
			throw new GameStateException("The manager was already created!");
		} else {
			this.states.add(state);
		}
	}
	
	public void next() {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		if (this.isRunning()) {
			if (this.hasNext()) {
				this.getCurrent().getLeaveListener().run();
				this.index = this.index + 1;
				this.getCurrent().getEnterListener().run();
			} else {
				this.setRunning(false);
			}
		}
	}
	
	public void back() {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		if (this.isRunning()) {
			if (this.index != 0) {
				this.getCurrent().getLeaveListener().run();
				this.index = this.index - 1;
				this.getCurrent().getEnterListener().run();
			} else {
				this.setRunning(false);
			}
		}
	}
	
	public void setRunning(boolean running, boolean restart) {
		if (!this.isCreated()) {
			return;
		}
		
		if (!running && this.isRunning()) {
			this.backup = this.index;
			this.getCurrent().getLeaveListener().run();
			this.index = -1;
			
			if (this.hasDisabledGameState()) {
				this.getCurrent().getEnterListener().run();
			}
		} else if (running && !this.isRunning()) {
			if (this.hasDisabledGameState()) {
				this.getCurrent().getLeaveListener().run();
			}
				
			if (restart) {
				this.index = 0;
			} else {
				this.index = this.backup;
			}
			
			this.getCurrent().getEnterListener().run();
		}
	}
	
	public void setRunning(boolean running) {
		this.setRunning(running, true);
	}
	
	public void setDisabledState(GameState disabledState) {
		if (this.isCreated()) {
			throw new GameStateException("The manager was already created!");
		}
		
		this.disabledState = disabledState;
	}
	
	public boolean isRunning() {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		return this.index != -1;
	}
	
	public boolean isCreated() {
		return this.created;
	}
	
	public boolean hasDisabledGameState() {
		return this.disabledState != null;
	}
	
	public boolean hasNext() {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		return this.states.size() > this.index + 1;
	}
	
	
	@SuppressWarnings("serial")
	public static class GameStateException extends RuntimeException {
		
		public GameStateException(String msg) {
			super(msg);
		}
		
	}
	
	public static interface Game {
		
		public abstract boolean isReady();
		
	}
	
}
