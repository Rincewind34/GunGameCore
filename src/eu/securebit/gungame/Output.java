package eu.securebit.gungame;

import java.util.List;

import lib.securebit.InfoLayout;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public interface Output {
	
	public static Output create(InfoLayout layout) {
		return Output.create(layout, Bukkit.getConsoleSender());
	}
	
	public static Output create(InfoLayout layout, CommandSender receiver) {
		return Output.create(layout, new CommandSender[] { receiver });
	}
	
	public static Output create(InfoLayout layout, CommandSender... receivers) {
		Output output = new CraftOutput(layout);
		
		for (CommandSender sender : receivers) {
			output.addReceiver(sender);
		}
		
		return output;
	}
	
	
	public abstract void insert(String msg);
	
	public abstract void insert(String msg, String... variables);
	
	public abstract void insertRaw(String msg);
	
	public abstract void insertException(Throwable throwable);
	
	public abstract void printBarrier();
	
	public abstract void printEmptyLine();
	
	public abstract void addReceiver(CommandSender sender);
	
	public abstract InfoLayout getLayout();
	
	public abstract List<CommandSender> getReceivers();
	
}
