package eu.securebit.gungame;

import java.util.Random;

import lib.securebit.InfoLayout;
import lib.securebit.command.BasicCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import eu.securebit.gungame.commands.CommandGunGame;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.framework.Frame.FrameProperties;
import eu.securebit.gungame.io.ConfigError;
import eu.securebit.gungame.io.CraftFileConfig;
import eu.securebit.gungame.io.CraftFrameLoader;
import eu.securebit.gungame.io.FileConfig;
import eu.securebit.gungame.io.FrameLoader;

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
	
	private Frame frame;
	private BasicCommand command;
	
	@Override
	public void onLoad() {
		Main.instance = this;
		Main.layout = new InfoLayout("System");
		Main.random = new Random();
	}
	
	@Override
	public void onEnable() {
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		Main.layout.message(sender, "Starting core...");
		Main.layout.message(sender, "");
		Main.layout.message(sender, "Running version: " + InfoLayout.replaceKeys(this.getDescription().getVersion()));
		Main.layout.message(sender, "");
		
		this.command = new CommandGunGame();
		this.command.create();
		
		FileConfig config = new CraftFileConfig(this);
		config.initialize();
		
		Main.layout.message(sender, "Loading frame...");
		
		ConfigError[] errors = config.validate();
		for (int i = 0; i < errors.length; i++) {
			Main.layout.message(sender, "§4Error: " + InfoLayout.replaceKeys(errors[i].getDescription()));
		}
		
		if (errors.length > 0) {
			Main.layout.message(sender, "§4=> " + errors.length + " errors");
			Main.layout.message(sender, "§4The server goes to sleep!");
			Bukkit.shutdown();
			return;
		}
		
		try {
			FrameLoader loader = new CraftFrameLoader(config.getFrameJar());
			this.frame = loader.load();
		} catch (Exception ex) {
			if (Main.DEBUG) {
				ex.printStackTrace();
			}
			
			this.frame = null;
			
			Main.layout.message(sender, "§4Error while loading frame: " + ex.getMessage());
		}
		
		if (this.frame != null) {
			Main.layout.message(sender, "Enabling frame...");
			Main.layout.message(sender, "§8================");
			
			FrameProperties properties = new FrameProperties(config.getBootFolder());
			
			this.frame.enable(properties);
			
			String name = InfoLayout.replaceKeys(this.frame.getName());
			String version = InfoLayout.replaceKeys(this.frame.getVersion());
			Main.layout.message(sender, "§8================");
			Main.layout.message(sender, "Frame '" + name + "' version '" + version + "' enabled!");
		} else {
			Main.layout.message(sender, "§4WARNING: No frame enabled!!!");
		}
		
		Main.layout.message(sender, "§aPlugin initialized!");
	}
	
	@Override
	public void onDisable() {
		if (this.frame != null) {
			Main.layout.message(Bukkit.getConsoleSender(), "Disabling frame...");
			this.frame.disable();
		}
	}
	
	public Frame getFrame() {
		return this.frame;
	}
	
	public BasicCommand getCommand() {
		return this.command;
	}
	
}