package eu.securebit.gungame.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lib.securebit.InfoLayout;
import lib.securebit.game.GameState;
import lib.securebit.game.impl.CraftGame;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import eu.securebit.gungame.GameBuilder;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.StateRegistry;
import eu.securebit.gungame.errorhandling.ErrorHandler;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.interpreter.GameOptions;
import eu.securebit.gungame.interpreter.GunGameMap;
import eu.securebit.gungame.interpreter.GunGameScoreboard;
import eu.securebit.gungame.interpreter.Interpreter;
import eu.securebit.gungame.interpreter.LevelManager;
import eu.securebit.gungame.interpreter.Messanger;
import eu.securebit.gungame.interpreter.impl.AbstractInterpreter;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.io.configs.FileGunGameConfig;
import eu.securebit.gungame.io.configs.FileLevels;
import eu.securebit.gungame.io.configs.FileMap;
import eu.securebit.gungame.io.configs.FileMessages;
import eu.securebit.gungame.io.configs.FileOptions;
import eu.securebit.gungame.io.configs.FileScoreboard;
import eu.securebit.gungame.io.directories.RootDirectory;
import eu.securebit.gungame.util.Warnings;

public class GunGame extends CraftGame<GunGamePlayer> {

	private GunGamePlayer winner;
	
	private GunGameScoreboard board;
	private LevelManager levelManager;
	private Messanger messanger;
	private GameOptions options;
	private GunGameMap map;
	
	private FileGameConfig config;
	
	private GameInterface gameInterface;
	
	private List<GameCheck> checks;
	
	private ErrorHandler errorHandler;
	
	public GunGame(FileGameConfig config, String name, GameInterface gameInterface, ErrorHandler errorHandler) {
		super(Core.getPlugin(), name);
		
		this.config = config;
		this.gameInterface = gameInterface;
		this.errorHandler = errorHandler;
		this.checks = new ArrayList<>();
		
		RootDirectory root = Core.getRootDirectory();
		
		FileLevels fileLevels = root.getLevelsFile(config.getFileLevelsLocation());
		FileMessages fileMessages = root.getMessagesFile(config.getFileMessagesLocation());
		FileScoreboard fileScoreboard = root.getScoreboardFile(config.getFileScoreboardLocation());
		FileOptions fileOptions = root.getOptionsFile(config.getFileOptionsLocation());
		FileMap fileMap = root.getMapFile(config.getFileMapLocation());
		
		this.levelManager = LevelManager.create(fileLevels);
		this.messanger = Messanger.create(fileMessages);
		this.board = GunGameScoreboard.create(fileScoreboard, this);
		
		if (fileLevels.isReady()) {
			this.options = GameOptions.create(fileOptions, this.levelManager.getLevelCount());
		}
		
		this.map = GunGameMap.create(fileMap);
		
		if (this.board.wasSuccessful()) {
			if (this.board.isEnabled()) {
				if (this.board.exists()) {
					this.board.delete();
				}
				
				this.board.create();
			}
		}
		
		if (config.isReady()) {
			this.mute(config.isMuted());
			this.registerWorld(config.getLocationLobby().getWorld());
		}
		
		if (this.map.wasSuccessful()) {
			for (Location spawn : this.map.getSpawnPoints()) {
				if (!this.containsWorld(spawn.getWorld())) {
					this.registerWorld(spawn.getWorld());
				}
			}
		}
		
		this.checks.add(new ConfigCheck(config));
		this.checks.add(new ConfigCheck(fileMessages));
		this.checks.add(new ConfigCheck(fileLevels));
		this.checks.add(new ConfigCheck(fileScoreboard));
		this.checks.add(new ConfigCheck(fileOptions));
		this.checks.add(new ConfigCheck(fileMap));
		this.checks.add(new SimpleCheck());
		this.checks.add(new InterpreterCheck(this.messanger));
		this.checks.add(new InterpreterCheck(this.levelManager));
		this.checks.add(new InterpreterCheck(this.board));
		this.checks.add(new InterpreterCheck(this.options));
		this.checks.add(new InterpreterCheck(this.map));
		
		boolean isReady = this.isReady();
		
		for (World world : this.getWorlds()) {
			world.setAutoSave(!isReady);
		}
	}
	
	@Override
	public int getSize() {
		return this.config.getMaxPlayers();
	}
	
	@Override
	public void kickPlayer(Player player, String cause) {
		this.gameInterface.kickPlayer(player, cause);
	}
	
	@Override
	public void quitPlayer(Player player) {
		if (this.winner == this.getPlayer(player)) {
			this.winner = null;
		}
		
		super.quitPlayer(player);
	}
	
	@Override
	public void resetPlayer(Player player) {
		player.setGameMode(GameMode.SURVIVAL);
		player.setHealth(20.0);
		player.setVelocity(new Vector(0, 0, 0));
		player.setFoodLevel(20);
		player.setExp(0.0F);
		player.setLevel(0);
		player.setFireTicks(0);
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[] { null, null, null, null });
		
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}
	
	@Override
	public void mute(boolean mute) {
		super.mute(mute);
		
		if (this.config.isReady()) {
			this.config.setMuted(mute);
		} else {
			this.errorHandler.throwError(Warnings.WARNING_GAME_MUTE, FileGameConfig.ERROR_LOAD);
		}
	}
	
	public void setEditMode(boolean value) {
		if (this.config.isReady()) {
			this.config.setEditMode(value);
		} else {
			this.errorHandler.throwError(Warnings.WARNING_GAME_EDITMODE, FileGameConfig.ERROR_LOAD);
		}
	}
	
	public void initWinner(GunGamePlayer player) {
		this.winner = player;
	}
	
	public void calculateGameState() {
		this.playConsoleDebugMessage("Calculating...", Main.layout());
		
		if (this.getPlayers().size() == 1) {
			this.playConsoleDebugMessage("Skiping all phases!", Main.layout());
			this.getManager().skipAll();
		}
		
		if (this.getPlayers().size() == 0) {
			this.playConsoleDebugMessage("Shutdown!", Main.layout());
			
			this.gameInterface.shutdown();
		}
	}
	
	public void shutdown() {
		this.gameInterface.shutdown();
	}
	
	public void setLobbyLocation(Location lobby) {
		this.config.setLocationLobby(lobby);
	}
	
	public boolean isEditMode() {
		return this.config.isEditMode();
	}
	
	public boolean isFileReady() {
		return this.config.isReady();
	}
	
	public boolean isReady() {
		for (GameCheck check : this.checks) {
			if (!check.check()) {
				return false;
			}
		}
		
		return true;
	}
	
	public int getMinPlayerCount() {
		return this.config.getMinPlayers();
	}
	
	public Location getLobbyLocation() {
		return this.config.getLocationLobby();
	}
	
	public FileGameConfig getFileGameConfig() {
		return this.config;
	}
	
	public GunGameScoreboard getScoreboard() {
		return this.board;
	}
	
	public Messanger getMessanger() {
		return this.messanger;
	}
	
	public LevelManager getLevelManager() {
		return this.levelManager;
	}
	
	public GameOptions getOptions() {
		return this.options;
	}
	
	public GunGameMap getMap() {
		return this.map;
	}
	
	public GameBuilder toBuilder() {
		GameBuilder builder = new GameBuilder();
		builder.setConfig(this.config);
		builder.setGameInterface(this.gameInterface);
		builder.setHandler(this.errorHandler);
		builder.setName(this.getName());
		
		StateRegistry registry = new StateRegistry();
		registry.clear();
		registry.setDisabledStateClass(this.getManager().getDisabledState().getClass());
		
		for (GameState state : this.getManager().getAll()) {
			registry.add(state.getClass());
		}
		
		builder.setStateRegistry(registry);
		
		return builder;
	}
	
	public GunGamePlayer getWinner() {
		return this.winner;
	}
	
	public List<GameCheck> getChecks() {
		return Collections.unmodifiableList(this.checks);
	}
	
	
	private class ConfigCheck extends ErrorCheck {
		
		private FileGunGameConfig config;
		
		public ConfigCheck(FileGunGameConfig config) {
			super(config.getIdentifier() + "-file", config.getErrorLoad());
			
			this.config = config;
		}
		
		@Override
		public boolean check() {
			return this.config.isReady();
		}
		
	}
	
	private class InterpreterCheck extends ErrorCheck {
		
		private Interpreter interpreter;
		
		public InterpreterCheck(Interpreter interpreter) {
			super(((AbstractInterpreter<?>) interpreter).getConfig().getIdentifier() + "-interpreter", interpreter.getErrorMain());
			
			this.interpreter = interpreter;
		}
		
		@Override
		public boolean check() {
			return this.interpreter.isInterpreted();
		}
		
	}
	
	private abstract class ErrorCheck extends GameCheck {
		
		private ThrownError error;
		
		public ErrorCheck(String name, ThrownError error) {
			super(GunGame.this, name);
			
			this.error = error;
		}
		
		@Override
		public boolean check() {
			return !GunGame.this.errorHandler.isErrorPresent(this.error);
		}
		
		@Override
		public String getFixPosibility() {
			if (this.error.getLayout() instanceof LayoutErrorFixable) {
				return "Try */gungame fix " + InfoLayout.replaceKeys(this.error.getParsedObjectId()) + "* to fix the error!";
			} else {
				return null;
			}
		}
		
		@Override
		public String getFailCause() {
			ThrownError cause = GunGame.this.errorHandler.getCause(this.error);
			
			return InfoLayout.replaceKeys(cause.getParsedObjectId()) + " (" + InfoLayout.replaceKeys(cause.getParsedMessage()) + ")";
		}
		
	}
	
	private class SimpleCheck extends GameCheck {

		public SimpleCheck() {
			super(GunGame.this, null);
		}
		
		@Override
		public void stageStatus(InfoLayout layout) {
			layout.line("");
		}

		@Override
		public boolean check() {
			return true;
		}

		@Override
		public String getFailCause() {
			return null;
		}

		@Override
		public String getFixPosibility() {
			return null;
		}
		
	}

}