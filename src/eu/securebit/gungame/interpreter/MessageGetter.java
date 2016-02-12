package eu.securebit.gungame.interpreter;

import eu.securebit.gungame.interpreter.impl.CraftMessanger;

public interface MessageGetter {
	
	public abstract String execute(CraftMessanger messanger);
	
}
