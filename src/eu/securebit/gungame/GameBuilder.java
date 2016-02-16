package eu.securebit.gungame;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import lib.securebit.game.GameState;
import lib.securebit.game.GameStateManager;
import lib.securebit.game.impl.CraftGameStateManager;

import org.bukkit.Bukkit;

import eu.securebit.gungame.errorhandling.ErrorHandler;
import eu.securebit.gungame.exception.GunGameReflectException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.game.GameInterface;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.io.configs.FileGameConfig;

public class GameBuilder {
	
	private GameInterface gameInterface;
	
	private String name;
	
	private ErrorHandler handler;
	
	private StateRegistry stateRegistry;
	
	private FileGameConfig config;
	
	public GameBuilder() {
		this.stateRegistry = new StateRegistry();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setGameInterface(GameInterface gameInterface) {
		this.gameInterface = gameInterface;
	}
	
	public void setHandler(ErrorHandler handler) {
		this.handler = handler;
	}
	
	public void setStateRegistry(StateRegistry stateRegistry) {
		this.stateRegistry = stateRegistry;
	}
	
	public void setConfig(File file) {
		this.setConfig(Core.getRootDirectory().getGameConfigFile(file, Bukkit.getWorlds().get(0)));
	}
	
	public void setConfig(FileGameConfig config) {
		this.config = config;
	}
	
	public GunGame build() {
		GunGame game = new GunGame(this.config, this.name, this.gameInterface, this.handler);		
		
		GameStateManager<GunGame> manager = new CraftGameStateManager<>(Core.getPlugin());
		
		try {
			manager.initGame(game);
			manager.initDisabledState(this.stateRegistry.getDisabledStateClass().getConstructor(GunGame.class).newInstance(game));
			
			for (Class<? extends GameState> stateClass : this.stateRegistry.getStateClasses()) {
				manager.add(stateClass.getConstructor(GunGame.class).newInstance(game));
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
		
		manager.create(game.isReady());
		return game;
	}
	
}
