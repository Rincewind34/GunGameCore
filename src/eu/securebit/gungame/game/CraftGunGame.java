package eu.securebit.gungame.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lib.securebit.InfoLayout;
import lib.securebit.game.impl.CraftGame;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.ErrorHandler;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
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

public class CraftGunGame extends CraftGame<GunGamePlayer> implements GunGame {

	private GunGamePlayer winner;
	
	private GunGameScoreboard board;
	private LevelManager levelManager;
	private Messanger messanger;
	private GameOptions options;
	private GunGameMap map;
	
	private String name;
	
	private FileGameConfig config;
	
	private GameInterface gameInterface;
	
	private List<GameCheck> checks;
	
	private ErrorHandler errorHandler;
	
	public CraftGunGame(FileGameConfig config, String name, GameInterface gameInterface, ErrorHandler errorHandler) {
		super(Main.instance());
		
		this.config = config;
		this.name = name;
		this.gameInterface = gameInterface;
		this.errorHandler = errorHandler;
		this.checks = new ArrayList<>();
		
		RootDirectory root = Main.instance().getRootDirectory();
		
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
	public void setEditMode(boolean value) {
		if (this.config.isReady()) {
			this.config.setEditMode(value);
		} else {
			this.errorHandler.throwError(Warnings.WARNING_GAME_EDITMODE, FileGameConfig.ERROR_LOAD);
		}
	}
	
	@Override
	public void initWinner(GunGamePlayer player) {
		this.winner = player;
	}
	
	@Override
	public void calculateGameState() {
		this.playConsoleMessage(Main.layout().format("Calculating..."));
		
		if (this.getPlayers().size() == 1) {
			this.playConsoleMessage(Main.layout().format("Skiping all phases!"));
			this.getManager().skipAll();
		}
		
		if (this.getPlayers().size() == 0) {
			this.playConsoleMessage(Main.layout().format("Shutdown!"));
			
			this.gameInterface.initShutdown();
		}
	}
	
	@Override
	public void setLobbyLocation(Location lobby) {
		this.config.setLocationLobby(lobby);
	}
	
	@Override
	public boolean isEditMode() {
		return this.config.isEditMode();
	}
	
	@Override
	public boolean isFileReady() {
		return this.config.isReady();
	}
	
	@Override
	public boolean isReady() {
		for (GameCheck check : this.checks) {
			if (!check.check()) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public int getMinPlayerCount() {
		return this.config.getMinPlayers();
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public Location getLobbyLocation() {
		return this.config.getLocationLobby();
	}
	
	@Override
	public FileGameConfig getFileGameConfig() {
		return this.config;
	}
	
	@Override
	public GunGameScoreboard getScoreboard() {
		return this.board;
	}
	
	@Override
	public Messanger getMessanger() {
		return this.messanger;
	}
	
	@Override
	public LevelManager getLevelManager() {
		return this.levelManager;
	}
	
	@Override
	public GameOptions getOptions() {
		return this.options;
	}
	
	@Override
	public GunGameMap getMap() {
		return this.map;
	}
	
	@Override
	public GameInterface getInterface() {
		return this.gameInterface;
	}
	
	@Override
	public GunGamePlayer getWinner() {
		return this.winner;
	}
	
	@Override
	public List<GameCheck> getChecks() {
		return Collections.unmodifiableList(this.checks);
	}
	
	@Override
	public void quitPlayer(Player player) {
		if (this.winner == this.getPlayer(player)) {
			this.winner = null;
		}
		
		super.quitPlayer(player);
	}
	
	@Override
	public void playConsoleMessage(String msg) {
		if (msg.matches("^.+\\[.+\\] .+")) {
			msg = msg.replaceFirst("^.+\\[.+\\] ", "");
		} else {
			msg = Main.layout().colorPrimary + "{" + Main.layout().colorSecondary + msg + Main.layout().colorPrimary + "}";
		}
		
		super.playConsoleMessage(Main.layout().colorSecondary + this.name + ": " + msg);
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
			super(CraftGunGame.this, name);
			
			this.error = error;
		}
		
		@Override
		public boolean check() {
			return !CraftGunGame.this.errorHandler.isErrorPresent(this.error);
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
			ThrownError cause = CraftGunGame.this.errorHandler.getCause(this.error);
			
			return InfoLayout.replaceKeys(cause.getParsedObjectId()) + " (" + InfoLayout.replaceKeys(cause.getParsedMessage()) + ")";
		}
		
	}
	
	private class SimpleCheck extends GameCheck {

		public SimpleCheck() {
			super(CraftGunGame.this, null);
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