package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.Permissions;
import eu.securebit.gungame.Util;

public class ArgumentInfo extends CustomArgument {
	
	@Override
	public String getSyntax() {
		return "/gungame info";
	}

	@Override
	public String getPermission() {
		return Permissions.commandGunGameToggle();
	}

	@Override
	public boolean isOnlyForPlayer() {
		return false;
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			InfoLayout layout = Main.layout();
			layout.begin();
			Util.stageInformation(layout);
			layout.commit(sender);
		} else {
			sender.sendMessage(Messages.syntax(this.getSyntax()));
		}
		
		return true;
	}

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Shows information about the current state");
	}

}
