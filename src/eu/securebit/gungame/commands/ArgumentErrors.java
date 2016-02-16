package eu.securebit.gungame.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lib.securebit.InfoLayout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.errorhandling.layouts.LayoutErrorFixable;
import eu.securebit.gungame.errorhandling.objects.ThrownError;
import eu.securebit.gungame.framework.Core;
import eu.securebit.gungame.util.Permissions;

public class ArgumentErrors extends CustomArgument {

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Prints you all thrown errors.");
		layout.line("");
		layout.line("*Options:*");
		layout.line("  $-m: Send with errormessages.");
		layout.line("  $-i: Send with index.");
		layout.line("  $-s <sorter>: Sorts the ouput.");
		layout.line("  $-c: With causes.");
		layout.line("  $-f: Mark fixable errors.");
		layout.line("  $-a: Works as $-m, $-i, $-c and $-f.");
		layout.line("");
		layout.line("*Examples:*");
		layout.line("  /gg errors $-m");
		layout.line("  /gg errors $-i $-s");
		layout.line("  /gg errors $-s $-f $-c");
	}

	@Override
	public String getSyntax() {
		return "/gungame errors [OPTIONS]";
	}

	@Override
	public String getPermission() {
		return Permissions.commandGunGameErrors();
	}

	@Override
	public boolean isOnlyForPlayer() {
		return false;
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = Main.layout();
		
		for (int i = 0; i < args.length; i++) {
			String element = args[i];
			
			if (element.equals("errors") || element.equals("-m") || element.equals("-s") || element.equals("-i") || element.equals("-f") ||
					element.equals("-c") || element.equals("-a")) {
				continue;
			} else if (i != 0 && args[i - 1].equals("-s")) {
				continue;
			} else {
				return false;
			}
		}
		
		layout.begin();
		layout.category("Errors:");
		
		List<ThrownError> errors = new ArrayList<>();
		boolean withTrigger = false;
		
		if (this.isFlagPresent("s", args)) {
			if (this.getSorter(args) == null) {
				return false;
			}
			
			if (this.getSorter(args).equals("name")) {
				for (ThrownError error : Core.getErrorHandler().getErrors().keySet()) {
					errors.add(error);
				}
				
				Collections.sort(errors);
			} else if (this.getSorter(args).equals("tree")) {
				withTrigger = true;
				
				for (ThrownError error : Core.getErrorHandler().getErrors().keySet()) {
					if (Core.getErrorHandler().getTrigger(error) == null) {
						errors.add(error);
						this.stageTrigger(error, errors);
					}
				}
			} else {
				return false;
			}
		} else {
			for (ThrownError error : Core.getErrorHandler().getErrors().keySet()) {
				errors.add(error);
			}
		}
		
		for (ThrownError error : errors) {
			layout.line(this.getOutput(error, withTrigger ? Core.getErrorHandler().getTrigger(error) != null : false, false, args));
			
			if (this.isFlagPresent("c", args) || this.isFlagPresent("a", args)) {
				if (Core.getErrorHandler().getErrors().get(error) != null) {
					layout.line(this.getOutput(Core.getErrorHandler().getErrors().get(error), false, true, args));
				}
			}
		}
		
		if (this.isFlagPresent("f", args) || this.isFlagPresent("a", args)) {
			layout.line("");
			layout.line("*Fixable errors:*");
			
			boolean printNone = true;
			
			for (ThrownError error : Core.getErrorHandler().getErrors().keySet()) {
				if (error.getLayout() instanceof LayoutErrorFixable) {
					layout.line(this.getOutput(error, false, false, args));
					printNone = false;
				}
			}
			
			if (printNone) {
				layout.line("  $-$- None $-$-");
			}
		}
		
		layout.barrier();
		layout.commit(sender);
		return true;
	}
	
	private int getErrorIndex(ThrownError error) {
		int index = 0;
		
		for (ThrownError target : Core.getErrorHandler().getErrors().keySet()) {
			if (error.equals(target)) {
				return index;
			} else {
				index = index + 1;
			}
		}
		
		return -1;
	}
	
	private boolean isFlagPresent(String flag, String[] args) {
		boolean skip = false;
		
		for (String arg : args) {
			if (skip) {
				skip = false;
				continue;
			}
			
			if (arg.equals("-s")) {
				skip = true;
			}
			
			if (arg.equals("-" + flag)) {
				return true;
			}
		}
		
		return false;
	}
	
	private String getSorter(String[] args) {
		boolean found = false;
		
		for (String arg : args) {
			if (found) {
				return arg;
			}
			
			if (arg.equals("-s")) {
				found = true;
			}
		}
		
		return null;
	}
	
	private String getOutput(ThrownError error, boolean triggered, boolean causes, String[] args) {
		String output = "-" + InfoLayout.replaceKeys(error.getParsedObjectId()) + "-";
		
		if (triggered) {
			output = "=> triggers: " + output;
		}
		
		if (causes) {
			output = "=> caused by: " + output;
		}
		
		if (this.isFlagPresent("m", args) || this.isFlagPresent("a", args)) {
			output = output + " (" + InfoLayout.replaceKeys(error.getParsedMessage()) + ")";
		}
		
		if (this.isFlagPresent("i", args) || this.isFlagPresent("a", args)) {
			output = "[Index: " + this.getErrorIndex(error) + "] " + output;
		}
		
		return output;
	}
	
	private void stageTrigger(ThrownError error, List<ThrownError> errors) {
		ThrownError triggerCause = Core.getErrorHandler().getTriggerCause(error);
		
		if (triggerCause != null) {
			errors.add(triggerCause);
			stageTrigger(triggerCause, errors);
		}
	}
	

}
