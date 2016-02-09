package eu.securebit.gungame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lib.securebit.InfoLayout;
import lib.securebit.command.ArgumentedCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import eu.securebit.gungame.addonsystem.Addon;
import eu.securebit.gungame.addonsystem.Addon.AddonProperties;
import eu.securebit.gungame.commands.CommandGunGame;
import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.exception.MalformedJarException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.framework.Frame.FrameProperties;
import eu.securebit.gungame.io.directories.AddonDirectory;
import eu.securebit.gungame.io.directories.BootDirectory;
import eu.securebit.gungame.io.directories.RootDirectory;
import eu.securebit.gungame.io.loader.AddonLoader;
import eu.securebit.gungame.io.loader.FrameLoader;
import eu.securebit.gungame.ioimpl.directories.CraftRootDirectory;
import eu.securebit.gungame.ioimpl.loader.CraftAddonLoader;
import eu.securebit.gungame.ioimpl.loader.CraftFrameLoader;
import eu.securebit.gungame.ioutil.IOUtil;

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
		
		Main.layout.message(sender, "Loading files...");
		this.rootDirectory = new CraftRootDirectory(new File("plugins" + File.separator + "GunGame"), this.handler);
		this.rootDirectory.create();
		
		if (this.rootDirectory.isReady()) {
			Main.layout.message(sender, "Files loaded!");
		} else {
			Main.layout.message(sender, "Could not load all files!");
		}
		
		Main.layout.message(sender, "");
		
		this.rootDirectory.resolveColorSet();
		
		Main.layout.message(sender, "Loading frame...");
		
		if (this.loadFrame(sender)) {
			Main.layout.message(sender, "Frame loaded!");
		} else {
			Main.layout.message(sender, "Frame could not be loaded!");
		}
		
		Main.layout.message(sender, "");
		
		String name = InfoLayout.replaceKeys(this.frame.getName());
		String version = InfoLayout.replaceKeys(this.frame.getVersion());
		
		Main.layout.message(sender, "Enabling frame *" + name + "* version *" + version + "*...");
		
		if (this.enableFrame(sender)) {
			Main.layout.message(sender, "+Frame enabled!+");
		} else {
			Main.layout.message(sender, "-Frame could not be enabled!-");
		}
		
		Main.layout.message(sender, "");
		Main.layout.message(sender, "Initializing addonloading...");
		
		List<File> files = this.initAddonLoading(sender);
		
		if (files != null) {
			if (files.size() == 0) {
				Main.layout.message(sender, "No addons found!");
			} else {
				Main.layout.message(sender, "Loadings addons (" + files.size() + ")...");
				
				Map<File, Addon> loadedAddons = new HashMap<>();
				List<Addon> enabledAddons = new ArrayList<>();
				
				for (File file : files) {
					Main.layout.message(sender, "Loading addon (file: " + InfoLayout.replaceKeys(file.getPath()) + ")...");
					
					Addon addon = this.loadAddon(sender, file);
					
					if (addon != null) {
						loadedAddons.put(file, addon);
						Main.layout.message(sender, "Could load addon!");
					} else {
						Main.layout.message(sender, "Addon loaded!");
					}
				}
				
				String status = Main.layout.colorPrimary + "[*" + loadedAddons.size() + "*" + 
						Main.layout.colorPrimary + "/*" + files.size() + "*" + Main.layout.colorPrimary + "]";
				
				if (files.size() == loadedAddons.size()) {
					Main.layout.message(sender, "+All addons loaded+ " + status + "+!+");
				} else {
					Main.layout.message(sender, "-Could not load all addons- " + status  + Main.layout.colorSecondary + "-!-");
				}
				
				Main.layout.message(sender, "");
				Main.layout.message(sender, "Enabling addons (" + loadedAddons.size() + ") ...");
				
				for (File file : loadedAddons.keySet()) {
					Addon addon = loadedAddons.get(file);
					
					Main.layout.message(sender, "Enabling addon *" + InfoLayout.replaceKeys(addon.getName()) + "* version *" +
							InfoLayout.replaceKeys(addon.getVersion()) + "*...");
					
					if (this.enableAddon(sender, addon, file)) {
						enabledAddons.add(addon);
						Main.layout.message(sender, "Addon enabled!");
					} else {
						Main.layout.message(sender, "Could not enable addon!");
					}
				}
				
				status = Main.layout.colorPrimary + "[*" + enabledAddons.size() + "*" + 
						Main.layout.colorPrimary + "/*" + loadedAddons.size() + "*" + Main.layout.colorPrimary + "]";
				
				if (loadedAddons.size() == enabledAddons.size()) {
					Main.layout.message(sender, "+All addons enabled+ " + status + "+!+");
				} else {
					Main.layout.message(sender, "-Could not load all addons- " + status  + Main.layout.colorSecondary + "-!-");
				}
			}
		} else {
			Main.layout.message(sender, "Initialization cancled!");
		}
		
		Main.layout.message(sender, "");
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
					
//					this.printError(ex, "disabling addon"); // TODO TmpError
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
				
//				this.printError(ex, "disabling frame"); // TODO TmpError
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
	
	public CraftErrorHandler getErrorHandler() {
		return this.handler;
	}
	
	public RootDirectory getRootDirectory() {
		return this.rootDirectory;
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
	
	private List<File> initAddonLoading(ConsoleCommandSender sender) {
		AddonDirectory addonDir = this.rootDirectory.getAddonDirectory();
		
		if (addonDir.isReady()) {
			return addonDir.getAddonFiles();
		} else {
			this.handler.throwError(Addon.ERROR_INIT, AddonDirectory.ERROR_MAIN);
		}
		
		return null;
	}
	
	private Addon loadAddon(ConsoleCommandSender sender, File file) {
		try {
			AddonLoader loader = new CraftAddonLoader(file);
			return loader.load();
		} catch (Throwable ex) {
			if (Main.DEBUG) {
				ex.printStackTrace();
			}
			
			this.handler.throwError(new ThrownError(Addon.ERROR_LOAD, IOUtil.preparePath(file.getPath())));
			return null;
		}
	}
	
	private boolean enableAddon(ConsoleCommandSender sender, Addon addon, File file) {
		for (String plugin : addon.getDependencies()) {
			if (Bukkit.getPluginManager().getPlugin(plugin) == null || !Bukkit.getPluginManager().getPlugin(plugin).isEnabled()) {
				this.handler.throwError(new ThrownError(Addon.ERROR_ENABLE_DEPENCIES, IOUtil.preparePath(file.getPath()), plugin));
				return false;
			}
		}
		
		if (addon.requiresFrame() && !Core.isFrameEnabled()) {
			this.handler.throwError(new ThrownError(Addon.ERROR_ENABLE_FRAME_REQUIRED, IOUtil.preparePath(file.getPath())), Frame.ERROR_ENABLE);
			return false;
		} else if (Core.isFrameEnabled() && addon.getIncompatibleFrames().contains(this.frame.getFrameId())) {
			this.handler.throwError(new ThrownError(Addon.ERROR_ENABLE_FRAME_INCOMPATIBLE, IOUtil.preparePath(file.getPath())));
			return false;
		}
		
		AddonProperties properties = new AddonProperties(new File(this.rootDirectory.getBootFolder().getRelativPath()));
		
		try {
			addon.enable(properties);
			return true;
		} catch (Throwable ex) {
			if (Main.DEBUG) {
				ex.printStackTrace();
			}
			
			this.handler.throwError(new ThrownError(Addon.ERROR_ENABLE, IOUtil.preparePath(file.getPath())));
			return false;
		}
	}
	
}