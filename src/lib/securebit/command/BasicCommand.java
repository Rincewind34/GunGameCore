package lib.securebit.command;

import java.util.HashMap;
import java.util.Map;

import lib.securebit.Validate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BasicCommand implements CommandExecutor, PluginIdentifiableCommand {
	
	public abstract static interface DefaultExecutor {
		
		public abstract boolean onExecute(CommandSender sender, Command cmd, String label, String[] args);
		
	}
	
	private HashMap<String, Argument<?>> arguments;
	
	private String description;
	private String usage;
	private String permission;
	private String[] aliases;
	
	private String key;
	
	private DefaultExecutor executor;
	
	private TabCompleter tabCompleter;
	
	private final String name;
	private final CommandSettings settings;
	private final Plugin plugin;
	
	public BasicCommand(String name, CommandSettings settings, Plugin plugin) {
		Validate.notNull(name, "Name cannot be null!");
		Validate.notNull(settings, "Settings cannot be null!");
		Validate.notNull(plugin, "Plugin cannot be null!");

		this.name = name;
		this.plugin = plugin;
		this.settings = settings;
		this.arguments = new HashMap<String, Argument<?>>();
	}

	@Override
	public Plugin getPlugin() {
		return this.plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase(this.name)) {
			if (args.length != 0) {
				
				if (this.permission != null && !sender.hasPermission(this.permission)) {
					sender.sendMessage(this.settings.getMessageNoPermission());
					return true;
				}
				
				for (String name : this.getArguments().keySet()) {
					if (args[0].equals(name)) {
						Argument<?> arg = this.getArgument(name);
						
						if ((arg.isOnlyForPlayer() && sender instanceof Player) || (!arg.isOnlyForPlayer())) {
							if (arg.getPermission() == null) {
								if (!arg.execute(sender, command, label, args)) {
									sender.sendMessage(String.format(this.settings.getMessageSyntax(), arg.getSyntax()));
								}
								return true;
							} else {
								if (sender.hasPermission(arg.getPermission())) {
									if (!arg.execute(sender, command, label, args)) {
										sender.sendMessage(String.format(this.settings.getMessageSyntax(), arg.getSyntax()));
									}
									return true;
								} else {
									sender.sendMessage(this.settings.getMessageNoPermission());
									return true;
								}
							}
						} else {
							sender.sendMessage(this.settings.getMessageOnlyPlayer());
							return true;
						}
					}
				}
				
				sender.sendMessage(this.settings.getMessageDefault());
			} else {
				if (this.executor != null) {
					return this.executor.onExecute(sender, command, label, args);
				}
			}
		}
		
		return true;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setAliases(String... aliases) {
		this.aliases = aliases;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public void setUsage(String usage) {
		this.usage = usage;
	}
	
	public void setTabCompleter(TabCompleter tabCompleter) {
		this.tabCompleter = tabCompleter;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String[] getAliases() {
		return this.aliases;
	}
	
	public String getUsage() {
		return this.usage;
	}
	
	public String getPermission() {
		return this.permission;
	}
	
	public String getName() {
		return this.name;
	}
	
	public TabCompleter getTabCompleter() {
		return this.tabCompleter;
	}
	
	public void create() {
		DynamicCommand dynCmd = null;
		String descr = this.description != null ? this.description : "";
		
		if (this.aliases == null && this.usage == null) {
			dynCmd = new DynamicCommand(this.plugin, this.name, descr, this);
		} else if (this.aliases != null && this.usage == null) {
			dynCmd = new DynamicCommand(this.plugin, this.name, descr, this, this.aliases);
		} else if ( this.aliases == null ) {
			dynCmd = new DynamicCommand(this.plugin, this.name, descr, this.usage, this);
		} else {
			dynCmd = new DynamicCommand(this.plugin, this.name, descr, this.usage, this, this.aliases);
		}
		
		if (this.tabCompleter != null) {
			dynCmd.setTabCompleter(this.tabCompleter);
		} if (!ReflectionCommand.check()) {
			System.out.println("The command " + this.name + " could not be created!");
		}
		
		ReflectionCommand.COMMAND_MAP.register((this.key == null || this.key.isEmpty() || this.key.trim().isEmpty()) ? this.name : this.key, dynCmd);
	}
	
	public void unregister() {
		if (!ReflectionCommand.check()) {
			System.out.println("The command " + this.name + " could not be unregistered!");
		}
		
		Map<String, Command> commands = ReflectionCommand.COMMANDS;
		
		commands.remove(((this.key == null || this.key.isEmpty() || this.key.trim().isEmpty()) ? this.name : this.key) + ":" + this.name);
		commands.remove(this.name);
		
		if (this.aliases != null) {
			for (String alias : aliases) {
				commands.remove(((this.key == null || this.key.isEmpty() || this.key.trim().isEmpty()) ? alias : this.key) + ":" + this.name);
				commands.remove(this.name);
			}
		}
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
	
	public void setDefaultExecutor(DefaultExecutor executor) {
		this.executor = executor;
	}
	
}