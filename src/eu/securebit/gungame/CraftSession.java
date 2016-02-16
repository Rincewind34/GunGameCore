package eu.securebit.gungame;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;

import org.bukkit.Bukkit;

import eu.securebit.gungame.addonsystem.Addon;
import eu.securebit.gungame.addonsystem.Addon.AddonProperties;
import eu.securebit.gungame.errorhandling.ErrorHandler;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.exception.GunGameJarException;
import eu.securebit.gungame.exception.GunGameSessionException;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.framework.Frame;
import eu.securebit.gungame.framework.Frame.FrameProperties;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.io.directories.BootDirectory;
import eu.securebit.gungame.io.loader.AddonLoader;
import eu.securebit.gungame.io.loader.FrameLoader;
import eu.securebit.gungame.ioimpl.loader.CraftAddonLoader;
import eu.securebit.gungame.ioimpl.loader.CraftFrameLoader;
import eu.securebit.gungame.ioutil.IOUtil;
import eu.securebit.gungame.util.Util;

public class CraftSession implements Session {
	
	private boolean debugMode;
	
	private ThrownError errorFrame;
	private ThrownError errorAddons;
	
	private ErrorHandler handler;
	
	private BootDirectory bootFolder;
	
	private File frameFile;
	private List<File> addonFiles;
	
	private Frame frame;
	private Map<File, Addon> loadedAddons;
	private List<Addon> enabledAddons;
	
	private Map<String, GunGame> games;
	
	public CraftSession(ThrownError errorFrame, ThrownError errorAddons, BootDirectory folder, ErrorHandler handler, boolean debugMode) {
		this((File) null, (List<File>) null, folder, handler, debugMode);
		
		this.errorFrame = errorFrame;
		this.errorAddons = errorAddons;
	}
	
	public CraftSession(File frame, ThrownError errorAddons, BootDirectory folder, ErrorHandler handler, boolean debugMode) {
		this(frame, (List<File>) null, folder, handler, debugMode);
		
		this.errorAddons = errorAddons;
	}
	
	public CraftSession(ThrownError errorFrame, List<File> addons, BootDirectory folder, ErrorHandler handler, boolean debugMode) {
		this((File) null, addons, folder, handler, debugMode);
		
		this.errorFrame = errorFrame;
	}
	
	public CraftSession(File frame, List<File> addons, BootDirectory folder, ErrorHandler handler, boolean debugMode) {
		this.frameFile = frame;
		this.addonFiles = addons;
		this.bootFolder = folder;
		this.handler = handler;
		this.debugMode = debugMode;
		
		this.loadedAddons = new HashMap<>();
		this.enabledAddons = new ArrayList<>();
		this.games = new HashMap<>();
		
		this.errorFrame = null;
		this.errorAddons = null;
	}
	
	@Override
	public void boot() {
		this.boot(Output.create(Main.layout()));
	}
	
	@Override
	public void boot(Output output) {
		output.insert("Loading frame...");
		
		if (this.frameFile != null) {
			if (this.loadFrame(output)) {
				output.insert("Frame loaded!");
			} else {
				output.insert("Frame could not be loaded!");
			}
		} else {
			this.handler.throwError(Frame.ERROR_LOAD, this.errorFrame);
			output.insert("Frame could not be loaded!");
		}
		
		output.printEmptyLine();
		output.insert("Preparing frame...");
		
		if (this.isFrameLoaded()) {
			String name = InfoLayout.replaceKeys(this.frame.getName());
			String version = InfoLayout.replaceKeys(this.frame.getVersion());
			
			output.insert("Enabling frame *%s* version *%s*...", name, version);
			
			if (this.enableFrame(output)) {
				output.insert("+Frame enabled!+");
			} else {
				output.insert("-Frame could not be enabled!-");
			}
		} else {
			this.handler.throwError(Frame.ERROR_ENABLE, Frame.ERROR_LOAD);
			output.insert("Aborting!");
		}
		

		output.printEmptyLine();
		output.insert("Initializing addonloading...");
		
		if (this.addonFiles != null) {
			if (this.addonFiles.size() != 0) {
				output.insert("Loading addons (" + this.addonFiles.size() + ")...");
				
				for (File file : this.addonFiles) {
					output.insert("Loading addon (file: %s)...", IOUtil.preparePath(file.getPath()));
					
					Addon addon = this.loadAddon(file, output);
					
					if (addon != null) {
						this.loadedAddons.put(file, addon);
						output.insert("Could load the addon!");
					} else {
						output.insert("Addon loaded!");
					}
				}
				
				String status = output.getLayout().colorPrimary + "[*" + this.loadedAddons.size() + "*" + 
						output.getLayout().colorPrimary + "/*" + this.addonFiles.size() + "*" + 
						output.getLayout().colorPrimary + "]";
				
				if (this.loadedAddons.size() == this.loadedAddons.size()) {
					output.insert("+All addons loaded+ " + status + "+!+");
				} else {
					output.insert("-Could not load all addons- " + status + "-!-");
				}
				
				if (this.loadedAddons.size() != 0) {
					output.printEmptyLine();
					output.insert("Enabling addons (" + this.loadedAddons.size() + ") ...");
					
					for (File file : this.loadedAddons.keySet()) {
						Addon addon = this.loadedAddons.get(file);
						
						output.insert("Enabling addon *%s* version *%s*...", addon.getName(), addon.getVersion());
						
						if (this.enableAddon(file, output)) {
							this.enabledAddons.add(addon);
							output.insert("Addon enabled!");
						} else {
							output.insert("Could not enable addon!");
						}
					}
					
					status = output.getLayout().colorPrimary + "[*" + this.enabledAddons.size() + "*" + 
							output.getLayout().colorPrimary + "/*" + this.loadedAddons.size() + "*" + 
							output.getLayout().colorPrimary + "]";
					
					if (this.loadedAddons.size() == this.enabledAddons.size()) {
						output.insert("+All addons enabled+ " + status + "+!+");
					} else {
						output.insert("-Could not load all addons- " + status + "-!-");
					}
				}
			} else {
				output.insert("No addons found!");
			}
		} else {
			this.handler.throwError(Addon.ERROR_INIT, this.errorAddons);
			output.insert("Initialization cancled!");
		}
		
		output.printEmptyLine();
		output.insert("+Booting complete!+");
		output.insert("Debug$-Mode: " + Util.parseBoolean(this.debugMode, output.getLayout()));
	}
	
	@Override
	public void reboot() {
		this.reboot(Output.create(Main.layout()));
	}
	
	@Override
	public void reboot(Output output) {
		this.shutdown(output);
		
		output.printEmptyLine();
		output.insert("Reboot...");
		output.printEmptyLine();
		
		this.boot(output);
	}
	
	@Override
	public void shutdown() {
		this.shutdown(Output.create(Main.layout()));
	}
	
	@Override
	public void shutdown(Output output) {
		for (String name : this.games.keySet()) {
			this.shutdownGame(name);
		}
		
		for (Addon addon : this.enabledAddons) {
			output.insert("Disabling addon *%s*...", addon.getName());
			
			try {
				addon.disable();
				output.insert("Addon disabled!");
			} catch (Throwable ex) {
				this.handleException(ex, output);
				output.insert("Could not disable the addon!");
			}
		}
		
		if (this.isFrameEnabled()) {
			output.insert("Disabling frame...");
			
			try {
				this.frame.disable();
				output.insert("Frame disabled!");
			} catch (Throwable ex) {
				this.handleException(ex, output);
				output.insert("Could not disable frame!");
			}
		}
	}
	
	@Override
	public void shutdownGame(String name) {
		GunGame gungame = this.getGame(name);
		
		for (GamePlayer player : gungame.getPlayers()) {
			gungame.quitPlayer(player.getHandle());
		}
		
		gungame.getManager().destroy();
		
		this.games.remove(name);
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		String frameName = this.isFrameEnabled() ? InfoLayout.replaceKeys(this.frame.getName()) : " $-$-$- ";
		String frameVersion = this.isFrameEnabled() ? InfoLayout.replaceKeys(this.frame.getVersion()) : " $-$-$- ";
		
		layout.category("General");
		layout.line("Core$-Version: " + InfoLayout.replaceKeys(Core.getPlugin().getDescription().getVersion()));
		layout.line("Frame$-Name: " + frameName);
		layout.line("Frame$-Version: " + frameVersion);
		layout.line("");
		layout.line("*Addons*");
		
		for (File addonFile : this.addonFiles) {
			String entry = "  $-";
			
			if (this.loadedAddons.containsKey(addonFile)) {
				if (this.enabledAddons.contains(this.loadedAddons.get(addonFile))) {
					entry = entry + "+";
				} else {
					entry = entry + "-";
				}
			} else {
				entry = entry + "-";
			}
			
			entry = entry + " " + addonFile.getName() + " ";
			
			if (this.loadedAddons.containsKey(addonFile)) {
				if (this.enabledAddons.contains(this.loadedAddons.get(addonFile))) {
					Addon addon = this.loadedAddons.get(addonFile);
					
					entry = entry + "+(Name: *" + addon.getName() + "*, Version: *" + addon.getVersion() + "*)";
				} else {
					entry = entry + "-(not enabled)";
				}
			} else {
				entry = entry + "(not loaded)-";
			}
			
			layout.line(entry);
		}
		
		if (this.addonFiles.size() == 0) {
			layout.line(" $-$- None $-$-");
		}
		
		layout.line("");
		layout.line("*Games*");
		
		for (GunGame gungame : this.games.values()) {
			String color = gungame.isReady() ? "+" : "-";
			
			layout.line("  $- " + color + gungame.getName() + color);
		}
		
		if (this.games.size() == 0) {
			layout.line(" $-$- None $-$-");
		}
		
		layout.barrier();
	}
	
	@Override
	public void handleException(Throwable throwable) {
		this.handleException(throwable, Output.create(Main.layout()));
	}
	
	@Override
	public void handleException(Throwable throwable, Output output) {
		if (this.debugMode) {
			output.insertException(throwable);
		}
	}
	
	@Override
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
		
		for (GunGame game : this.getGames()) {
			game.setDebugMode(debugMode);
		}
	}

	@Override
	public boolean isFrameLoaded() {
		return !this.handler.isErrorPresent(Frame.ERROR_LOAD);
	}

	@Override
	public boolean isFrameEnabled() {
		return !this.handler.isErrorPresent(Frame.ERROR_ENABLE);
	}
	
	@Override
	public boolean isDebugMode() {
		return this.debugMode;
	}
	
	@Override
	public boolean containsGame(String name) {
		return this.games.containsKey(name);
	}
	
	@Override
	public Frame getFrame() {
		return this.frame;
	}
	
	@Override
	public GunGame getGame(String name) {
		if (!this.containsGame(name)) {
			throw GunGameSessionException.gameNotExists(name);
		}
		
		return this.games.get(name);
	}
	
	@Override
	public GunGame restartGame(String name) {
		GameBuilder builder = this.getGame(name).toBuilder();
		
		this.shutdownGame(name);
		return this.createNewGameInstance(name, builder);
	}
	
	@Override
	public GunGame createNewGameInstance(String name, GameBuilder builder) {
		if (this.containsGame(name)) {
			throw GunGameSessionException.gameAlreadyExists(name);
		}
		
		builder.setName(name);
		builder.setHandler(this.handler);
		
		GunGame gungame = builder.build();
		
		this.games.put(name, gungame);
		
		return gungame;
	}
	
	@Override
	public List<GunGame> getGames() {
		return Collections.unmodifiableList(this.games.values().stream().collect(Collectors.toList()));
	}
	
	private boolean loadFrame(Output output) {
		try {
			FrameLoader loader = new CraftFrameLoader(this.frameFile);
			this.frame = loader.load();
			
			return true;
		} catch (GunGameJarException ex) {
			this.handleException(ex, output);
			
			this.frame = null;
			this.handler.throwError(Frame.ERROR_LOAD_MAINCLASS);
		} catch (Throwable ex) {
			this.handleException(ex, output);
			
			this.frame = null;
			this.handler.throwError(Frame.ERROR_LOAD);
		}
		
		return false;
	}
	
	private boolean enableFrame(Output output) {
		if (this.bootFolder.isReady()) {
			if (this.bootFolder.getUsingFrameId() == 0) {
				this.bootFolder.setUsingFrameId(this.frame.getFrameId());
			}
			
			if (this.bootFolder.getUsingFrameId() == this.frame.getFrameId()) {
				output.printBarrier();
				
				try {
					this.frame.enable(new FrameProperties(new File(bootFolder.getRelativPath())));
				} catch (Throwable ex) {
					this.handleException(ex, output);
					
					this.handler.throwError(Frame.ERROR_ENABLE);
				}
				
				output.printBarrier();
			} else {
				this.handler.throwError(Frame.ERROR_ENABLE_ID);
			}
		} else {
			this.handler.throwError(Frame.ERROR_ENABLE, BootDirectory.ERROR_MAIN);
		}
		
		return this.isFrameEnabled();
	}
	
	private Addon loadAddon(File file, Output output) {
		try {
			AddonLoader loader = new CraftAddonLoader(file);
			return loader.load();
		} catch (Throwable ex) {
			this.handleException(ex, output);
			
			this.handler.throwError(new ThrownError(Addon.ERROR_LOAD, IOUtil.preparePath(file.getPath())));
			return null;
		}
	}
	
	private boolean enableAddon(File file, Output output) {
		Addon addon = this.loadedAddons.get(file);
		
		for (String plugin : addon.getDependencies()) {
			if (Bukkit.getPluginManager().getPlugin(plugin) == null || !Bukkit.getPluginManager().getPlugin(plugin).isEnabled()) {
				this.handler.throwError(new ThrownError(Addon.ERROR_ENABLE_DEPENCIES, IOUtil.preparePath(file.getPath()), plugin));
				return false;
			}
		}
		
		if (addon.requiresFrame() && !this.isFrameEnabled()) {
			this.handler.throwError(new ThrownError(Addon.ERROR_ENABLE_FRAME_REQUIRED, IOUtil.preparePath(file.getPath())), Frame.ERROR_ENABLE);
			return false;
		} else if (this.isFrameEnabled() && addon.getIncompatibleFrames().contains(this.frame.getFrameId())) {
			this.handler.throwError(new ThrownError(Addon.ERROR_ENABLE_FRAME_INCOMPATIBLE, IOUtil.preparePath(file.getPath())));
			return false;
		}
		
		try {
			addon.enable(new AddonProperties(new File(this.bootFolder.getRelativPath())));
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
