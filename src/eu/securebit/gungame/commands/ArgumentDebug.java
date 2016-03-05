package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.util.CoreMessages;
import eu.securebit.gungame.util.Permissions;

public class ArgumentDebug extends CustomArgument {

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Switches the debug mode of the game.");
	}

	@Override
	public String getSyntax() {
		return "/gungame debug [on|off]";
	}

	@Override
	public String getPermission() {
		return Permissions.commandGunGameDebug();
	}

	@Override
	public boolean isOnlyForPlayer() {
		return false;
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		String debugMode;
		
		if (args.length == 1) {
			debugMode = Core.getSession().isDebugMode() ? "off" : "on";
		} else if (args.length == 2) {
			debugMode = args[1].toLowerCase();
		} else {
			return false;
		}
		
		if (debugMode.equals("on")) {
			if (!Core.getSession().isDebugMode()) {
				Core.getSession().setDebugMode(true);
				Core.getRootDirectory().setDebugMode(true);
			} else {
				sender.sendMessage(CoreMessages.debugModeActive());
				return true;
			}
		} else if (debugMode.equals("off")) {
			if (Core.getSession().isDebugMode()) {
				Core.getSession().setDebugMode(false);
				Core.getRootDirectory().setDebugMode(false);
			} else {
				sender.sendMessage(CoreMessages.debugModeInactive());
				return true;
			}
		} else {
			return false;
		}
		
		sender.sendMessage(CoreMessages.debugModeSwitch(debugMode));
		return true;
	}

}
