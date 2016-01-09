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
import eu.securebit.gungame.io.FileBootConfig;
import eu.securebit.gungame.io.FrameLoader;
import eu.securebit.gungame.io.impl.CraftFileBootConfig;
import eu.securebit.gungame.io.impl.CraftFrameLoader;

public class Main extends JavaPlugin {
	
	public static final boolean DEBUG = true; // Switch debug & release mode
	
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
		
		FileBootConfig config = new CraftFileBootConfig(this);
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
			
			try {
				this.frame.enable(properties);
			} catch (Exception ex) {
				if (Main.DEBUG) {
					ex.getMessage();
				}
				
				Main.layout.message(sender, "§4Error while enabling frame: " + ex.getMessage());
				Main.layout.message(sender, "§4WARNING: Could not finish enabling of the frame! May causes bugs! ");
			}
			
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
			try {
				this.frame.disable();
			} catch (Exception ex) {
				if (Main.DEBUG) {
					ex.printStackTrace();
				}
				
				Main.layout.message(Bukkit.getConsoleSender(), "§4Error while disabling frame: " + ex.getMessage());
			}
			
		}
	}
	
	public Frame getFrame() {
		return this.frame;
	}
	
	public BasicCommand getCommand() {
		return this.command;
	}
	
}