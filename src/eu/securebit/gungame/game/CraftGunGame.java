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
import eu.securebit.gungame.interpreter.GunGameMap;
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

public class CraftGunGame extends CraftGame<GunGamePlayer> implements GunGame {

	private GunGamePlayer winner;
	
	private GunGameScoreboard board;
	private LevelManager levelManager;
	private Messanger messanger;
	private GameOptions options;
	private GunGameMap locationManager;
	
	private String name;
	
	private FileGameConfig config;
	
	private GameInterface gameInterface;
	
	private List<GameCheck> checks;
	
	private ErrorHandler errorHandler;
	
	public CraftGunGame(FileGameConfig config, String name, GameInterface gameInterface, ErrorHandler errorHandler) {
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
			this.locationManager = GunGameMap.create(config);
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
				return CraftGunGame.this.config.isReady();
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
				return Main.instance().getErrorHandler().getCause(CraftGunGame.this.config.createError(FileGameConfig.ERROR_MAIN));
			}
			
		});
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#getSize()
	 */
	@Override
	@Override
	public int getSize() {
		return this.config.getMaxPlayers();
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#kickPlayer(org.bukkit.entity.Player, java.lang.String)
	 */
	@Override
	@Override
	public void kickPlayer(Player player, String cause) {
		this.gameInterface.kickPlayer(player, cause);
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#quitPlayer(org.bukkit.entity.Player)
	 */
	@Override
	@Override
	public void quitPlayer(Player player) {
		if (this.winner == this.getPlayer(player)) {
			this.winner = null;
		}
		
		super.quitPlayer(player);
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#playConsoleMessage(java.lang.String)
	 */
	@Override
	@Override
	public void playConsoleMessage(String msg) {
		if (msg.matches("^.+\\[.+\\] .+")) {
			msg = msg.replaceFirst("^.+\\[.+\\] ", "");
		} else {
			msg = Main.layout().colorPrimary + "{" + Main.layout().colorSecondary + msg + Main.layout().colorPrimary + "}";
		}
		
		super.playConsoleMessage(Main.layout().colorSecondary + this.name + ": " + msg);
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#resetPlayer(org.bukkit.entity.Player)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#mute(boolean)
	 */
	@Override
	@Override
	public void mute(boolean mute) {
		super.mute(mute);
		
		if (this.config.isAccessable()) {
			this.config.setMuted(mute);
		} else {
			this.errorHandler.throwError(Warnings.WARNING_GAME_MUTE, FileGameConfig.ERROR_LOAD);
		}
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#setEditMode(boolean)
	 */
	@Override
	public void setEditMode(boolean value) {
		if (this.config != null) {
			this.config.setEditMode(value);
		} else {
			this.errorHandler.throwError(Warnings.WARNING_GAME_EDITMODE, FileGameConfig.ERROR_LOAD);
		}
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#initWinner(eu.securebit.gungame.game.GunGamePlayer)
	 */
	@Override
	public void initWinner(GunGamePlayer player) {
		this.winner = player;
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#calculateGameState()
	 */
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
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#isEditMode()
	 */
	@Override
	public boolean isEditMode() {
		return this.config.isEditMode();
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#isReady()
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#getMinPlayerCount()
	 */
	@Override
	public int getMinPlayerCount() {
		return this.config.getMinPlayers();
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#getStartLevel()
	 */
	@Override
	public int getStartLevel() {
		return this.config.getStartLevel();
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#getScoreboard()
	 */
	@Override
	public GunGameScoreboard getScoreboard() {
		return this.board;
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#getMessanger()
	 */
	@Override
	public Messanger getMessanger() {
		return this.messanger;
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#getLevelManager()
	 */
	@Override
	public LevelManager getLevelManager() {
		return this.levelManager;
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#getOptions()
	 */
	@Override
	public GameOptions getOptions() {
		return this.options;
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#getLocationManager()
	 */
	@Override
	public GunGameMap getLocationManager() {
		return this.locationManager;
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#getInterface()
	 */
	@Override
	public GameInterface getInterface() {
		return this.gameInterface;
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#getWinner()
	 */
	@Override
	public GunGamePlayer getWinner() {
		return this.winner;
	}
	
	/* (non-Javadoc)
	 * @see eu.securebit.gungame.game.GunGame#getChecks()
	 */
	@Override
	public List<GameCheck> getChecks() {
		return Collections.unmodifiableList(this.checks);
	}
	
}