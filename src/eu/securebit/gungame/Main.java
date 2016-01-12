package eu.securebit.gungame;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import lib.securebit.InfoLayout;
import lib.securebit.command.BasicCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import eu.securebit.gungame.commands.CommandGunGame;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.framework.Frame.FrameProperties;
import eu.securebit.gungame.io.ConfigError;
import eu.securebit.gungame.io.FileBootConfig;
import eu.securebit.gungame.io.FileConfigRegistry;
import eu.securebit.gungame.io.FrameLoader;
import eu.securebit.gungame.io.impl.CraftFileBootConfig;
import eu.securebit.gungame.io.impl.CraftFileConfigRegistry;
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
	
	private FileConfigRegistry fileRegistry;
	
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
		
		this.fileRegistry = new CraftFileConfigRegistry();
		
		for (String element : this.fileRegistry.getEntry()) {
			if (!new File(element.split(":")[0]).exists()) {
				this.fileRegistry.remove(element);
			}
		}
		
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
		
		boolean frameEnabled = false;
		
		enableFrame: {
			if (this.frame != null) {
				int id = this.frame.getFrameId();
				
				File fileBootData = new File(config.getBootFolder(), ".bootdata.yml");
				
				if (fileBootData.exists()) {
					if (fileBootData.isDirectory()) {
						Main.layout.message(sender, "§4Error while loading bootfolder: '.bootdata.yml' is a directory! Delete it to fix this error!");
						break enableFrame;
					}
				} else {
					try {
						fileBootData.createNewFile();
					} catch (IOException e) {
						if (Main.DEBUG) {
							e.printStackTrace();
						}
						
						Main.layout.message(sender, "§4Error while creating '.bootdata.yml' in the bootfolder: " + e.getMessage());
						break enableFrame;
					}
				}
				
				try {
					FileConfiguration bootDataConfig = YamlConfiguration.loadConfiguration(fileBootData);
					
					if (!bootDataConfig.contains("id") || !bootDataConfig.isInt("id")) {
						bootDataConfig.set("id", id);
						bootDataConfig.save(fileBootData);
					}
					
					if (bootDataConfig.getInt("id") != id) {
						Main.layout.message(sender, "§4Error while setting up bootfolder: The folder is already in use by another frametype!");
						break enableFrame;
					}
				} catch (Exception ex) {
					if (Main.DEBUG) {
						ex.printStackTrace();
					}
					
					Main.layout.message(sender, "§4Error while setting up bootfolder: " + ex.getMessage());
					break enableFrame;
				}
				
				Main.layout.message(sender, "Enabling frame...");
				Main.layout.message(sender, "§8================");
				
				FrameProperties properties = new FrameProperties(config.getBootFolder());
				
				try {
					this.frame.enable(properties);
					frameEnabled = true;
				} catch (Exception ex) {
					if (Main.DEBUG) {
						ex.printStackTrace();;
					}
					
					Main.layout.message(sender, "§4Error while enabling frame: " + ex.getMessage());
				}
				
				Main.layout.message(sender, "§8================");
			}
		}
		
		if (frameEnabled) {
			String name = InfoLayout.replaceKeys(this.frame.getName());
			String version = InfoLayout.replaceKeys(this.frame.getVersion());
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
	
	public FileConfigRegistry getFileRegistry() {
		return this.fileRegistry;
	}
	
}