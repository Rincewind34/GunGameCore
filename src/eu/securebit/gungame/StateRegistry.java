package eu.securebit.gungame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lib.securebit.game.GameState;
import eu.securebit.gungame.game.states.DisabledStateEdit;
import eu.securebit.gungame.game.states.GameStateEnd;
import eu.securebit.gungame.game.states.GameStateGrace;
import eu.securebit.gungame.game.states.GameStateIngame;
import eu.securebit.gungame.game.states.GameStateLobby;
import eu.securebit.gungame.game.states.GameStateSpawns;

public class StateRegistry {
	
	private List<Class<? extends GameState>> stateClasses;
	
	private Class<? extends GameState> disabledStateClass;
	
	public StateRegistry() {
		this.stateClasses = new ArrayList<>();
		
		this.stateClasses.add(GameStateLobby.class);
		this.stateClasses.add(GameStateSpawns.class);
		this.stateClasses.add(GameStateGrace.class);
		this.stateClasses.add(GameStateIngame.class);
		this.stateClasses.add(GameStateEnd.class);
		
		this.disabledStateClass = DisabledStateEdit.class;
	}
	
	public void clear() {
		this.stateClasses.clear();
	}
	
	public void add(Class<? extends GameState> stateClass) {
		this.stateClasses.add(stateClass);
	}
	
	public void setDisabledStateClass(Class<? extends GameState> disabledStateClass) {
		this.disabledStateClass = disabledStateClass;
	}
	
	public Class<? extends GameState> getDisabledStateClass() {
		return this.disabledStateClass;
	}
	
	public List<Class<? extends GameState>> getStateClasses() {
		return Collections.unmodifiableList(this.stateClasses);
	}
	
}
