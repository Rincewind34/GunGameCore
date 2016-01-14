package eu.securebit.gungame.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lib.securebit.command.BasicCommand;
import lib.securebit.game.GameStateManager;
import lib.securebit.game.impl.CraftGameStateManager;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.framework.Settings.SettingsLocations;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.game.states.DisabledStateEdit;
import eu.securebit.gungame.game.states.GameStateEnd;
import eu.securebit.gungame.game.states.GameStateGrace;
import eu.securebit.gungame.game.states.GameStateIngame;
import eu.securebit.gungame.game.states.GameStateLobby;
import eu.securebit.gungame.game.states.GameStateSpawns;

public class Core {
	
	@SuppressWarnings("unchecked")
	public static <T extends GunGame> T createNewGameInstance(Settings settings, ActionInterface actionInterface, Class<T> gameClass) {
		T instance = null;
		
		try {
			Constructor<?> constructor = gameClass.getConstructor(Settings.class, ActionInterface.class);
			instance = (T) constructor.newInstance(settings, actionInterface);
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
		manager.add(new GameStateSpawns(instance));
		manager.add(new GameStateGrace(instance));
		manager.add(new GameStateIngame(instance));
		manager.add(new GameStateEnd(instance));
		manager.initDisabledState(new DisabledStateEdit(instance));
		
		List<World> worlds = new ArrayList<>();
		
		SettingsLocations locations = settings.locations();
		
		if (instance.isReady()) {
			locations.getLobbyLocation().getWorld().setAutoSave(false);
		}
		
		worlds.add(locations.getLobbyLocation().getWorld());
		instance.registerWorld(locations.getLobbyLocation().getWorld());
		
		for (Location spawn : locations.getSpawnPoints().values()) {
			if (!worlds.contains(spawn.getWorld())) {
				if (instance.isReady()) {
					spawn.getWorld().setAutoSave(false);
				}
					
				instance.registerWorld(spawn.getWorld());
				worlds.add(spawn.getWorld());
			}
		}
		
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