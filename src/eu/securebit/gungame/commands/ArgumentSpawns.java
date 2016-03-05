package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.util.CoreMessages;
import eu.securebit.gungame.util.Permissions;
import eu.securebit.gungame.util.Util;

public class ArgumentSpawns extends CustomArgument {

	@Override
	public String getSyntax() {
		return "/gungame spawns {add|tp|remove|list} ...";
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
		
		if (!Core.getSession().isFrameEnabled()) {
			player.sendMessage(CoreMessages.frameDisabled());
			return true;
		}
		
		if (!Core.getSession().getFrame().isInGame(player)) {
			player.sendMessage(CoreMessages.notInGame());
			return true;
		}
		
		GunGame gungame = Core.getSession().getFrame().getGame(player);
		
		if (!gungame.getMap().wasSuccessful()) {
			player.sendMessage(CoreMessages.interprete(gungame.getMap()));
			return true;
		}
		
		if (args.length <= 1) {
			this.sendSuggestions(sender);
			return true;
		} else if (args[1].equalsIgnoreCase("add")) {
			int createId = gungame.getMap().addSpawnPoint(player.getLocation());
			sender.sendMessage(CoreMessages.spawnAdded(createId));
			return true;
		} else if (args[1].equalsIgnoreCase("tp")) {
			if (args.length != 3) {
				sender.sendMessage(CoreMessages.syntax("/gungame spawns tp <id>"));
				return true;
			}
			
			if (Util.isInt(args[2])) {
				int id = NumberConversions.toInt(args[2]);
				
				if (gungame.getMap().containsSpawn(id)) {
					try {
						player.teleport(gungame.getMap().getSpawnPoint(id));
					} catch (Exception ex) {
						player.sendMessage(CoreMessages.worldNotFound("spawnworld"));
					} finally {
						player.sendMessage(CoreMessages.spawnTeleportedTo(id));
					}
				} else {
					player.sendMessage(CoreMessages.spawnNotExisting(id));
				}
			} else {
				player.sendMessage(CoreMessages.invalidNumber(args[2]));
			}
			
			return true;
		} else if (args[1].equalsIgnoreCase("remove")) {
			if (args.length != 3) {
				sender.sendMessage(CoreMessages.syntax("/gungame spawns remove <id>"));
				return true;
			}
			
			if (Util.isInt(args[2])) {
				int id = NumberConversions.toInt(args[2]);
				
				if (gungame.getMap().containsSpawn(id)) {
					gungame.getMap().removeSpawnPoint(id);
					player.sendMessage(CoreMessages.spawnRemoved(id));
				} else {
					player.sendMessage(CoreMessages.spawnNotExisting(id));
				}
			} else {
				player.sendMessage(CoreMessages.invalidNumber(args[2]));
			}
		} else if (args[1].equalsIgnoreCase("list")) {
			if (args.length == 2) {
				String ids = "";
				
				for (int spawnId : gungame.getMap().getSpawnPointIds()) {
					ids = ids + Integer.toString(spawnId) + ", ";
				}
				
				Main.layout().message(player, "Registered ids: " + (ids.isEmpty() ? "NONE" : ids.substring(0, ids.length() - 2)));
				return true;
			} else if (args.length == 3) {
				if (!args[2].equals("-w")) {
					sender.sendMessage(CoreMessages.syntax("/gungame spawns list [-w]"));
					return true;
				}
				
				InfoLayout layout = Main.layout();
				
				layout.begin();
				layout.category("Spawns");
				
				for (int spawnId : gungame.getMap().getSpawnPointIds()) {
					layout.line("  $-" + Integer.toString(spawnId) + " (World: " + gungame.getMap().getSpawnPoint(spawnId).getWorld().getName() + ")");
				}
				
				layout.barrier();
				layout.commit(player);
				return true;
			} else {
				sender.sendMessage(CoreMessages.syntax("/gungame spawns list [-w]"));
				return true;
			}
		} else {
			this.sendSuggestions(sender);
			return true;
		}
		
		return true;
	}

	private void sendSuggestions(CommandSender sender) {
		Main.layout().begin();
		Main.layout().category("Syntax");
		Main.layout().line("/gungame spawns add");
		Main.layout().line("/gungame spawns tp <id>");
		Main.layout().line("/gungame spawns remove <id>");
		Main.layout().line("/gungame spawns list [$-w]");
		Main.layout().line("");
		Main.layout().line("Type */gungame help spawns* for further information.");
		Main.layout().barrier();
		Main.layout().commit(sender);
	}

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("This argument makes it possible to simply");
		layout.line("manage spawn points.");
		layout.line("");
		layout.line("*$-\"$- add*");
		layout.line("Adds a new spawn using your current location,");
		layout.line("you'll get back the generated id.");
		layout.line("");
		layout.line("*$-\"$- tp <id>*");
		layout.line("Teleports you to spawnpoint with the");
		layout.line("given id.");
		layout.line("");
		layout.line("*$-\"$- remove <id>*");
		layout.line("Removes the specified spawn by its id.");
		layout.line("*$-\"$- list [$-w]*");
		layout.line("Lists all registered spawns. By giving the flag $-w the ouput will contain the world of each spawn.");
	}
}
