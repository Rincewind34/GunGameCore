package eu.securebit.gungame;

import java.io.File;
import java.util.Random;

import lib.securebit.InfoLayout;
import lib.securebit.command.ArgumentedCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import eu.securebit.gungame.commands.CommandGunGame;
import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.io.directories.AddonDirectory;
import eu.securebit.gungame.io.directories.BootDirectory;
import eu.securebit.gungame.io.directories.RootDirectory;
import eu.securebit.gungame.ioimpl.directories.CraftRootDirectory;

public class Main extends JavaPlugin {
	
	public static final String PREFIX_CORE = "System";
	
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
	
	private Session session;
	
	private ArgumentedCommand command;
	
	private CraftErrorHandler handler;
	
	private RootDirectory rootDirectory;
	
	@Override
	public void onLoad() {
		Main.instance = this;
		Main.layout = new InfoLayout(Main.PREFIX_CORE);
		Main.random = new Random();
	}
	
	@Override
	public void onEnable() {
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		Main.layout.message(sender, "Starting core...");
		Main.layout.message(sender, "Running version: " + InfoLayout.replaceKeys(this.getDescription().getVersion()));
		Main.layout.message(sender, "");
		
		this.handler = new CraftErrorHandler();
		
		this.command = new CommandGunGame();
		this.command.create();
		
		Main.layout.message(sender, "Creating filestructure...");
		this.rootDirectory = new CraftRootDirectory(new File("plugins" + File.separator + "GunGame"), this.handler);
		this.rootDirectory.create();
		
		if (this.rootDirectory.isReady()) {
			Main.layout.message(sender, "Created!");
		} else {
			Main.layout.message(sender, "Could not create the filestructure!");
		}
		
		Main.layout.message(sender, "");
		
		this.rootDirectory.resolveColorSet();
		
		Main.layout.message(sender, "Creating session...");
		this.createSession();
	
		Main.layout.message(sender, "Booting...");
		Main.layout.message(sender, "");
		this.session.boot();
		
		Main.layout.message(sender, "");
		Main.layout.message(sender, "+Core initialized!+");
	}
	
	@Override
	public void onDisable() {
		this.session.shutdown();
		
		Main.instance = null;
	}
	
	public ArgumentedCommand getCommand() {
		return this.command;
	}
	
	public CraftErrorHandler getErrorHandler() {
		return this.handler;
	}
	
	public RootDirectory getRootDirectory() {
		return this.rootDirectory;
	}
	
	public Session getSession() {
		return this.session;
	}
	
	private void createSession() {
		BootDirectory bootDir = this.rootDirectory.getBootFolder();
		AddonDirectory addonDir = this.rootDirectory.getAddonDirectory();
		
		boolean debug = this.rootDirectory.isDebugMode();
		
		if (this.rootDirectory.isFramePresent()) {
			if (addonDir.isReady()) {
				this.session = new CraftSession(this.rootDirectory.getFrameJar(), addonDir.getAddonFiles(), bootDir, this.handler, debug);
			} else {
				this.session = new CraftSession(this.rootDirectory.getFrameJar(), new ThrownError(AddonDirectory.ERROR_MAIN), bootDir, this.handler, debug);
			}
		} else {
			ThrownError error = new ThrownError(RootDirectory.ERROR_FRAME);
			
			if (addonDir.isReady()) {
				this.session = new CraftSession(error, addonDir.getAddonFiles(), bootDir, this.handler, debug);
			} else {
				this.session = new CraftSession(error, new ThrownError(AddonDirectory.ERROR_MAIN), bootDir, this.handler, debug);
			}
		}
	}
	
}