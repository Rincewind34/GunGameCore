package eu.securebit.gungame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import lib.securebit.game.GameStateManager;
import lib.securebit.game.impl.CraftGameStateManager;
import eu.securebit.gungame.game.states.DisabledStateEdit;
import eu.securebit.gungame.game.states.GameStateEnd;
import eu.securebit.gungame.game.states.GameStateGrace;
import eu.securebit.gungame.game.states.GameStateIngame;
import eu.securebit.gungame.game.states.GameStateLobby;

public class Core {
	
	@SuppressWarnings("unchecked")
	public static <T extends GunGame> T createNewGameInstance(Settings settings, Class<T> gameClass) {
		T instance = null;
		
		try {
			Constructor<?> constructor = gameClass.getConstructor(Settings.class);
			instance = (T) constructor.newInstance(settings);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		GameStateManager<T> manager = new CraftGameStateManager<T>(Main.instance());
		manager.initGame(instance);
		manager.add(new GameStateLobby(instance));
		manager.add(new GameStateGrace());
		manager.add(new GameStateIngame());
		manager.add(new GameStateEnd());
		manager.initDisabledState(new DisabledStateEdit());
		manager.create(instance.isReady());
		
		return instance;
	}
	
}
