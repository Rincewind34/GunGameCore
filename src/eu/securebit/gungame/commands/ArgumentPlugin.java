package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Output;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.util.Permissions;

public class ArgumentPlugin extends CustomArgument {

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("*$-\"$- <no parameter>*");
		layout.line("Gives information about the server and plugin.");
		layout.line("");
		layout.line("*$-\"$- shutdown*");
		layout.line("Shutdowns the whole plugin.");
	}

	@Override
	public String getSyntax() {
		return "/gungame plugin [shutdown]";
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
		Output output = Output.create(Main.layout(), sender);
		
		if (!(sender instanceof ConsoleCommandSender)) {
			output.addReceiver(Bukkit.getConsoleSender());
		}
		
		if (args.length == 1) {
			InfoLayout layout = Main.layout();
			layout.begin();
			Core.getSession().stageInformation(layout);
			layout.commit(sender);
		} else if (args.length == 2) {
			if (args[1].equals("reboot")) {
				Core.getSession().reboot(output);
			} else if (args[1].equals("shutdown")) {
				Core.shutdown();
			} else {
				return false;
			}
		} else {
			return false;
		}
		
		return true;
	}
	
}
