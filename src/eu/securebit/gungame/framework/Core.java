package eu.securebit.gungame.framework;

import lib.securebit.command.ArgumentedCommand;

import org.bukkit.plugin.Plugin;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Session;
import eu.securebit.gungame.errorhandling.ErrorHandler;
import eu.securebit.gungame.io.directories.RootDirectory;

public class Core {
	
	public static ArgumentedCommand getCommand() {
		return Main.instance().getCommand();
	}
	
	public static Plugin getPlugin() {
		return Main.instance();
	}
	
	public static RootDirectory getRootDirectory() {
		return Main.instance().getRootDirectory();
	}
	
	public static ErrorHandler getErrorHandler() {
		return Main.instance().getErrorHandler();
	}
	
	public static Session getSession() {
		return Main.instance().getSession();
	}
	
}