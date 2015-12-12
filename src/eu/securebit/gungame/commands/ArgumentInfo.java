package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;
import lib.securebit.command.Argument;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.gungame.Information;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.Permissions;

public class ArgumentInfo extends Argument<Main> {
	
	public ArgumentInfo() {
		super(Main.instance());
	}

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
			Information.generalInfo(layout);
			layout.commit(sender);
		} else {
			sender.sendMessage(Messages.syntax(this.getSyntax()));
		}
		
		return true;
	}

}
