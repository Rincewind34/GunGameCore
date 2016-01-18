package lib.securebit.command;

import lib.securebit.command.BasicCommand.DefaultExecutor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class UnargumentedCommand extends BasicCommand implements DefaultExecutor {
	
	private boolean onlyPlayers;
	
	public UnargumentedCommand(String name, CommandSettings settings, Plugin plugin) {
		super(name, settings, plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase(this.getName())) {
			if (this.getPermission() != null && !sender.hasPermission(this.getPermission())) {
				sender.sendMessage(this.getSettings().getMessageNoPermission());
				return true;
			}
			
			if (this.onlyPlayers && !(sender instanceof Player)) {
				sender.sendMessage(this.getSettings().getMessageOnlyPlayer());
			}
				
			boolean syntax = this.onExecute(sender, command, label, args);
			
			if (syntax) {
				sender.sendMessage(String.format(this.getSettings().getMessageSyntax(), this.getUsage()));
			}
		}
		
		return true;
	}
	
	public void setOnlyPlayers(boolean value) {
		this.onlyPlayers = value;
	}
	
}
