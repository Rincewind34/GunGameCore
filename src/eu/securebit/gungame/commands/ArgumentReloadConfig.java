package eu.securebit.gungame.commands;

import lib.securebit.command.Argument;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.Permissions;
import eu.securebit.gungame.game.states.DisabledStateEdit;

public class ArgumentReloadConfig extends Argument<Main> {
	
	public ArgumentReloadConfig() {
		super(Main.instance());
	}

	@Override
	public String getSyntax() {
		return "/gungame rlcfg";
	}

	@Override
	public String getPermission() {
		return Permissions.commandGunGameReloadConfig();
	}

	@Override
	public boolean isOnlyForPlayer() {
		return false;
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			if (Main.instance().getGameStateManager().getCurrent() instanceof DisabledStateEdit) {
				Main.instance().reloadConfig();
				sender.sendMessage(Messages.reloadFiles());
			} else {
				sender.sendMessage(Messages.wrongMode("GameMode"));
			}
		} else {
			sender.sendMessage(Messages.syntax(this.getSyntax()));
		}
		
		return true;
	}

}
