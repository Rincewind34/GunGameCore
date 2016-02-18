package eu.securebit.gungame.io.directories;

import java.io.File;

import org.bukkit.World;

import eu.securebit.gungame.errorhandling.layouts.LayoutError;
import eu.securebit.gungame.io.abstracts.Directory;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.io.configs.FileLevels;
import eu.securebit.gungame.io.configs.FileMap;
import eu.securebit.gungame.io.configs.FileMessages;
import eu.securebit.gungame.io.configs.FileOptions;
import eu.securebit.gungame.io.configs.FileScoreboard;
import eu.securebit.gungame.util.ColorSet;

public interface RootDirectory extends Directory {
	
	public static final String ERROR_MAIN = 			"Error-1000";
	
	public static final String ERROR_FILE = 			"Error-1100";
	
	public static final String ERROR_CREATE = 			"Error-1200";
	
	public static final String ERROR_FRAME = 			"Error-1800";
	
	public static final String ERROR_FRAME_EXIST = 		"Error-1810";
	
	public static final String ERROR_FRAME_NOJAR = 		"Error-1820";
	
	public static LayoutError createErrorMain() {
		return new LayoutError("The rootdirectory could not be loaded!");
	}
	
	public static LayoutError createErrorFile() {
		return new LayoutError("The rootdirectory is a file", RootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorCreate() {
		return new LayoutError("The rootdirectory could not be created!", RootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorFrame() {
		return new LayoutError("The frame could not be found!", RootDirectory.ERROR_MAIN);
	}
	
	public static LayoutError createErrorFrameExists() {
		return new LayoutError("The framepath given by the bootconfig is empty!", RootDirectory.ERROR_FRAME); // TODO fix as framesearch
	}
	
	public static LayoutError createErrorFrameNojar() {
		return new LayoutError("The framepath given by the bootconfig is not a jarfile!", RootDirectory.ERROR_FRAME); // TODO fix as framesearch
	}
	
	
	public abstract void resolveColorSet();
	
	public abstract void deleteBootConfig();
	
	public abstract void deleteConfigRegistry();
	
	public abstract void setColorSet(ColorSet colorset);
	
	public abstract void setDebugMode(boolean debug);
	
	public abstract void deleteFile(String directory, String name);
	
	public abstract void deleteFile(String relativPath);
	
	public abstract void deleteFile(File file);
	
	public abstract boolean isFramePresent();
	
	public abstract boolean isDebugMode();
	
	public abstract AddonDirectory getAddonDirectory();
	
	public abstract BootDirectory getBootFolder();
	
	public abstract File getFile(String path);
	
	public abstract File getFrameJar();
	
	public abstract FileMessages getMessagesFile(String directory, String name);
	
	public abstract FileMessages getMessagesFile(String relativPath);
	
	public abstract FileMessages getMessagesFile(File file);
	
	public abstract FileScoreboard getScoreboardFile(String directory, String name);
	
	public abstract FileScoreboard getScoreboardFile(String relativPath);
	
	public abstract FileScoreboard getScoreboardFile(File file);
	
	public abstract FileLevels getLevelsFile(String directory, String name);
	
	public abstract FileLevels getLevelsFile(String relativPath);
	
	public abstract FileLevels getLevelsFile(File file);
	
	public abstract FileOptions getOptionsFile(String directory, String name);
	
	public abstract FileOptions getOptionsFile(String relativPath);
	
	public abstract FileOptions getOptionsFile(File file);
	
	public abstract FileMap getMapFile(String directory, String name);
	
	public abstract FileMap getMapFile(String relativPath);
	
	public abstract FileMap getMapFile(File file);
	
	public abstract FileGameConfig getGameConfigFile(String directory, String name, World lobbyWorld);
	
	public abstract FileGameConfig getGameConfigFile(String relativPath, World lobbyWorld);
	
	public abstract FileGameConfig getGameConfigFile(File file, World lobbyWorld);
	
	public abstract <T extends FileIdentifyable> T getIdentifyableFile(T file);

}
