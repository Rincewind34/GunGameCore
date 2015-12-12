package eu.securebit.gungame.test;

import lib.securebit.command.Argument;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.gungame.Main;

public class ArgumentTestSkip extends Argument<Main> {

	public ArgumentTestSkip() {
		super(Main.instance());
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			Main.instance().getGameStateManager().next();
			
			sender.sendMessage("§7Successful.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	
		return true;
	}

	// Really? Of course.
	@Override public String getSyntax() {return null;}
	@Override public String getPermission() {return null;}
	@Override public boolean isOnlyForPlayer() {return true;}
}
