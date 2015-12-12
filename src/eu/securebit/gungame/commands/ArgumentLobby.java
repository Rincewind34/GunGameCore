package eu.securebit.gungame.commands;

import lib.securebit.command.Argument;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.Permissions;
import eu.securebit.gungame.io.MainConfig;

public class ArgumentLobby extends Argument<Main> {

	public ArgumentLobby() {
		super(Main.instance());
	}

	@Override
	public String getSyntax() {
		return "/gungame lobby {set|tp}";
	}

	@Override
	public String getPermission() {
		return Permissions.commandGunGameSpawns();
	}

	@Override
	public boolean isOnlyForPlayer() {
		return true;
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		if (args.length == 2) {
			MainConfig config = Main.instance().getFileConfig();
			
			if (args[1].equals("tp")) {
				try {
					player.teleport(config.getLocationLobby());
				} catch (Exception ex) {
					player.sendMessage(Messages.worldNotFound("lobbyworld"));
				} finally {
					player.sendMessage(Messages.lobbyTeleport());
				}
			} else if (args[1].equals("set")) {
				config.setLocationLobby(player.getLocation());
				player.sendMessage(Messages.lobbySet());
			} else {
				return false;
			}
		} else {
			return false;
		}
		
		return true;
	}

}
