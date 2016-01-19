package eu.securebit.gungame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lib.securebit.InfoLayout;
import lib.securebit.command.ArgumentedCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import eu.securebit.gungame.addonsystem.Addon;
import eu.securebit.gungame.addonsystem.Addon.AddonProperties;
import eu.securebit.gungame.commands.CommandGunGame;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.framework.Frame.FrameProperties;
import eu.securebit.gungame.io.AddonLoader;
import eu.securebit.gungame.io.ConfigError;
import eu.securebit.gungame.io.FileBootConfig;
import eu.securebit.gungame.io.FileConfigRegistry;
import eu.securebit.gungame.io.FrameLoader;
import eu.securebit.gungame.io.impl.CraftAddonLoader;
import eu.securebit.gungame.io.impl.CraftFileBootConfig;
import eu.securebit.gungame.io.impl.CraftFileConfigRegistry;
import eu.securebit.gungame.io.impl.CraftFrameLoader;

public class Main extends JavaPlugin {
	
	public static final boolean DEBUG = true; // Switch debug & release mode
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
	
	private Frame frame;
	private ArgumentedCommand command;
	
	private FileConfigRegistry fileRegistry;
	private FileBootConfig fileBootConfig;
	
	private List<Addon> addons;
	
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
		
		this.fileBootConfig = new CraftFileBootConfig(this);
		this.fileBootConfig.initialize();
		
		ConfigError[] errors = this.fileBootConfig.validate();
		
		for (int i = 0; i < errors.length; i++) {
			Main.layout.message(sender, "§4Error: " + InfoLayout.replaceKeys(errors[i].getDescription()));
		}
		
		if (errors.length > 0) {
			Main.layout.message(sender, "-=> " + errors.length + " errors-");
			Main.layout.message(sender, "-The server goes to sleep!-");
			Bukkit.shutdown();
			return;
		}
		
		Main.layout.message(sender, "Resolving colorset...");
		this.fileBootConfig.getColorSet().prepare(Main.layout);
		
		Main.layout.message(sender, "Loading frame...");
		
		File fileBootData = new File(this.fileBootConfig.getBootFolder(), ".bootdata.yml");
		
		try {
			FrameLoader loader = new CraftFrameLoader(this.fileBootConfig.getFrameJar());
			this.frame = loader.load();
		} catch (Throwable ex) {
			if (Main.DEBUG) {
				ex.printStackTrace();
			}
			
			this.frame = null;
			
			Main.layout.message(sender, "-Error while loading frame: " + InfoLayout.replaceKeys(ex.getMessage() != null ? ex.getMessage() : ">>NULL<<") + "-");
		}
		
		boolean frameEnabled = false;
		
		enableFrame: {
			if (this.frame != null) {
				int id = this.frame.getFrameId();
				
				if (fileBootData.exists()) {
					if (fileBootData.isDirectory()) {
						Main.layout.message(sender, "-Error while loading bootfolder: '.bootdata.yml' is a directory! Delete it to fix this error!-");
						break enableFrame;
					}
				} else {
					try {
						fileBootData.createNewFile();
					} catch (IOException e) {
						if (Main.DEBUG) {
							e.printStackTrace();
						}
						
						Main.layout.message(sender, "-Error while creating '.bootdata.yml' in the bootfolder: " + InfoLayout.replaceKeys(e.getMessage()) + "-");
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
						Main.layout.message(sender, "-Error while setting up bootfolder: The folder is already in use by another frametype!-");
						break enableFrame;
					}
				} catch (Throwable ex) {
					if (Main.DEBUG) {
						ex.printStackTrace();
					}
					
					Main.layout.message(sender, "-Error while setting up bootfolder: " + InfoLayout.replaceKeys(ex.getMessage() != null ? ex.getMessage() : ">>NULL<<") + "-");
					break enableFrame;
				}
				
				Main.layout.message(sender, "Enabling frame...");
				Main.layout.message(sender, "§8================");
				
				FrameProperties properties = new FrameProperties(this.fileBootConfig.getBootFolder());
				
				try {
					this.frame.enable(properties);
					frameEnabled = true;
				} catch (Throwable ex) {
					if (Main.DEBUG) {
						ex.printStackTrace();
					}
					
					Main.layout.message(sender, "-Error while enabling frame: " + InfoLayout.replaceKeys(ex.getMessage() != null ? ex.getMessage() : ">>NULL<<") + "-");
				}
				
				Main.layout.message(sender, "§8================");
			}
		}
		
		if (frameEnabled) {
			String name = InfoLayout.replaceKeys(this.frame.getName());
			String version = InfoLayout.replaceKeys(this.frame.getVersion());
			Main.layout.message(sender, "+Frame '+*" + name + "*+' version '+*" + InfoLayout.replaceKeys(version) + "*+' enabled!+");
		} else {
			Main.layout.message(sender, "-WARNING: No frame enabled!!!-");
		}
		
		Main.layout.message(sender, "");
		
		File addonDir = new File(this.getDataFolder(), "addons");
		
		if (addonDir.exists() && !addonDir.isDirectory()) {
			Main.layout.message(sender, "-ERROR: The addondirectory is a file (" + InfoLayout.replaceKeys(addonDir.getPath()) + ")!-");
		} else {
			addonDir.mkdirs();
		}
		
		if (addonDir.isDirectory()) {
			if (addonDir.listFiles().length > 0) {
				Main.layout.message(sender, addonDir.listFiles().length + " addons found! Loading them...");
				
				List<Addon> addons = new ArrayList<>();
				int misses = 0;
				
				for (File file : addonDir.listFiles()) {
					Main.layout.message(sender, "Loading addon (file: " + InfoLayout.replaceKeys(file.getPath()) + ")!");
					Addon addon = null;
					
					try {
						AddonLoader loader = new CraftAddonLoader(file);
						addon = loader.load();
						addons.add(addon);
					} catch (Throwable ex) {
						misses = misses + 1;
						
						if (Main.DEBUG) {
							ex.printStackTrace();
						}
						
						Main.layout.message(sender, "-Error while loading addon: " + InfoLayout.replaceKeys(ex.getMessage() != null ? ex.getMessage() : ">>NULL<<") + "-");
						continue;
					}
					
					Main.layout.message(sender, "Addon loaded!");
				}
				
				if (misses == 0) {
					Main.layout.message(sender, "+All addons loaded +§8[*" + addons.size() + "*§8/*" + addons.size() + "*§8]+!+");
				} else {
					Main.layout.message(sender, "Could not load all addons §8[*" + addons.size() + "*§8/*" + (addons.size() + misses) + "*§8]§7!");
				}
				
				if (!addons.isEmpty()) {
					Main.layout.message(sender, "Enabling addons...");
					
					List<Addon> enabledAddons = new ArrayList<>();
					misses = 0;
					
					addons: for (Addon addon : addons) {
						for (String plugin : addon.getDependencies()) {
							if (Bukkit.getPluginManager().getPlugin(plugin) == null || !Bukkit.getPluginManager().getPlugin(plugin).isEnabled()) {
								Main.layout.message(sender, "-The plugin '-*" + plugin + "*-' is missing to enable addon '-*" + addon.getName() + "*-'! => SKIPPING-");
								
								misses = misses + 1;
								continue addons;
							}
						}
						
						if (addon.getIncompatibleFrames().contains(Integer.MAX_VALUE) && this.frame == null) {
							Main.layout.message(sender, "-The addon -'*" + addon.getName() + "*'- cannot be enabled without a frame! => SKIPPING-");
							misses = misses + 1;
							continue addons;
						}
						
						if (addon.getIncompatibleFrames().contains(this.frame.getFrameId())) {
							Main.layout.message(sender, "-The currently loaded frame is incompatible with the addon -'*" + addon.getName() + "*'-! => SKIPPING-");
							misses = misses + 1;
							continue addons;
						}
						
						Main.layout.message(sender, "Enabling addon '*" + InfoLayout.replaceKeys(addon.getName()) + "*' version '*" +
								InfoLayout.replaceKeys(addon.getVersion()) + "*'...");
						
						AddonProperties properties = new AddonProperties(this.fileBootConfig.getBootFolder());
						
						try {
							addon.enable(properties);
							enabledAddons.add(addon);
						} catch (Throwable ex) {
							misses = misses + 1;
							
							if (Main.DEBUG) {
								ex.printStackTrace();
							}
							
							Main.layout.message(sender, "-Error while enabling addon: " + InfoLayout.replaceKeys(ex.getMessage() != null ? ex.getMessage() : ">>NULL<<") + "-");
							continue;
						}
						
						Main.layout.message(sender, "Addon enabled!");
					}
					
					if (misses == 0) {
						Main.layout.message(sender, "+All addons enabled +§8[*" + addons.size() + "*§8/*" + addons.size() + "*§8]+!+");
					} else {
						Main.layout.message(sender, "Could not enable all addons §8[*" + (addons.size() - misses) + "*§8/*" + addons.size() + "*§8]§7!");
					}
					
					this.addons = enabledAddons;
				}
			} else {
				Main.layout.message(sender, "No addon found!");
			}
		}
		
		Main.layout.message(sender, "+Core initialized!+");
	}
	
	@Override
	public void onDisable() {
		ConsoleCommandSender sender = Bukkit.getConsoleSender();
		
		if (this.addons != null) {
			for (Addon addon : this.addons) {
				Main.layout.message(sender, "Disabling addon '" + InfoLayout.replaceKeys(addon.getName()) + "'...");
				
				try {
					addon.disable();
				} catch (Throwable ex) {
					if (Main.DEBUG) {
						ex.printStackTrace();
					}
					
					Main.layout.message(sender, "-Error while disabling addon: " + InfoLayout.replaceKeys(ex.getMessage() != null ? ex.getMessage() : ">>NULL<<") + "-");
					continue;
				}
				
				Main.layout.message(sender, "Addon disabled!");
			}
		}
		
		if (this.frame != null) {
			Main.layout.message(sender, "Disabling frame...");
			try {
				this.frame.disable();
			} catch (Throwable ex) {
				if (Main.DEBUG) {
					ex.printStackTrace();
				}
				
				Main.layout.message(sender, "-Error while disabling frame: " + InfoLayout.replaceKeys(ex.getMessage() != null ? ex.getMessage() : ">>NULL<<") + "-");
			}
			
			Main.layout.message(sender, "Frame disabled!");
		}
	}
	
	public Frame getFrame() {
		return this.frame;
	}
	
	public ArgumentedCommand getCommand() {
		return this.command;
	}
	
	public FileConfigRegistry getFileRegistry() {
		return this.fileRegistry;
	}
	
	public FileBootConfig getFileBootConfig() {
		return this.fileBootConfig;
	}
	
}