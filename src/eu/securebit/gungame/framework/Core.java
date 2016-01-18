package eu.securebit.gungame.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lib.securebit.command.ArgumentedCommand;
import lib.securebit.game.GameStateManager;
import lib.securebit.game.impl.CraftGameStateManager;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.framework.Settings.SettingsLocations;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.game.states.DisabledStateEdit;
import eu.securebit.gungame.game.states.GameStateEnd;
import eu.securebit.gungame.game.states.GameStateGrace;
import eu.securebit.gungame.game.states.GameStateIngame;
import eu.securebit.gungame.game.states.GameStateLobby;
import eu.securebit.gungame.game.states.GameStateSpawns;
import eu.securebit.gungame.io.FileBootConfig;

public class Core {
	
	@SuppressWarnings("unchecked")
	public static <T extends GunGame> T createNewGameInstance(Class<T> gameClass, Object... values) {
		T instance = null;
		
		Class<?>[] classArray = new Class<?>[values.length];
		
		for (int i = 0; i < values.length; i++) {
			classArray[i] = values[i].getClass();
		}
		
		search: {
			try {
				constrcutors: for (Constructor<?> constructor : gameClass.getConstructors()) {
					if (classArray.length != constructor.getParameterCount()) {
						continue;
					}
				
					for (int i = 0; i < constructor.getParameterCount(); i++) {
						if (!Core.isMatching(classArray[i], values[i].getClass())) {
							continue constrcutors;
						}
					}
					
					instance = (T) constructor.newInstance(values);
					break search;
				}
				
				throw new GunGameException("Could not find a matching constructor!");
			} catch (SecurityException e) {
				throw new GunGameException(e.getMessage(), e);
			} catch (InstantiationException e) {
				throw new GunGameException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new GunGameException(e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				throw new GunGameException(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				throw new GunGameException(e.getMessage(), e);
			}
		}
		
		boolean ready = instance.isReady();
		
		GameStateManager<T> manager = new CraftGameStateManager<T>(Main.instance());
		manager.initGame(instance);
		manager.add(new GameStateLobby(instance));
		manager.add(new GameStateSpawns(instance));
		manager.add(new GameStateGrace(instance));
		manager.add(new GameStateIngame(instance));
		manager.add(new GameStateEnd(instance));
		manager.initDisabledState(new DisabledStateEdit(instance));
		
		List<World> worlds = new ArrayList<>();
		
		SettingsLocations locations = instance.getSettings().locations();
		
		if (ready) {
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
		
		manager.create(ready);
		
		return instance;
	}
	
	public static ArgumentedCommand getCommand() {
		return Main.instance().getCommand();
	}
	
	public static Plugin getPlugin() {
		return Main.instance();
	}
	
	public static FileBootConfig getBootConfig() {
		return Main.instance().getFileBootConfig();
	}
	
	private static boolean isMatching(Class<?> cls, Class<?> clsCompare) {
		if (cls == clsCompare) {
			return true;
		} else {
			return Core.isMatching(cls, clsCompare.getSuperclass());
		}
	}
	
}