package eu.securebit.gungame.commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.util.Level;
import eu.securebit.gungame.util.Messages;
import eu.securebit.gungame.util.Permissions;
import eu.securebit.gungame.util.Util;
import lib.securebit.InfoLayout;

public class ArgumentLevels extends CustomArgument {

	private final String descLoad;
	private final String descSave;
	private final String descDelete;
	
	public ArgumentLevels() {
		this.descLoad = "Sets your inventory items to the given level.";
		this.descSave = "Saves your inventory as {id}.";
		this.descDelete = "Deletes {count} levels from the stack.";
	}

	@Override
	public String getSyntax() {
		return "/gungame levels {load|save|delete}";
	}

	@Override
	public String getPermission() {
		return Permissions.commandGunGameLevels();
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
		Map<Integer, Level> levels = gungame.getSettings().getLevels();
		
		if (args.length == 1) {
			this.sendSuggestions(player);
			return true;
		} else if (args.length >= 2) {
			if (args[1].equals("load")) {
				if (args.length == 3) {
					if (Util.isInt(args[2])) {
						int id = NumberConversions.toInt(args[2]);
						
						if (levels.containsKey(id)) {
							levels.get(id).equip(player);
							player.sendMessage(Messages.levelGiven(id));
						} else {
							player.sendMessage(Messages.levelNotExists(id));
						}
					} else {
						player.sendMessage(Messages.invalidNumber(args[2]));
					}
				} else {
					player.sendMessage(Messages.syntax("/gungame levels load <id>"));
				}
			} else if (args[1].equals("save")) {
				int id = -1;
				int nextId = levels.size() + 1;
				
				if (args.length == 2) {
					id = nextId;
				} else if (args.length == 3) {
					if (Util.isInt(args[2])) {
						id = NumberConversions.toInt(args[2]);
					} else {
						player.sendMessage(Messages.invalidNumber(args[2]));
						return true;
					}
				} else {
					player.sendMessage(Messages.syntax("/gungame levels save [id]"));
					return true;
				}
				
				if (id <= nextId) {
					if (id > 0) {
						gungame.saveLevel(player, id);
						player.sendMessage(Messages.levelSaved(id));
					} else {
						player.sendMessage(Messages.greaterNull());
					}
				} else {
					player.sendMessage(Messages.nextLevelId(nextId));
				}
			} else if (args[1].equals("delete")) {
				int count = 1; // default
				
				if (args.length == 3) {
					if (Util.isInt(args[2])) {
						count = NumberConversions.toInt(args[2]);
					} else {
						player.sendMessage(Messages.invalidNumber(args[2]));
						return true;
					}
				} else if (args.length != 2) {
					player.sendMessage(Messages.syntax("/gungame levels delete [count = 1]"));
					return true;
				}
				
				for (int i = 0; i < count; i++) {
					if (!gungame.deleteLevel()) {
						player.sendMessage(Messages.noLevelToRemove());
						return true;
					} else {
						player.sendMessage(Messages.levelDeleted(gungame.getSettings().getLevels().size()));
					}
				}
			} else {
				this.sendSuggestions(sender);
			}
		} else {
			this.sendSuggestions(sender);
		}
		
		return true;
	}

	private void sendSuggestions(CommandSender sender) {
		Main.layout().begin();
		Main.layout().suggestion("gungame levels load <id>", this.descLoad);
		Main.layout().suggestion("gungame levels save [id]", this.descSave);
		Main.layout().suggestion("gungame levels delete [count = 1]", this.descDelete);
		Main.layout().commit(sender);
	}

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("This argument provides some methods to manage your levels.");
		layout.line("/gungame levels load <id> $- " + this.descLoad);
		layout.line("/gungame levels save [id] $- " + this.descSave);
		layout.line("/gungame levels delete [count = 1] $- " + this.descDelete);
	}
}
