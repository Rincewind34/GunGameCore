package eu.securebit.gungame.commands;

import lib.securebit.command.Argument;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.Permissions;
import eu.securebit.gungame.game.states.DisabledStateEdit;

public class ArgumentToggle extends Argument<Main> {
	
	public ArgumentToggle() {
		super(Main.instance());
	}

	@Override
	public String getSyntax() {
		return "/gungame toggle";
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
			if (Main.instance().getGameStateManager().getCurrent() instanceof DisabledStateEdit) {
				Main.instance().getFileConfig().setEditMode(false);
				Main.instance().getFileConfig().save();
				
				sender.sendMessage(Messages.changeMode("GameMode"));
				sender.sendMessage(Messages.suggestReload());
			} else {
				Main.instance().getFileConfig().setEditMode(true);
				Main.instance().getFileConfig().save();
				
				sender.sendMessage(Messages.changeMode("EditMode"));
				sender.sendMessage(Messages.suggestReload());
			}
		} else {
			sender.sendMessage(Messages.syntax(this.getSyntax()));
		}
		
		return true;
	}

}
