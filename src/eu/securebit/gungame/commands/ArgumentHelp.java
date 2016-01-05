package eu.securebit.gungame.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Permissions;
import lib.securebit.InfoLayout;
import lib.securebit.command.BasicCommand;

public class ArgumentHelp extends CustomArgument {
	
	private final BasicCommand handle;
	
	public ArgumentHelp(BasicCommand handle) {
		this.handle = handle;
	}
	
	@Override
	public String getSyntax() {
		return "/gungame help [argument]";
	}

	@Override
	public String getPermission() {
		return Permissions.commandGunGameHelp();
	}

	@Override
	public boolean isOnlyForPlayer() {
		return false;
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = Main.layout();
		if (args.length == 1) {
			layout.begin();
			layout.category("Commands");
			
			this.handle.getArguments().forEach((name, arg) -> {
				CustomArgument argument = (CustomArgument) arg;
				layout.line("*" + argument.getSyntax() + "* (" + argument.getPermission() + ")");
			});
			
			layout.line("");
			layout.line("Type */gungame help <argument>* for further information.");
			layout.barrier();
			layout.commit(sender);
			
			return true;
		}
		
		if (args.length == 2) {
			String name = args[1];		
			
			if (!this.handle.getArguments().containsKey(name)) {
				layout.message(sender, "-There is no argument with the name '" + name + "'.*");
				return true;
			}
			
			CustomArgument argument = (CustomArgument) this.handle.getArgument(name);
			
			layout.begin();
			layout.category("Argument: " + name);
			layout.line("*Syntax:* " + argument.getSyntax());
			layout.line("*Permission:* " + argument.getPermission());
			layout.line("");
			argument.stageInformation(layout);
			layout.barrier();
			layout.commit(sender);
			
			return true;
		}
		
		return false;
	}

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Shows you a little ingame reference about commands & permissions.");
	}

}
