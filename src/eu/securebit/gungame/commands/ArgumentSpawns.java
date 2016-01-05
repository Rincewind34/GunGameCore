package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

import eu.securebit.gungame.GunGame;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.Permissions;
import eu.securebit.gungame.Util;

public class ArgumentSpawns extends CustomArgument {

	private final String descTp;
	private final String descAdd;
	private final String descRemove;
	
	public ArgumentSpawns() {
		this.descTp = "Teleports you to the specified spawn point.";
		this.descAdd = "Adds a new spawn using your current location, you'll get back the generated id.";
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
			player.sendMessage(Messages.notInGame());
			return true;
		}
		
		GunGame gungame = Main.instance().getFrame().getGame(player);
		
		if (args.length <= 1) {
			this.sendSuggestions(sender);
			return true;
		}
		
		if (args[1].equalsIgnoreCase("add")) {
			int createId = gungame.addSpawn(player.getLocation());
			sender.sendMessage(Messages.spawnAdded(createId));
			return true;
		}
		
		if (args[1].equalsIgnoreCase("tp")) {
			if (args.length != 3) {
				sender.sendMessage(Messages.syntax("/gungame spawns tp <id>"));
				return true;
			}
			
			if (Util.isInt(args[2])) {
				int id = NumberConversions.toInt(args[2]);
				
				if (gungame.getSettings().getSpawnPoints().containsKey(id)) {
					try {
						player.teleport(gungame.getSettings().getSpawnPoints().get(id));
					} catch (Exception ex) {
						player.sendMessage(Messages.worldNotFound("spawnworld"));
					} finally {
						player.sendMessage(Messages.spawnTeleportedTo(id));
					}
				} else {
					player.sendMessage(Messages.spawnNotExisting(id));
				}
			} else {
				player.sendMessage(Messages.invalidNumber(args[2]));
			}
			
			return true;
		}
		
		if (args[1].equalsIgnoreCase("remove")) {
			if (args.length != 3) {
				sender.sendMessage(Messages.syntax("/gungame spawns remove <id>"));
				return true;
			}
			
			if (Util.isInt(args[2])) {
				int id = NumberConversions.toInt(args[2]);
				if (gungame.getSettings().getSpawnPoints().containsKey(id)) {
					gungame.removeSpawn(id);
					player.sendMessage(Messages.spawnRemoved(id));
				} else {
					player.sendMessage(Messages.spawnNotExisting(id));
				}
			} else {
				player.sendMessage(Messages.invalidNumber(args[2]));
			}
		}
		
		return true;
	}

	private void sendSuggestions(CommandSender sender) {
		Main.layout().begin();
		Main.layout().suggestion("gungame spawns add", this.descAdd);
		Main.layout().suggestion("gungame spawns tp <id>", this.descTp);
		Main.layout().suggestion("gungame spawns remove <id>", this.descRemove);
		Main.layout().commit(sender);
	}

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("This argument makes it possible to simply manage spawn points.");
		layout.line("/gungame spawns add $- " + this.descAdd);
		layout.line("/gungame spawns tp <id> $- " + this.descTp);
		layout.line("/gungame spawns remove <id> $- " + this.descRemove);
	}
}
