package lib.securebit.command;

import java.lang.reflect.Field;
import java.util.Map;

import lib.securebit.ReflectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

public class ReflectionCommand {
	
	public static final Class<?> CLASS_CRAFTSERVER = ReflectionUtil.getCraftBukkitClass("CraftServer");
	public static final Class<?> CLASS_SIMPLECOMMANDMAP = ReflectionUtil.getClass("org.bukkit.command.SimpleCommandMap");
	
	public static final Field FIELD_COMMANDMAP = ReflectionUtil.getDeclaredField(ReflectionCommand.CLASS_CRAFTSERVER, "commandMap");
	public static final Field FIELD_COMMANDS = ReflectionUtil.getDeclaredField(ReflectionCommand.CLASS_SIMPLECOMMANDMAP, "knownCommands");
	
	public static final SimpleCommandMap COMMAND_MAP = (SimpleCommandMap) ReflectionUtil.createObject(ReflectionCommand.FIELD_COMMANDMAP, Bukkit.getServer());
	
	@SuppressWarnings("unchecked")
	public static final Map<String, Command> COMMANDS = (Map<String, Command>) ReflectionUtil.createObject(ReflectionCommand.FIELD_COMMANDS, ReflectionCommand.COMMAND_MAP);
	
	public static boolean check() {
		return ReflectionCommand.COMMAND_MAP != null && ReflectionCommand.COMMANDS != null;
	}
	
}
