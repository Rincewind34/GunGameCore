package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.util.Permissions;

public class ArgumentError extends CustomArgument {

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Prints you all thrown errors");
	}

	@Override
	public String getSyntax() {
		return "/gungame error";
	}

	@Override
	public String getPermission() {
		return Permissions.commandGunGameError();
	}

	@Override
	public boolean isOnlyForPlayer() {
		return false;
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = Main.layout();
		
		layout.begin();
		layout.category("Errors");
		
		for (ThrownError error : Main.instance().getErrorHandler().getErrors().keySet()) {
			layout.line("-" + InfoLayout.replaceKeys(error.getParsedObjectId() + " (" + error.getParsedMessage()) + ")-");
			
			ThrownError cause = Main.instance().getErrorHandler().getErrors().get(error);
			
			if (cause != null) {
				layout.line("-=> Cause: " + InfoLayout.replaceKeys(cause.getParsedObjectId() + " (" + cause.getParsedMessage()) + ")-");
			}
		}
		
		layout.barrier();
		layout.commit(sender);
		return true;
	}

}
