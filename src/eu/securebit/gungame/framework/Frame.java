package eu.securebit.gungame.framework;

import java.io.File;

import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.ioutil.IOUtil;

public abstract class Frame {
	
	public static final String ERROR_LOAD = 			"2000";
	
	public static final String ERROR_LOAD_MAINCLASS = 	"2100";
	
	public static final String ERROR_ENABLE = 			"3000";
	
	public static final String ERROR_ENABLE_ID = 		"3100";
	
	public static Error createErrorLoad() {
		return new SimpleError("Frame could not be loaded!");
	}
	
	public static Error createErrorLoadMainclass() {
		return new SimpleError("No main-class in the given frame found!", Frame.ERROR_LOAD);
	}
	
	public static Error createErrorEnable() {
		return new SimpleError("Frame could not be enabled!");
	}
	
	public static Error createErrorEnableId() {
		return new SimpleFixableError("The frameid in the given bootfolder does not match the id of the loaded frame!", Frame.ERROR_ENABLE, () -> {
			// TODO delete bootfolder
		});
	}
	
	
	public static Frame instance() {
		return Main.instance().getFrame();
	}
	
	
	private File dataFolder;
	
	public final void enable(FrameProperties properties) {
		this.dataFolder = properties.getDataFolder();
		this.onEnable();
	}
	
	public final void disable() {
		this.onDisable();
	}
	
	public final File getDataFolder() {
		return this.dataFolder;
	}
	
	public final String getDataFolderRelativPath() {
		return IOUtil.preparePath(this.getDataFolderAbsolutePath());
	}
	
	public final String getDataFolderAbsolutePath() {
		return this.dataFolder.getAbsolutePath();
	}
	
	public abstract void onEnable();
	
	public abstract void onDisable();
	
	public abstract boolean isInGame(Player player);
	
	public abstract int getFrameId();
	
	public abstract String getVersion();
	
	public abstract String getName();
	
	public abstract GunGame getGame(Player player);
	
	
	public static class FrameProperties {
		
		private final File dataFolder;
		
		public FrameProperties(File dataFolder) {
			this.dataFolder = dataFolder;
		}
		
		public File getDataFolder() {
			return this.dataFolder;
		}
		
	}
	
}
