package eu.securebit.gungame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lib.securebit.InfoLayout;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class CraftOutput implements Output {
	
	private InfoLayout layout;
	
	private List<CommandSender> receivers;
	
	public CraftOutput(InfoLayout layout) {
		this.receivers = new ArrayList<>();
		this.layout = layout;
	}
	
	@Override
	public void insert(String msg) {
		this.insertRaw(this.layout.format("\\pre" + msg));
	}

	@Override
	public void insert(String msg, String... variables) {
		Object[] formated = new String[variables.length];
	
		for (int i = 0; i < variables.length; i++) {
			formated[i] = InfoLayout.replaceKeys(variables[i]);
		}
		
		this.insert(String.format(msg, formated));
	}

	@Override
	public void insertRaw(String msg) {
		for (CommandSender sender : this.receivers) {
			sender.sendMessage(msg);
		}
	}
	
	@Override
	public void insertException(Throwable throwable) {
		if (this.receivers.contains(Bukkit.getConsoleSender())) {
			throwable.printStackTrace();
		}
		
		for (CommandSender sender : this.receivers) {
			if (sender.equals(Bukkit.getConsoleSender())) {
				continue;
			}
			
			sender.sendMessage(this.layout.format("An exception occured: " + throwable.getClass().getSimpleName() + " (" + 
					throwable.getMessage() != null ? throwable.getMessage() : ">> NULL <<"));
		}
	}

	@Override
	public void printBarrier() {
		for (CommandSender sender : this.receivers) {
			this.layout.begin();
			this.layout.barrier();
			this.layout.commit(sender);
		}
	}

	@Override
	public void printEmptyLine() {
		this.insert("");
	}
	
	@Override
	public void addReceiver(CommandSender sender) {
		this.receivers.add(sender);
	}
	
	@Override
	public InfoLayout getLayout() {
		return this.layout;
	}
	
	@Override
	public List<CommandSender> getReceivers() {
		return Collections.unmodifiableList(this.receivers);
	}

}
