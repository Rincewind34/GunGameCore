package eu.securebit.gungame;

import java.io.IOException;
import java.util.Random;

import lib.securebit.InfoLayout;
import lib.securebit.command.BasicCommand;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import eu.securebit.gungame.Frame.FrameProperties;
import eu.securebit.gungame.commands.CommandGunGame;
import eu.securebit.gungame.exception.MalformedFrameException;
import eu.securebit.gungame.io.CraftFileConfig;
import eu.securebit.gungame.io.CraftFrameLoader;
import eu.securebit.gungame.io.FileConfig;
import eu.securebit.gungame.io.FrameLoader;

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
		this.command = new CommandGunGame();
		this.command.create();
		
		FileConfig config = new CraftFileConfig(this);
		config.initialize();
		
		boolean shutdown = false;
		if (config.isValid()) {
			try {
				FrameProperties properties = new FrameProperties(config.getBootFolder());
				
				FrameLoader loader = new CraftFrameLoader(config.getFrameJar());
				this.frame = loader.load();
				this.frame.enable(properties);
				// At this point, the concrete frame gets control about GunGame
			} catch (MalformedFrameException e) {
				e.printStackTrace();
				shutdown = true;
			} catch (IOException e) {
				e.printStackTrace();
				shutdown = true;
			} catch (Exception e) {
				e.printStackTrace();
				shutdown = true;
			}
		} else {
			shutdown = true;
		}
		
		if (shutdown) {
			Main.layout.message(Bukkit.getConsoleSender(), "§4Please insert a frame and specify a valid bootfolder!");
			Main.layout.message(Bukkit.getConsoleSender(), "§4The server goes to sleep!");
			Bukkit.shutdown();
		}
	}
	
	@Override
	public void onDisable() {
		if (this.frame != null) {
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