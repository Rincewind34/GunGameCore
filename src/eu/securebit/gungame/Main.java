package eu.securebit.gungame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lib.securebit.InfoLayout;
import lib.securebit.command.ArgumentedCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import eu.securebit.gungame.addonsystem.Addon;
import eu.securebit.gungame.addonsystem.Addon.AddonProperties;
import eu.securebit.gungame.commands.CommandGunGame;
import eu.securebit.gungame.errors.ErrorHandler;
import eu.securebit.gungame.exception.MalformedJarException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.framework.Frame.FrameProperties;
import eu.securebit.gungame.io.directories.BootDirectory;
import eu.securebit.gungame.io.directories.RootDirectory;
import eu.securebit.gungame.io.loader.AddonLoader;
import eu.securebit.gungame.io.loader.FrameLoader;
import eu.securebit.gungame.ioimpl.directories.CraftRootDirectory;
import eu.securebit.gungame.ioimpl.loader.CraftAddonLoader;
import eu.securebit.gungame.ioimpl.loader.CraftFrameLoader;

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
	private List<Addon> addons;
	
	private ArgumentedCommand command;
	
	private ErrorHandler handler;
	
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
		
		this.handler = new ErrorHandler();
		
		this.command = new CommandGunGame();
		this.command.create();
		
		Main.layout.message(sender, "Loading files...");
		this.rootDirectory = new CraftRootDirectory(new File("plugins" + File.separator + "GunGame"), this.handler);
		this.rootDirectory.create();
		
		if (this.rootDirectory.isReady()) {
			Main.layout.message(sender, "Files loaded!");
		} else {
			Main.layout.message(sender, "Could not load all files!");
		}
		
		this.rootDirectory.resolveColorSet();
		
		Main.layout.message(sender, "");
		Main.layout.message(sender, "Loading frame...");
		
		if (this.loadFrame(sender)) {
			Main.layout.message(sender, "Frame loaded!");
		} else {
			Main.layout.message(sender, "Frame could not be loaded!");
		}
		
		Main.layout.message(sender, "");
		Main.layout.message(sender, "Enabling frame...");
		
		if (this.enableFrame(sender)) {
			String name = InfoLayout.replaceKeys(this.frame.getName());
			String version = InfoLayout.replaceKeys(this.frame.getVersion());
			
			Main.layout.message(sender, "+Frame '+*" + name + "*+' version '+*" + InfoLayout.replaceKeys(version) + "*+' enabled!+");
		} else {
			Main.layout.message(sender, "Frame could not be enabled!");
		}
		
		Main.layout.message(sender, "");
		
		this.manageAddons(sender);
		
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
					
					this.printError(ex, "disabling addon");
					continue;
				}
				
				Main.layout.message(sender, "Addon disabled!");
			}
		}
		
		if (Core.isFrameEnabled()) {
			Main.layout.message(sender, "Disabling frame...");
			
			try {
				this.frame.disable();
			} catch (Throwable ex) {
				if (Main.DEBUG) {
					ex.printStackTrace();
				}
				
				this.printError(ex, "disabling frame");
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
	
	public ErrorHandler getErrorHandler() {
		return this.handler;
	}
	
	public RootDirectory getRootDirectory() {
		return this.rootDirectory;
	}
	
	private void printError(Throwable ex, String when) {
		Main.layout.message(Bukkit.getConsoleSender(), "-Error while " + when + " (" + ex.getClass().getSimpleName() + "): " + 
				InfoLayout.replaceKeys(ex.getMessage() != null ? ex.getMessage() : ">>NULL<<") + "-");
	}
	
	private boolean loadFrame(ConsoleCommandSender sender) {
		if (this.rootDirectory.isFramePresent()) {
			try {
				FrameLoader loader = new CraftFrameLoader(this.rootDirectory.getFrameJar());
				this.frame = loader.load();
			} catch (MalformedJarException ex) {
				if (Main.DEBUG) {
					ex.printStackTrace();
				}
				
				this.frame = null;
				this.handler.throwError(Frame.ERROR_LOAD_MAINCLASS);
			} catch (Throwable ex) {
				if (Main.DEBUG) {
					ex.printStackTrace();
				}
				
				this.frame = null;
				this.handler.throwError(Frame.ERROR_LOAD);
			}
		} else {
			this.handler.throwError(Frame.ERROR_LOAD, RootDirectory.ERROR_FRAME);
		}
		
		return Core.isFrameLoaded();
	}
	
	private boolean enableFrame(ConsoleCommandSender sender) {
		if (Core.isFrameLoaded()) {
			if (this.rootDirectory.getBootFolder().isReady()) {
				if (this.rootDirectory.getBootFolder().getUsingFrameId() == 0) {
					this.rootDirectory.getBootFolder().setUsingFrameId(this.frame.getFrameId());
				}
				
				if (this.rootDirectory.getBootFolder().getUsingFrameId() == this.frame.getFrameId()) {
					Main.layout.message(sender, "§8================================");
					
					FrameProperties properties = new FrameProperties(new File(this.rootDirectory.getBootFolder().getRelativPath()));
					
					try {
						this.frame.enable(properties);
					} catch (Throwable ex) {
						if (Main.DEBUG) {
							ex.printStackTrace();
						}
						
						this.handler.throwError(Frame.ERROR_ENABLE);
					}
					
					Main.layout.message(sender, "§8================================");
				} else {
					this.handler.throwError(Frame.ERROR_ENABLE_ID);
				}
			} else {
				this.handler.throwError(Frame.ERROR_ENABLE, BootDirectory.ERROR_MAIN);
			}
		} else {
			this.handler.throwError(Frame.ERROR_ENABLE, Frame.ERROR_LOAD);
		}
		
		return Core.isFrameEnabled();
	}
	
	private void manageAddons(ConsoleCommandSender sender) { // TODO Addon.ERROR_INIT
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
						
						this.printError(ex, "loading addon");
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
						
						if (addon.getIncompatibleFrames().contains(Integer.MAX_VALUE) && !Core.isFrameEnabled()) {
							Main.layout.message(sender, "-The addon -'*" + addon.getName() + "*'- cannot be enabled without a frame! => SKIPPING-");
							misses = misses + 1;
							continue addons;
						} else if (this.frame != null && addon.getIncompatibleFrames().contains(this.frame.getFrameId())) {
							Main.layout.message(sender, "-The currently loaded frame is incompatible with the addon -'*" + addon.getName() + "*'-! => SKIPPING-");
							misses = misses + 1;
							continue addons;
						}
						
						Main.layout.message(sender, "Enabling addon '*" + InfoLayout.replaceKeys(addon.getName()) + "*' version '*" +
								InfoLayout.replaceKeys(addon.getVersion()) + "*'...");
						
						AddonProperties properties = new AddonProperties(new File(this.rootDirectory.getBootFolder().getRelativPath()));
						
						try {
							addon.enable(properties);
							enabledAddons.add(addon);
						} catch (Throwable ex) {
							misses = misses + 1;
							
							if (Main.DEBUG) {
								ex.printStackTrace();
							}
							
							this.printError(ex, "enabling addon");
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
	}
	
}