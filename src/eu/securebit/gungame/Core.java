package eu.securebit.gungame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import lib.securebit.command.BasicCommand;
import lib.securebit.game.GameStateManager;
import lib.securebit.game.impl.CraftGameStateManager;

import org.bukkit.plugin.Plugin;

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
		manager.add(new GameStateGrace(instance));
		manager.add(new GameStateIngame(instance));
		manager.add(new GameStateEnd(instance));
		manager.initDisabledState(new DisabledStateEdit(instance));
		manager.create(instance.isReady());
		
		return instance;
	}
	
	public static BasicCommand getCommand() {
		return Main.instance().getCommand();
	}
	
	public static Plugin getPlugin() {
		return Main.instance();
	}
	
}