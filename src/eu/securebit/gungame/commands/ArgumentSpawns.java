package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.util.CoreMessages;
import eu.securebit.gungame.util.Permissions;
import eu.securebit.gungame.util.Util;

public class ArgumentSpawns extends CustomArgument {

	private final String descTp;
	private final String descAdd;
	private final String descRemove;
	
	public ArgumentSpawns() {
		this.descTp = "Teleports you to the specified spawn point.";
		this.descAdd = "";
		this.descRemove = "Removes the specified spawn by its id.";
	}

	@Override
	public String getSyntax() {
		return "/gungame spawns";
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
		
		if (!Main.instance().getFrame().isInGame(player)) {
			player.sendMessage(CoreMessages.notInGame());
			return true;
		}
		
		GunGame gungame = Main.instance().getFrame().getGame(player);
		
		if (args.length <= 1) {
			this.sendSuggestions(sender);
			return true;
		} else if (args[1].equalsIgnoreCase("add")) {
			int createId = gungame.addSpawn(player.getLocation());
			sender.sendMessage(CoreMessages.spawnAdded(createId));
			return true;
		} else if (args[1].equalsIgnoreCase("tp")) {
			if (args.length != 3) {
				sender.sendMessage(CoreMessages.syntax("/gungame spawns tp <id>"));
				return true;
			}
			
			if (Util.isInt(args[2])) {
				int id = NumberConversions.toInt(args[2]);
				
				if (gungame.getSettings().locations().getSpawnPoints().containsKey(id)) {
					try {
						player.teleport(gungame.getSettings().locations().getSpawnPoints().get(id));
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
				if (gungame.getSettings().locations().getSpawnPoints().containsKey(id)) {
					gungame.removeSpawn(id);
					player.sendMessage(CoreMessages.spawnRemoved(id));
				} else {
					player.sendMessage(CoreMessages.spawnNotExisting(id));
				}
			} else {
				player.sendMessage(CoreMessages.invalidNumber(args[2]));
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
		Main.layout().line("");
		Main.layout().line("Type */gungame help spawns* for further information.");
		Main.layout().barrier();
		Main.layout().commit(sender);
	}

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("*$-\"$- add*");
		layout.line("Adds a new spawn using your current location,");
		layout.line("you'll get back the generated id.");
		layout.line("");
		layout.line("*$-\"$- tp <id>*");
		layout.line("Teleports you to the defined lobby location.");
		
		layout.line("This argument makes it possible to simply manage spawn points.");
		layout.line("/gungame spawns add $- " + this.descAdd);
		layout.line("/gungame spawns tp <id> $- " + this.descTp);
		layout.line("/gungame spawns remove <id> $- " + this.descRemove);
	}
}
