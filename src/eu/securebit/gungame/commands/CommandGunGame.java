package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;
import lib.securebit.command.BasicCommand;
import lib.securebit.command.BasicCommand.DefaultExecutor;
import lib.securebit.command.LayoutCommandSettings;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.util.Permissions;

public class CommandGunGame extends BasicCommand implements DefaultExecutor {
	
	/*
	 * /gungame {
	 * 		help [argument]
	 * 
	 * 		spawns {
	 * 			add - adds a new spawn using your current location; you'll get back the generated id
	 * 			remove <id> - removes a specific spawn by the given id
	 * 			tp <id> - teleports you to the specified spawn point
	 * 		}
	 * 		
	 * 		lobby {
	 * 			set - defines the lobby location where joining players will be teleported to (before game starts)
	 * 			tp - teleports you to the lobby location
	 * 		}
	 * 
	 * 		levels {
	 * 			load <level> - Sets your inventory items to the given level
	 * 			save [level] - If a level is specified, this command will change the items for these level.
	 * 						   Otherwise, a new level is created using the items from your inventory.
	 * 			delete [count = 1] - Deletes {count} levels from the stack. 
	 * 		}
	 * }
	 * 
	 * 
	 */
	
	public CommandGunGame() {
		super("gungame", new LayoutCommandSettings(Main.layout()), Main.instance());
		
		this.setAliases("gg");
		this.setDescription("GunGame's main command.");
		this.setPermission(Permissions.commandGungame());
		this.setDefaultExecutor(this);
		this.registerArgument("help", new ArgumentHelp(this));
		this.registerArgument("spawns", new ArgumentSpawns());
		this.registerArgument("levels", new ArgumentLevels());
		this.registerArgument("lobby", new ArgumentLobby());
	}

	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		PluginDescriptionFile desc = Main.instance().getDescription();
		
		Main.layout().begin();
		Main.layout().barrier();
		Main.layout().line("Version *" + InfoLayout.replaceKeys(desc.getVersion()) + "*");
		Main.layout().line("Developed by *SecureBit*");
		Main.layout().line("All rights reserved.");
		Main.layout().barrier();
		Main.layout().line("Show documentation for further information.");
		Main.layout().line("Check */gungame help* out for command information");
		Main.layout().commit(sender);
		Main.layout().barrier();
		return true;
	}

}
