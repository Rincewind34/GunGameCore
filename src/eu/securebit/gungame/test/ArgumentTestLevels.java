package eu.securebit.gungame.test;

import lib.securebit.command.Argument;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;

public class ArgumentTestLevels extends Argument<Main> {

	public ArgumentTestLevels() {
		super(Main.instance());
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		if (args[1].equalsIgnoreCase("create")) {
			sender.sendMessage("�7Adding level...");
			super.plugin.getFileLevels().setItems(player, Integer.parseInt(args[2]));
			super.plugin.getFileLevels().save();
			sender.sendMessage("�7Success! ID: " + args[2]);
		}
		
		if (args[1].equalsIgnoreCase("give")) {
			sender.sendMessage("�7Giving level items...");
			super.plugin.getFileLevels().give(player, 1);
			sender.sendMessage("�7Success! You have got the items for level 1.");
		}
		
		if (args[1].equalsIgnoreCase("count")) {
			sender.sendMessage("�7There are " + super.plugin.getFileLevels().getLevelCount() + " levels registered.");
		}
		return true;
	}

	// Really? Of course.
	@Override public String getSyntax() {return null;}
	@Override public String getPermission() {return null;}
	@Override public boolean isOnlyForPlayer() {return true;}
}
