package eu.securebit.gungame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lib.securebit.InfoLayout;
import lib.securebit.command.BasicCommand;
import lib.securebit.command.LayoutCommandSettings;
import lib.securebit.game.GameStateManager;
import lib.securebit.game.impl.CraftGameStateManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
import eu.securebit.gungame.listeners.ListenerPlayerJoin;
import eu.securebit.gungame.listeners.ListenerPlayerQuit;
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
	
	public static void broadcast(String msg) {
		if (msg != null) {
			Bukkit.broadcastMessage(msg);
		}
	}
	
	// ---------- Members ---------- //
	private MainConfig fileConfig;
	private LevelConfig fileLevels;
	
	private GameStateManager<GunGame> manager;
	
	@Override
	public void onLoad() {
		Main.instance = this;
		Main.layout = new InfoLayout("System");
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
		
		this.getServer().getPluginManager().registerEvents(new ListenerPlayerJoin(), this);
		this.getServer().getPluginManager().registerEvents(new ListenerPlayerQuit(), this);
		
		this.manager = new CraftGameStateManager<>(this);
		this.manager.initGame(new GunGame());
		this.manager.add(new GameStateLobby());
		this.manager.add(new GameStateGrace());
		this.manager.add(new GameStateIngame());
		this.manager.add(new GameStateEnd());
		this.manager.initDisabledState(new DisabledStateEdit());
		Main.layout.message(sender, "Game initilized!");
		
		if (this.fileConfig.isScoreboard()) {
			if (GunGameScoreboard.exists()) {
				GunGameScoreboard.delete();
			}
			
			GunGameScoreboard.create();
		}
		
		new CommandGunGame().create();
		
		if (DEBUG) {
			BasicCommand cmd = new BasicCommand("ggtest", new LayoutCommandSettings(Main.layout), this);
			cmd.setDefaultExecutor(new CommandTest(cmd));
			cmd.create();
		}
		
		Main.layout.begin();
		Main.layout.barrier();
		Main.layout.commit(sender);
		
		if (this.manager.getGame().isReady()) {
			Main.layout.message(sender, "Configuring wolrds..");
			
			List<World> worlds = new ArrayList<>();
			
			for (Location spawn : this.fileConfig.getSpawns()) {
				if (!worlds.contains(spawn.getWorld())) {
					spawn.getWorld().setAutoSave(false);
					worlds.add(spawn.getWorld());
					this.getGame().registerWorld(spawn.getWorld());
				}
			}
			
			String strWorlds = "'";
			
			for (int i = 0; i < worlds.size(); i++) {
				strWorlds = strWorlds + worlds.get(i).getName() + (i == (worlds.size() - 1) ? "'!" : "', '");
			}
			
			Main.layout.message(sender, "Turned AutoSave to OFF for the worlds: " + strWorlds);
			Main.layout.message(sender, "+Server is ready! Starting game...+");
			
			this.manager.create(true);
		} else {
			this.manager.create(false);
			Main.layout.message(sender, "+EditMode enabled! Check+ */gungame info* +out, to finish the setup!+");
		}
		
		for (Player player : Bukkit.getOnlinePlayers()) { // TODO onlineplayers!!!
			this.manager.getGame().joinPlayer(new GunGamePlayer(player));
		}
		
		Main.layout.begin();
		Main.layout.barrier();
		Main.layout.line("");
		Main.layout.line("Printing help:");
		
		Util.stageInformation(Main.layout);
		
		Main.layout.commit(sender);
	}
	
	@Override
	public void onDisable() {
		if (this.fileConfig.isScoreboard()) {
			if (GunGameScoreboard.exists()) {
				GunGameScoreboard.delete();
			}
		}
	}
	
	public MainConfig getFileConfig() {
		return this.fileConfig;
	}
	
	public LevelConfig getFileLevels() {
		return this.fileLevels;
	}
	
	public GameStateManager<GunGame> getGameStateManager() {
		return this.manager;
	}
	
	public GunGame getGame() {
		return this.manager.getGame();
	}
	
	public void loadFiles() {
		this.fileLevels = new FileLevels();
		this.fileLevels.load();
		
		this.fileConfig = new FileConfig();
		this.fileConfig.load();
	}
	
}