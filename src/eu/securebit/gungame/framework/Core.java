package eu.securebit.gungame.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lib.securebit.command.ArgumentedCommand;
import lib.securebit.game.GameState;
import lib.securebit.game.GameStateManager;
import lib.securebit.game.impl.CraftGameStateManager;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.framework.Settings.SettingsLocations;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.io.FileBootConfig;

public class Core {
	
	public static Class<? extends GameState> STATE_LOBBY;
	public static Class<? extends GameState> STATE_SPAWNS;
	public static Class<? extends GameState> STATE_GRACE;
	public static Class<? extends GameState> STATE_INGAME;
	public static Class<? extends GameState> STATE_END;
	public static Class<? extends GameState> STATE_DISABLED;
	
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
		
		try {
			manager.initGame(instance);
			
			if (Core.STATE_LOBBY != null) {
				manager.add(Core.newStateInstance(Core.STATE_LOBBY, instance));
			} if (Core.STATE_SPAWNS != null) {
				manager.add(Core.newStateInstance(Core.STATE_SPAWNS, instance));
			} if (Core.STATE_GRACE != null) {
				manager.add(Core.newStateInstance(Core.STATE_GRACE, instance));
			} if (Core.STATE_INGAME != null) {
				manager.add(Core.newStateInstance(Core.STATE_INGAME, instance));
			} if (Core.STATE_END != null) {
				manager.add(Core.newStateInstance(Core.STATE_END, instance));
			} if (Core.STATE_DISABLED != null) {
				manager.initDisabledState(Core.newStateInstance(Core.STATE_DISABLED, instance));
			}
		} catch (NoSuchMethodException e) {
			throw new GunGameException(e.getMessage(), e);
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
	
	private static GameState newStateInstance(Class<? extends GameState> state, GunGame gungame)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		return state.getConstructor(GunGame.class).newInstance(gungame);
	}
	
}