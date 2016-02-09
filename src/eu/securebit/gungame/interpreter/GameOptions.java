package eu.securebit.gungame.interpreter;

import eu.securebit.gungame.interpreter.impl.CraftGameOptions;
import eu.securebit.gungame.io.configs.FileOptions;

public interface GameOptions extends Interpreter {
	
	public static GameOptions create(FileOptions file) {
		return new CraftGameOptions(file);
	}
	
	
	public abstract void autoRespawn(boolean value);
	
	public abstract void careNaturalDeath(boolean value);
	
	public abstract void levelReset(boolean value);
	
	public abstract boolean autoRespawn();
	
	public abstract boolean careNaturalDeath();
	
	public abstract boolean levelReset();
	
}
