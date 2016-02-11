package eu.securebit.gungame.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lib.securebit.InfoLayout;
import lib.securebit.game.impl.CraftGame;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.ErrorHandler;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.interpreter.GameOptions;
import eu.securebit.gungame.interpreter.GunGameScoreboard;
import eu.securebit.gungame.interpreter.LevelManager;
import eu.securebit.gungame.interpreter.LocationManager;
import eu.securebit.gungame.interpreter.Messanger;
import eu.securebit.gungame.interpreter.impl.CraftGameOptions;
import eu.securebit.gungame.interpreter.impl.CraftGunGameScoreboard;
import eu.securebit.gungame.interpreter.impl.CraftLevelManager;
import eu.securebit.gungame.interpreter.impl.CraftMessanger;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.io.configs.FileLevels;
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
	private LocationManager locationManager;
	
	private String name;
	
	private FileGameConfig config;
	
	private GameInterface gameInterface;
	
	private List<GameCheck> checks;
	
	private ErrorHandler errorHandler;
	
	public GunGame(FileGameConfig config, String name, GameInterface gameInterface, ErrorHandler errorHandler) {
		super(Main.instance());
		
		this.name = name;
		this.gameInterface = gameInterface;
		this.errorHandler = errorHandler;
		this.checks = new ArrayList<>();
		
		RootDirectory root = Main.instance().getRootDirectory();
		
		FileLevels fileLevels = root.getLevelsFile(config.getFileLevelsLocation());
		FileMessages fileMessages = root.getMessagesFile(config.getFileMessagesLocation());
		FileScoreboard fileScoreboard = root.getScoreboardFile(config.getFileScoreboardLocation());
		FileOptions fileOptions = root.getOptionsFile(config.getFileOptionsLocation());
		
		if (fileLevels.isAccessable()) {
			this.levelManager = LevelManager.create(fileLevels);
		}
		
		if (fileMessages.isAccessable()) {
			this.messanger = Messanger.create(fileMessages);
		}
		
		if (fileScoreboard.isAccessable()) {
			this.board = GunGameScoreboard.create(fileScoreboard, this);
		}
		
		if (fileOptions.isAccessable()) {
			this.options = GameOptions.create(fileOptions);
		}
		
		if (config.isAccessable()) {
			this.locationManager = LocationManager.create(config);
			this.config = config;
		}
		
		if (this.board != null) {
			if (this.board.isEnabled()) {
				if (this.board.exists()) {
					this.board.delete();
				}
				
				this.board.create();
			}
		}
		
		this.checks.add(new GameCheck(this, "config-file") {
			
			@Override
			public boolean check() {
				return GunGame.this.config.isReady();
			}
			
			@Override
			public String getFixPosibility() {
				ThrownError error = this.getError();
				
				return "Try */gungame fix " + InfoLayout.replaceKeys(error.getParsedObjectId()) + "* to fix the error";
			}
			
			@Override
			public String getFailCause() {
				ThrownError error = this.getError();
				
				return InfoLayout.replaceKeys(error.getParsedObjectId()) + " (" + InfoLayout.replaceKeys(error.getParsedMessage()) + ")";
			}
			
			private ThrownError getError() {
				return Main.instance().getErrorHandler().getCause(GunGame.this.config.createError(FileGameConfig.ERROR_MAIN));
			}
			
		});
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
		
		if (this.config.isAccessable()) {
			this.config.setMuted(mute);
		} else {
			this.errorHandler.throwError(Warnings.WARNING_GAME_MUTE, FileGameConfig.ERROR_LOAD);
		}
	}
	
	public void setEditMode(boolean value) {
		if (this.config != null) {
			this.config.setEditMode(value);
		} else {
			this.errorHandler.throwError(Warnings.WARNING_GAME_EDITMODE, FileGameConfig.ERROR_LOAD);
		}
	}
	
	public void initWinner(GunGamePlayer player) {
		this.winner = player;
	}
	
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
	
	public boolean isEditMode() {
		return this.config.isEditMode();
	}
	
	public boolean isReady() {
		if (!((CraftLevelManager) this.levelManager).getFile().isReady()) {
			return false;
		}
		
		if (!((CraftMessanger) this.messanger).getFile().isReady()) {
			return false;
		}
		
		if (!((CraftGunGameScoreboard) this.board).getFile().isReady()) {
			return false;
		}
		
		if (!((CraftGameOptions) this.options).getFile().isReady()) {
			return false;
		}
		
		if (this.getLocationManager().getSpawnPointCount() < 1) {
			return false;
		}
		
		return !this.isEditMode();
	}
	
	public int getMinPlayerCount() {
		return this.config.getMinPlayers();
	}
	
	public int getStartLevel() {
		return this.config.getStartLevel();
	}
	
	public String getName() {
		return this.name;
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
	
	public LocationManager getLocationManager() {
		return this.locationManager;
	}
	
	public GameInterface getInterface() {
		return this.gameInterface;
	}
	
	public GunGamePlayer getWinner() {
		return this.winner;
	}
	
	public List<GameCheck> getChecks() {
		return Collections.unmodifiableList(this.checks);
	}
	
}