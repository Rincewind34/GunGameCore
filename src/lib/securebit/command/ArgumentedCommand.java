package lib.securebit.command;

import java.util.HashMap;

import lib.securebit.Validate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ArgumentedCommand extends BasicCommand {
	
	private HashMap<String, Argument<?>> arguments;
	
	private DefaultExecutor executor;
	
	public ArgumentedCommand(String name, CommandSettings settings, Plugin plugin) {
		super(name, settings, plugin);
		
		this.arguments = new HashMap<String, Argument<?>>();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase(this.getName())) {
			if (args.length != 0) {
				
				if (this.getPermission() != null && !sender.hasPermission(this.getPermission())) {
					sender.sendMessage(this.getSettings().getMessageNoPermission());
					return true;
				}
				
				for (String name : this.getArguments().keySet()) {
					if (args[0].equals(name)) {
						Argument<?> arg = this.getArgument(name);
						
						if ((arg.isOnlyForPlayer() && sender instanceof Player) || (!arg.isOnlyForPlayer())) {
							if (arg.getPermission() == null) {
								if (!arg.execute(sender, command, label, args)) {
									sender.sendMessage(String.format(this.getSettings().getMessageSyntax(), arg.getSyntax()));
								}
								return true;
							} else {
								if (sender.hasPermission(arg.getPermission())) {
									if (!arg.execute(sender, command, label, args)) {
										sender.sendMessage(String.format(this.getSettings().getMessageSyntax(), arg.getSyntax()));
									}
									return true;
								} else {
									sender.sendMessage(this.getSettings().getMessageNoPermission());
									return true;
								}
							}
						} else {
							sender.sendMessage(this.getSettings().getMessageOnlyPlayer());
							return true;
						}
					}
				}
				
				sender.sendMessage(this.getSettings().getMessageDefault());
			} else {
				if (this.executor != null) {
					return this.executor.onExecute(sender, command, label, args);
				}
			}
		}
		
		return true;
	}
	
	public void registerArgument(String name, Argument<?> arg) {
		Validate.notNull(name, "Name cannot be null!");
		Validate.notNull(arg, "Argument cannot be null!");

		this.arguments.put(name, arg);
	}
	
	public HashMap<String, Argument<?>> getArguments() {
		return this.arguments;
	}

	public Argument<?> getArgument(String name) {
		Validate.notNull(name, "Name cannot be null!");

		return this.arguments.get(name);
	}
	
	public void setExecutor(DefaultExecutor executor) {
		this.executor = executor;
	}
	

}
