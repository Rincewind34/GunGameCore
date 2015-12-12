package eu.securebit.gungame.test;

import lib.securebit.command.Argument;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;

public class ArgumentTestLocations extends Argument<Main> {

	public ArgumentTestLocations() {
		super(Main.instance());
	}
	
	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		sender.sendMessage("�7Testing...");
		
		player.teleport(super.plugin.getFileConfig().getLocationLobby());
		for (Location loc : super.plugin.getFileConfig().getSpawns()) {
			sender.sendMessage("�7Spawn: " + loc.getWorld().getName() + " | " + loc.getX() + " | " + loc.getY() + " | " + loc.getZ());
		}
		
		sender.sendMessage("�7Done!");
		return true;
	}
	
	// Really? Of course.
	@Override public String getSyntax() {return null;}
	@Override public String getPermission() {return null;}
	@Override public boolean isOnlyForPlayer() {return true;}
}