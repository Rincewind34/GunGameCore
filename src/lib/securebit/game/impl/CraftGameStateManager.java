package lib.securebit.game.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lib.securebit.game.Game;
import lib.securebit.game.GameState;
import lib.securebit.game.GameStateManager;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class CraftGameStateManager<G extends Game<?>> implements GameStateManager<G> {
	
	private List<GameState> states;
	private GameState disabledState;
	
	private G game;
	
	private Plugin plugin;
	
	private int index;
	
	private boolean created;
	
	public CraftGameStateManager(Plugin plugin) {
		this.plugin = plugin;
		this.states = new ArrayList<GameState>();
	}
	
	@Override
	public Iterator<GameState> iterator() {
		return this.states.iterator();
	}

	@Override
	public void next() {
		this.next(1);
	}
	
	@Override
	public void next(int count) {
		this.next(count, true);
	}
	
	@Override
	public void next(int count, boolean startStates) {
		if (!this.created) {
			throw new GameStateException("The manager was not created!");
		}
		
		if (count <= 0) {
			throw new GameStateException("The count must be positiv!");
		}
		
		if (!this.isRunning()) {
			throw new GameStateException("Cannot load next GameState, because DisabledState is currently active.");
		}
		
		if (!startStates) {
			this.getCurrent().stop();
			this.getCurrent().unregisterListener(this.plugin);
		}
		
		for (int i = 0; i < count; i++) {
			if (startStates) {
				this.getCurrent().stop();
				this.getCurrent().unregisterListener(this.plugin);
			}
			
			this.getCurrent().unload();
			this.index = this.index + 1;
			
			if (this.index >= this.getSize()) {
				throw new GameStateException("Invalid index (index = " + index + ")!");
			}
			
			this.getCurrent().load();
			
			if (startStates) {
				this.getCurrent().registerListener(this.plugin);
				this.getCurrent().start();
			}
		}
		
		if (!startStates) {
			this.getCurrent().registerListener(this.plugin);
			this.getCurrent().start();
		}
	}

	@Override
	public void skip(int count) {
		if (!this.created) {
			throw new GameStateException("The manager was not created!");
		}
		
		if (count <= 0) {
			throw new GameStateException("The count must be positiv!");
		}
		
		this.getCurrent().stop();
		this.getCurrent().unregisterListener(this.plugin);
		this.getCurrent().unload();
		
		this.index = this.index + count;
		
		if (this.index >= this.getSize()) {
			throw new GameStateException("Invalid index (index = " + index + ")!");
		}
		
		this.getCurrent().load();
		this.getCurrent().registerListener(this.plugin);
		this.getCurrent().start();
	}
	
	@Override
	public void skipAll() {
		if (!this.created) {
			throw new GameStateException("The manager was not created!");
		}
		
		this.skip(this.getSize() - 1 - this.index);
	}

	@Override
	public void setRunning(boolean running) {
		if (!this.created) {
			throw new GameStateException("The manager was not created!");
		}
		
		if (this.isRunning() == running) {
			throw new GameStateException("You have to change the running-value!");
		}
		
		this.setRunning(running, true);
	}

	@Override
	public void add(GameState state) {
		if (this.created) {
			throw new GameStateException("The manager is already created!");
		}
		
		this.states.add(state);
	}

	@Override
	public void remove(GameState state) {
		if (this.created) {
			throw new GameStateException("The manager is already created!");
		}
		
		this.states.remove(state);
	}

	@Override
	public void initDisabledState(GameState state) {
		if (this.created) {
			throw new GameStateException("The manager is already created!");
		}
		
		this.disabledState = state;
	}

	@Override
	public void initGame(G game) {
		if (this.created) {
			throw new GameStateException("The manager is already created!");
		}
		
		this.game = game;
		((CraftGame<?>) this.game).setManager(this);
	}
	
	@Override
	public void destroy() {
		if (!this.created) {
			throw new GameStateException("The manager is not created!");
		}
		
		HandlerList.unregisterAll(this.game);
		
		this.getCurrent().stop();
		this.getCurrent().unregisterListener(this.plugin);
		this.getCurrent().unload();
		
		this.created = false;
	}
	
	@Override
	public void create() {
		this.create(false);
	}

	@Override
	public void create(boolean running) {
		if (!this.created) {
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
			this.plugin.getServer().getPluginManager().registerEvents(this.game, this.plugin);
			this.setRunning(running, false);
		} else {
			throw new GameStateException("The manager is already created!");
		}
	}

	@Override
	public boolean isCreated() {
		return this.created;
	}

	@Override
	public boolean isRunning() {
		if (!this.created) {
			throw new GameStateException("The manager was not created!");
		}
		
		return this.index != -1;
	}

	@Override
	public boolean hasNext() {
		return this.index < (this.getSize() - 1);
	}

	@Override
	public boolean isCurrent(Class<? extends GameState> stateClass) {
		return this.getCurrent().getClass() == stateClass;
	}

	@Override
	public int getCurrentIndex() {
		return this.index;
	}

	@Override
	public int getSize() {
		return this.states.size();
	}

	@Override
	public GameState getDisabledState() {
		return this.disabledState;
	}

	@Override
	public GameState getCurrent() {
		if (!this.created) {
			throw new GameStateException("The manager was not created!");
		}
		
		if (this.index == -1) {
			return this.disabledState;
		} else {
			return this.states.get(this.index);
		}
	}

	@Override
	public GameState getState(String name) {
		for (GameState state : this.getAll()) {
			if (state.getName().equals(name)) {
				return state;
			}
		}
		
		if (this.disabledState.getName().equals(name)) {
			return this.disabledState;
		}
		
		return null;
	}

	@Override
	public G getGame() {
		return this.game;
	}

	@Override
	public List<GameState> getAll() {
		return Collections.unmodifiableList(this.states);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends GameState> T getCurrent(Class<T> cls) {
		if (this.isCurrent(cls)) {
			return (T) this.getCurrent();
		} else {
			throw new GameStateException("Unable to cast current gamestate!");
		}
	}
	
	private void setRunning(boolean running, boolean withShutdown) {
		if (withShutdown) {
			this.getCurrent().stop();
			this.getCurrent().unregisterListener(this.plugin);
			this.getCurrent().unload();
		}
		
		if (running) {
			this.index = 0;
		} else {
			this.index = -1;
		}
		
		this.getCurrent().load();
		this.getCurrent().registerListener(this.plugin);
		this.getCurrent().start();
	}
	
}
