package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.util.Permissions;
import eu.securebit.gungame.util.Util;

public class ArgumentServer extends CustomArgument {

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Gives information about the server.");
	}

	@Override
	public String getSyntax() {
		return "/gungame server";
	}

	@Override
	public String getPermission() {
		return Permissions.commandGunGameServer();
	}

	@Override
	public boolean isOnlyForPlayer() {
		return false;
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = Main.layout();
		layout.begin();
		Util.stageServerInformation(layout);
		layout.commit(sender);
		
		return true;
	}
	
}
