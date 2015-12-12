package eu.securebit.gungame.test;

import lib.securebit.InfoLayout;
import lib.securebit.command.BasicCommand;
import lib.securebit.command.BasicCommand.DefaultExecutor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.gungame.Main;

public class CommandTest implements DefaultExecutor {

	private final BasicCommand instance;
	
	public CommandTest(BasicCommand instance) {
		this.instance = instance;
		this.instance.registerArgument("levels", new ArgumentTestLevels());
		this.instance.registerArgument("messages", new ArgumentTestMessages());
		this.instance.registerArgument("locations", new ArgumentTestLocations());
		this.instance.registerArgument("itemserializer", new ArgumentTestItemSerializer());
		this.instance.registerArgument("skip", new ArgumentTestSkip());
	}
	
	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = Main.layout();
		
		layout.begin();
		layout.category("GunGame Test Suite");
		layout.line("/ggtest levels $- Test level item system");
		layout.line("/ggtest messages $- Test config messages");
		layout.line("/ggtest locations $- Test config locations");
		layout.line("/ggtest itemserializer $- Test the ItemSerializer");
		layout.line("/ggtest skip $- Skips a GameState");
		layout.barrier();
		layout.commit(sender);
		
		return true;
	}
}
