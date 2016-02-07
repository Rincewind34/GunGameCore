package eu.securebit.gungame.io.directories;

import java.io.File;

import org.bukkit.World;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errors.Error;
import eu.securebit.gungame.errors.SimpleError;
import eu.securebit.gungame.errors.SimpleFixableError;
import eu.securebit.gungame.io.abstracts.Directory;
import eu.securebit.gungame.io.abstracts.FileIdentifyable;
import eu.securebit.gungame.io.configs.FileGameConfig;
import eu.securebit.gungame.io.configs.FileLevels;
import eu.securebit.gungame.io.configs.FileMessages;
import eu.securebit.gungame.io.configs.FileScoreboard;

public interface RootDirectory extends Directory {
	
	public static final String ERROR_MAIN = 			"1|000|000|000";
	
	public static final String ERROR_FILE = 			"1|001|000|000";
	
	public static final String ERROR_CREATE = 			"1|002|000|000";
	
	public static final String ERROR_FRAME = 			"1|008|000|000";
	
	public static final String ERROR_FRAME_EXIST = 		"1|008|001|000";
	
	public static final String ERROR_FRAME_NOJAR = 		"1|008|002|000";
	
	public static Error createErrorMain() {
		return new SimpleError("The rootdirectory could not be loaded!");
	}
	
	public static Error createErrorFile() {
		return new SimpleFixableError("The rootdirectory is a file", RootDirectory.ERROR_MAIN, () -> {
			Main.instance().getDataFolder().delete();
		});
	}
	
	public static Error createErrorCreate() {
		return new SimpleError("The rootdirectory could not be created!", RootDirectory.ERROR_MAIN);
	}
	
	public static Error createErrorFrame() {
		return new SimpleError("The frame could not be found!", RootDirectory.ERROR_MAIN);
	}
	
	public static Error createErrorFrameExists() {
		return new SimpleFixableError("The framepath given by the bootconfig is empty!", RootDirectory.ERROR_FRAME, () -> {
			// TODO search for frame
		});
	}
	
	public static Error createErrorFrameNojar() {
		return new SimpleFixableError("The framepath given by the bootconfig is not a jarfile!", RootDirectory.ERROR_FRAME, () -> {
			// TODO search for valid frame
		});
	}
	
	
	public abstract void resolveColorSet();
	
	public abstract boolean isFramePresent();
	
	public abstract AddonDirectory getAddonDirectory();
	
	public abstract BootDirectory getBootFolder();
	
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
	
	public abstract FileGameConfig getGameConfigFile(String directory, String name, World lobbyWorld);
	
	public abstract FileGameConfig getGameConfigFile(String relativPath, World lobbyWorld);
	
	public abstract FileGameConfig getGameConfigFile(File file, World lobbyWorld);
	
	public abstract <T extends FileIdentifyable> T getIdentifyableFile(T file);

}
