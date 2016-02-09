package eu.securebit.gungame.framework;

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
import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.exception.GunGameReflectException;
import eu.securebit.gungame.game.GameInterface;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.game.states.DisabledStateEdit;
import eu.securebit.gungame.game.states.GameStateEnd;
import eu.securebit.gungame.game.states.GameStateGrace;
import eu.securebit.gungame.game.states.GameStateIngame;
import eu.securebit.gungame.game.states.GameStateLobby;
import eu.securebit.gungame.game.states.GameStateSpawns;
import eu.securebit.gungame.interpreter.LocationManager;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.io.directories.RootDirectory;

public class Core {
	
	public static Class<? extends GameState> STATE_LOBBY;
	public static Class<? extends GameState> STATE_SPAWNS;
	public static Class<? extends GameState> STATE_GRACE;
	public static Class<? extends GameState> STATE_INGAME;
	public static Class<? extends GameState> STATE_END;
	public static Class<? extends GameState> STATE_DISABLED;
	
	static {
		Core.STATE_LOBBY = GameStateLobby.class;
		Core.STATE_SPAWNS = GameStateSpawns.class;
		Core.STATE_GRACE = GameStateGrace.class;
		Core.STATE_INGAME = GameStateIngame.class;
		Core.STATE_END = GameStateEnd.class;
		Core.STATE_DISABLED = DisabledStateEdit.class;
	}
	
	public static GunGame createNewGameInstance(FileGameConfig config, String name, GameInterface gameInterface) {
		GunGame game = new GunGame(config, name, gameInterface);		
		
		boolean ready = game.isReady();
		
		GameStateManager<GunGame> manager = new CraftGameStateManager<>(Main.instance());
		
		try {
			manager.initGame(game);
			
			if (Core.STATE_LOBBY != null) {
				manager.add(Core.newStateInstance(Core.STATE_LOBBY, game));
			} if (Core.STATE_SPAWNS != null) {
				manager.add(Core.newStateInstance(Core.STATE_SPAWNS, game));
			} if (Core.STATE_GRACE != null) {
				manager.add(Core.newStateInstance(Core.STATE_GRACE, game));
			} if (Core.STATE_INGAME != null) {
				manager.add(Core.newStateInstance(Core.STATE_INGAME, game));
			} if (Core.STATE_END != null) {
				manager.add(Core.newStateInstance(Core.STATE_END, game));
			} if (Core.STATE_DISABLED != null) {
				manager.initDisabledState(Core.newStateInstance(Core.STATE_DISABLED, game));
			}
		} catch (NoSuchMethodException ex) {
			throw GunGameReflectException.fromOther(ex);
		} catch (SecurityException ex) {
			throw GunGameReflectException.fromOther(ex);
		} catch (InstantiationException ex) {
			throw GunGameReflectException.fromOther(ex);
		} catch (IllegalAccessException ex) {
			throw GunGameReflectException.fromOther(ex);
		} catch (IllegalArgumentException ex) {
			throw GunGameReflectException.fromOther(ex);
		} catch (InvocationTargetException ex) {
			throw GunGameReflectException.fromOther(ex);
		}
		
		List<World> worlds = new ArrayList<>();
		
		LocationManager locations = game.getLocationManager();
		
		if (ready) {
			locations.getLobbyLocation().getWorld().setAutoSave(false);
		}
		
		worlds.add(locations.getLobbyLocation().getWorld());
		game.registerWorld(locations.getLobbyLocation().getWorld());
		
		for (Location spawn : locations.getSpawnPoints()) {
			if (!worlds.contains(spawn.getWorld())) {
				if (game.isReady()) {
					spawn.getWorld().setAutoSave(false);
				}
					
				game.registerWorld(spawn.getWorld());
				worlds.add(spawn.getWorld());
			}
		}
		
		manager.create(ready);
		
		return game;
	}
	
	public static ArgumentedCommand getCommand() {
		return Main.instance().getCommand();
	}
	
	public static Plugin getPlugin() {
		return Main.instance();
	}
	
	public static RootDirectory getRootDirectory() {
		return Main.instance().getRootDirectory();
	}
	
	public static CraftErrorHandler getErrorHandler() {
		return Main.instance().getErrorHandler();
	}
	
	public static boolean isFrameLoaded() {
		return !Main.instance().getErrorHandler().isErrorPresent(Frame.ERROR_LOAD);
	}
	
	public static boolean isFrameEnabled() {
		return !Main.instance().getErrorHandler().isErrorPresent(Frame.ERROR_ENABLE);
	}
	
	private static GameState newStateInstance(Class<? extends GameState> state, GunGame gungame)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		return state.getConstructor(GunGame.class).newInstance(gungame);
	}
	
}