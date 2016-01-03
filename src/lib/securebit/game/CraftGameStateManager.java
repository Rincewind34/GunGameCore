package lib.securebit.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class CraftGameStateManager implements GameStateManager {
	
	private List<GameState> states;
	private GameState disabledState;
	
	private Game game;
	
	private Plugin plugin;
	
	private int index;
	
	private boolean created;
	
	public CraftGameStateManager(Plugin plugin) {
		this.states = new ArrayList<GameState>();
		this.plugin = plugin;
	}
	
	@Override
	public Iterator<GameState> iterator() {
		return this.states.iterator();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends GameState> T getCurrent(Class<T> cls) {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		if (this.getCurrent().getClass().isAssignableFrom(cls)) {
			return (T) this.getCurrent();
		} else {
			throw new GameStateException("The current-gamestate-class is not assignable from '" + cls.getSimpleName() + "'!");
		}
	}

	@Override
	public List<GameState> getStates() {
		return Collections.unmodifiableList(this.states);
	}
	
	@Override
	public GameState getState(String name) {
		if (this.disabledState != null) {
			if (this.disabledState.getClass().getSimpleName().toLowerCase().contains(name.toLowerCase())) {
				return this.disabledState;
			}
		}
		
		for (GameState state : this.states) {
			if (state.getClass().getSimpleName().toLowerCase().contains(name.toLowerCase())) {
				return state;
			}
		}
		
		return null;
	}
	
	@Override
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

	@Override
	public GameState getDisabledState() {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		return this.disabledState;
	}

	@Override
	public Game getGame() {
		return this.game;
	}
	
	@Override
	public String getCurrentName() {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		return this.stripState(this.getCurrent().getClass().getSimpleName());
	}
	
	@Override
	public boolean isRunning() {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		return this.index != -1;
	}

	@Override
	public boolean isCreated() {
		return this.created;
	}

	@Override
	public boolean hasNext() {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		return this.states.size() > this.index + 1;
	}

	@Override
	public void addGameState(GameState state) {
		if (this.isCreated()) {
			throw new GameStateException("The manager was already created!");
		} else {
			this.states.add(state);
		}
	}

	@Override
	public void initDisabledState(GameState state) {
		if (this.isCreated()) {
			throw new GameStateException("The manager was already created!");
		}
		
		this.disabledState = state;
	}

	@Override
	public void initGame(Game game) {
		if (this.isCreated()) {
			throw new GameStateException("The manager was already created!");
		}
		
		this.game = game;
	}

	@Override
	public void setRunning(boolean running) {
		if (!this.isCreated()) {
			return;
		}
		
		if (running == this.isRunning()) {
			throw new GameStateException("The manager is already in this state!");
		}
		
		this.disableState(this.getCurrent());
		
		if (!running && this.isRunning()) {
			this.index = -1;
		} else if (running && !this.isRunning()) {
			this.index = 0;
		}
		
		this.enableState(this.getCurrent());
	}

	@Override
	public void next() {
		this.next(this.index + 1);
	}

	@Override
	public void next(int indexTo) {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		if (this.index < indexTo) {
			this.switchTo(indexTo);
		} else {
			throw new GameStateException("Invalid index!");
		}
	}

	@Override
	public void back() {
		this.back(this.index - 1);
	}

	@Override
	public void back(int indexTo) {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		if (this.index > indexTo) {
			this.switchTo(indexTo);
		} else {
			throw new GameStateException("Invalid index!");
		}
	}

	@Override
	public void skip(int count) {
		this.next(this.index + count);
	}

	@Override
	public void skipAll() {
		this.next(this.states.size() - this.index);
	}

	@Override
	public void create() {
		this.create(false);
	}

	@Override
	public void create(boolean running) {
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
			
			this.created = true;
			
			if (running) {
				this.index = 0;
			} else {
				this.index = -1;
			}
			
			this.enableState(this.getCurrent());
		} else {
			throw new GameStateException("The manager was already created!");
		}
	}
	
	@Override
	public void addListener(Listener listener) {
		List<GameState> states = new ArrayList<>();
		
		if (listener.getClass().isAnnotationPresent(StateTarget.class)) {
			StateTarget target = listener.getClass().getAnnotationsByType(StateTarget.class)[0];
			
			if (target.invert()) {
				states.addAll(this.states);
			}
			
			for (String stateName : target.states()) {
				if (!target.invert()) {
					GameState state = this.getState(stateName);
					
					if (state != null) {
						states.add(state);
					} else {
						throw new GameStateException("The state '" + state + "' is unknown in listener '" + listener.getClass().getSimpleName() + "'!");
					}
				}
			}
		} else {
			states.addAll(this.states);
			
			if (this.disabledState != null) {
				states.add(this.disabledState);
			}
		}
		
		for (GameState state : states) {
			state.getListeners().add(listener);
		}
	}
	
	@Override
	public int getCurrentIndex() {
		if (!this.isCreated()) {
			throw new GameStateException("The manager has to be created!");
		}
		
		return this.index;
	}
	
	private void enableState(GameState state) {
		state.onEnter();
		Bukkit.getServer().getPluginManager().registerEvents(state, this.plugin);
		
		for (Listener listener : state.getListeners()) {
			Bukkit.getServer().getPluginManager().registerEvents(listener, this.plugin);
		}
	}
	
	private void disableState(GameState state) {
		state.onLeave();
		HandlerList.unregisterAll(state);
		
		for (Listener listener : state.getListeners()) {
			HandlerList.unregisterAll(listener);
		}
	}
	
	private void switchTo(int index) {
		if (this.isRunning()) {
			if (this.index == index) {
				throw new GameStateException("The index is already set!");
			}
			
			if (index >= 0 && this.states.size() > (index + 1)) {
				this.disableState(this.getCurrent());
				this.index = index;
				this.enableState(this.getCurrent());
			} else {
				this.setRunning(false);
			}
		} else {
			throw new GameStateException("The manager is not running!");
		}
	}
	
	private String stripState(String input) {
		return input.replace("GameState", "").replace("DisabledState", "");
	}

}
