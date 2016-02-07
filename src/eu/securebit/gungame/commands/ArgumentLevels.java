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

public class ArgumentLevels extends CustomArgument {

	private final String descLoad;
	private final String descSave;
	private final String descDelete;
	
	public ArgumentLevels() {
		this.descLoad = "";
		this.descSave = "";
		this.descDelete = "";
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
		
		if (!Core.isFrameEnabled()) {
			player.sendMessage(CoreMessages.frameDisabled());
			return true;
		}
		
		if (!Main.instance().getFrame().isInGame(player)) {
			player.sendMessage(CoreMessages.notInGame());
			return true;
		}
		
		GunGame gungame = Main.instance().getFrame().getGame(player);
		
		if (args.length == 1) {
			this.sendSuggestions(player);
			return true;
		} else if (args.length >= 2) {
			if (args[1].equals("load")) {
				if (args.length == 3) {
					if (Util.isInt(args[2])) {
						int id = NumberConversions.toInt(args[2]);
						
						if (!gungame.getLevelManager().exists(id)) {
							player.sendMessage(CoreMessages.levelNotExists(id));
						} else {
							gungame.getLevelManager().equipPlayer(player, id);
							player.sendMessage(CoreMessages.levelGiven(id));
						}
					} else {
						player.sendMessage(CoreMessages.invalidNumber(args[2]));
					}
				} else {
					player.sendMessage(CoreMessages.syntax("/gungame levels load <id>"));
				}
			} else if (args[1].equals("save")) {
				int id = -1;
				int nextId = gungame.getLevelManager().getLevelCount() + 1;
				
				if (args.length == 2) {
					id = nextId;
				} else if (args.length == 3) {
					if (Util.isInt(args[2])) {
						id = NumberConversions.toInt(args[2]);
					} else {
						player.sendMessage(CoreMessages.invalidNumber(args[2]));
						return true;
					}
				} else {
					player.sendMessage(CoreMessages.syntax("/gungame levels save [id]"));
					return true;
				}
				
				if (id <= nextId) {
					if (id > 0) {
						gungame.getLevelManager().saveLevel(player, id);
						player.sendMessage(CoreMessages.levelSaved(id));
					} else {
						player.sendMessage(CoreMessages.greaterNull());
					}
				} else {
					player.sendMessage(CoreMessages.nextLevelId(nextId));
				}
			} else if (args[1].equals("delete")) {
				int count = 1; // default
				
				if (args.length == 3) {
					if (Util.isInt(args[2])) {
						count = NumberConversions.toInt(args[2]);
					} else {
						player.sendMessage(CoreMessages.invalidNumber(args[2]));
						return true;
					}
				} else if (args.length != 2) {
					player.sendMessage(CoreMessages.syntax("/gungame levels delete [count = 1]"));
					return true;
				}
				
				for (int i = 0; i < count; i++) {
					if (!gungame.getLevelManager().deleteHighestLevel()) {
						player.sendMessage(CoreMessages.noLevelToRemove());
						return true;
					} else {
						player.sendMessage(CoreMessages.levelDeleted(gungame.getLevelManager().getLevelCount() + 1));
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
		layout.line("This argument provides some methods to");
		layout.line("manage your levels. The first level has the id 1.");
		layout.line("");
		layout.line("*$-\"$- load <id>*");
		layout.line("Sets your inventory items to the given level.");
		layout.line("");
		layout.line("*$-\"$- save [id]*");
		layout.line("Saves your inventory as {id}. If the parameter {id}");
		layout.line("is not present, the level will be added to the");
		layout.line("current levellist.");
		layout.line("");
		layout.line("*$-\"$- delete [count]*");
		layout.line("Deletes {count} levels from the levellist. If the ");
		layout.line("parameter is not present, the command will delete");
		layout.line("one level.");
		layout.line("");
		layout.line("*Examples (There are 6 levels already created)*");
		layout.line("  /gg save §8=> §7Creates the 7'th level.");
		layout.line("  /gg save 5 §8=> §7Overrides the 5'th level.");
		layout.line("  /gg delete §8=> §7Deletes level 6.");
		layout.line("  /gg delete 3 §8=> §7Deletes the levels 6, 5 and 4.");
	}
}
