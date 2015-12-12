package eu.securebit.gungame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lib.securebit.InfoLayout;
import lib.securebit.command.BasicCommand;
import lib.securebit.command.LayoutCommandSettings;
import lib.securebit.game.GameStateManager;
import lib.securebit.listener.ListenerHandler;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import eu.securebit.gungame.commands.CommandGunGame;
import eu.securebit.gungame.game.states.DisabledStateEdit;
import eu.securebit.gungame.game.states.GameStateEnd;
import eu.securebit.gungame.game.states.GameStateGrace;
import eu.securebit.gungame.game.states.GameStateIngame;
import eu.securebit.gungame.game.states.GameStateLobby;
import eu.securebit.gungame.io.LevelConfig;
import eu.securebit.gungame.io.MainConfig;
import eu.securebit.gungame.io.flatfile.FileConfig;
import eu.securebit.gungame.io.flatfile.FileLevels;
import eu.securebit.gungame.listeners.ListenerBlockBreak;
import eu.securebit.gungame.listeners.ListenerBlockBurn;
import eu.securebit.gungame.listeners.ListenerBlockIgnite;
import eu.securebit.gungame.listeners.ListenerBlockPlace;
import eu.securebit.gungame.listeners.ListenerEntityDamage;
import eu.securebit.gungame.listeners.ListenerEntityDamageByBlock;
import eu.securebit.gungame.listeners.ListenerEntityDamageByEntity;
import eu.securebit.gungame.listeners.ListenerExpChange;
import eu.securebit.gungame.listeners.ListenerFoodLevelChange;
import eu.securebit.gungame.listeners.ListenerGameModeChange;
import eu.securebit.gungame.listeners.ListenerPlayerAchievementAwarded;
import eu.securebit.gungame.listeners.ListenerPlayerDeath;
import eu.securebit.gungame.listeners.ListenerPlayerDropItem;
import eu.securebit.gungame.listeners.ListenerPlayerJoin;
import eu.securebit.gungame.listeners.ListenerPlayerLogin;
import eu.securebit.gungame.listeners.ListenerPlayerPickupItem;
import eu.securebit.gungame.listeners.ListenerPlayerQuit;
import eu.securebit.gungame.listeners.ListenerPlayerRespawn;
import eu.securebit.gungame.test.CommandTest;

public class Main extends JavaPlugin {
	
	public static final boolean DEBUG = false; // Switch debug & release mode
	
	private static Main instance;
	private static InfoLayout layout;
	private static Random random;
	
	public static Main instance() {
		return Main.instance;
	}
	
	public static InfoLayout layout() {
		return Main.layout;
	}
	
	public static Random random() {
		return Main.random;
	}
	
	public static void resetPlayer(Player player) {
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
	
	public static void broadcast(String msg) {
		if (msg != null) {
			Bukkit.broadcastMessage(msg);
		}
	}
	
	// ---------- Members ---------- //
	private MainConfig fileConfig;
	private LevelConfig fileLevels;
	
	private GameStateManager manager;
	
	private ListenerHandler listenerHandler;
	
	@Override
	public void onLoad() {
		Main.instance = this;
		Main.layout = new InfoLayout("System-GunGame");
		Main.random = new Random();
	}
	
	@Override
	public void onEnable() {
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		Main.layout.begin();
		Main.layout.barrier();
		Main.layout.line("Starting plugin...");
		Main.layout.commit(sender);
		
		this.loadFiles();
		Main.layout.message(sender, "Files loaded!");
		
		this.listenerHandler = new ListenerHandler();
		this.listenerHandler.add(new ListenerExpChange());
		this.listenerHandler.add(new ListenerBlockBurn());
		this.listenerHandler.add(new ListenerBlockBreak());
		this.listenerHandler.add(new ListenerBlockPlace());
		this.listenerHandler.add(new ListenerPlayerJoin());
		this.listenerHandler.add(new ListenerPlayerQuit());
		this.listenerHandler.add(new ListenerBlockIgnite());
		this.listenerHandler.add(new ListenerPlayerLogin());
		this.listenerHandler.add(new ListenerPlayerDeath());
		this.listenerHandler.add(new ListenerEntityDamage());
		this.listenerHandler.add(new ListenerPlayerRespawn());
		this.listenerHandler.add(new ListenerPlayerDropItem());
		this.listenerHandler.add(new ListenerGameModeChange());
		this.listenerHandler.add(new ListenerFoodLevelChange());
		this.listenerHandler.add(new ListenerPlayerPickupItem());
		this.listenerHandler.add(new ListenerEntityDamageByBlock());
		this.listenerHandler.add(new ListenerEntityDamageByEntity());
		this.listenerHandler.add(new ListenerPlayerAchievementAwarded());
		this.listenerHandler.create();
		this.listenerHandler.register("bundle.all");
		Main.layout.message(sender, "Listener registered!");
		
		this.manager = new GameStateManager();
		this.manager.addState(new GameStateLobby());
		this.manager.addState(new GameStateGrace());
		this.manager.addState(new GameStateIngame());
		this.manager.addState(new GameStateEnd());
		this.manager.initGame(new GunGame());
		this.manager.setDisabledState(new DisabledStateEdit());
		this.manager.create();
		Main.layout.message(sender, "Game initilized!");
		
		if (this.fileConfig.isScoreboard()) {
			if (GunGameScoreboard.exists()) {
				GunGameScoreboard.delete();
			}
		}
		
		if (this.fileConfig.isScoreboard()) {
			GunGameScoreboard.create();
		}
		
		{
			BasicCommand cmd = new CommandGunGame();
			cmd.create();
		}
		
		if (DEBUG) {
			BasicCommand cmd = new BasicCommand("ggtest", new LayoutCommandSettings(Main.layout), this);
			cmd.setDefaultExecutor(new CommandTest(cmd));
			cmd.create();
		}
		
		Main.layout.begin();
		Main.layout.barrier();
		Main.layout.commit(sender);
		
		if (this.manager.getGame().isReady()) {
			Main.layout.message(sender, "*Game will be started!*");
			Main.layout.message(sender, "Configuring wolrds..");
			
			List<World> worlds = new ArrayList<>();
			
			for (Location spawn : this.fileConfig.getSpawns()) {
				if (!worlds.contains(spawn.getWorld())) {
					spawn.getWorld().setAutoSave(false);
					worlds.add(spawn.getWorld());
				}
			}
			
			String strWorlds = "'";
			
			for (int i = 0; i < worlds.size(); i++) {
				strWorlds = strWorlds + worlds.get(i).getName() + (i == (worlds.size() - 1) ? "'!" : "', '");
			}
			
			Main.layout.message(sender, "Turned AutoSave to OFF for the worlds: " + strWorlds);
			Main.layout.message(sender, "+Server is ready! Starting game...+");
			Main.layout.begin();
			Main.layout.barrier();
			Main.layout.commit(sender);
			
			this.manager.setRunning(true);
		} else {
			Main.layout.message(sender, "+EditMode enabled! Check+ */gungame info* +out, to finish the setup!+");
			Main.layout.begin();
			Main.layout.barrier();
			Main.layout.commit(sender);
		}
		
		Main.layout.message(sender, "");
		Main.layout.message(sender, "Printing help:");
		
		Main.layout.begin();
		Information.generalInfo(layout);
		Main.layout.barrier();
		Main.layout.commit(sender);
	}
	
	@Override
	public void onDisable() {
		if (this.fileConfig.isScoreboard()) {
			GunGameScoreboard.delete();
		}
	}
	
	public MainConfig getFileConfig() {
		return this.fileConfig;
	}
	
	public LevelConfig getFileLevels() {
		return this.fileLevels;
	}
	
	public GameStateManager getGameStateManager() {
		return this.manager;
	}
	
	public ListenerHandler getListenerHandler() {
		return this.listenerHandler;
	}
	
	public GunGame getGame() {
		return (GunGame) this.manager.getGame();
	}
	
	public void loadFiles() {
		this.fileLevels = new FileLevels();
		this.fileLevels.load();
		
		this.fileConfig = new FileConfig();
		this.fileConfig.load();
	}
	
}
