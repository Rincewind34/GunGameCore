package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.util.CoreMessages;
import eu.securebit.gungame.util.Permissions;

public class ArgumentLobby extends CustomArgument {

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
		
		if (!Core.isFrameEnabled()) {
			player.sendMessage(CoreMessages.frameDisabled());
			return true;
		}
		
		if (!Main.instance().getFrame().isInGame(player)) {
			player.sendMessage(CoreMessages.notInGame());
			return true;
		}
		
		GunGame gungame = Main.instance().getFrame().getGame(player);
		
		if (!gungame.isFileReady()) {
			player.sendMessage(CoreMessages.gamefileNotPresent(gungame.getFileGameConfig()));
			return true;
		}
		
		if (args.length == 2) {
			if (args[1].equals("tp")) {
				try {
					player.teleport(gungame.getLobbyLocation());
				} catch (Exception ex) {
					player.sendMessage(CoreMessages.worldNotFound("lobbyworld"));
				} finally {
					player.sendMessage(CoreMessages.lobbyTeleport());
				}
			} else if (args[1].equals("set")) {
				gungame.setLobbyLocation(player.getLocation());
				player.sendMessage(CoreMessages.lobbySet());
			} else {
				return false;
			}
		} else {
			return false;
		}
		
		return true;
	}

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("*$-\"$- set*");
		layout.line("Sets the lobby location where players are");
		layout.line("teleported to after logging in.");
		layout.line("");
		layout.line("*$-\"$- tp*");
		layout.line("Teleports you to the defined lobby location.");
	}

}
