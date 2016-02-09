package eu.securebit.gungame.framework;

import java.io.File;

import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.ioutil.IOUtil;

public abstract class Frame {
	
	public static final String ERROR_LOAD = 			"2000";
	
	public static final String ERROR_LOAD_MAINCLASS = 	"2100";
	
	public static final String ERROR_ENABLE = 			"3000";
	
	public static final String ERROR_ENABLE_ID = 		"3100";
	
	public static LayoutError createErrorLoad() {
		return new LayoutError("Frame could not be loaded!");
	}
	
	public static LayoutError createErrorLoadMainclass() {
		return new LayoutError("No main-class in the given frame found!", Frame.ERROR_LOAD);
	}
	
	public static LayoutError createErrorEnable() {
		return new LayoutError("Frame could not be enabled!");
	}
	
	public static LayoutError createErrorEnableId() {
		return new LayoutErrorFixable("The frameid in the given bootfolder does not match the id of the loaded frame!", Frame.ERROR_ENABLE, () -> {
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
