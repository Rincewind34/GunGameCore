package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;
import lib.securebit.command.Argument;

import org.bukkit.plugin.Plugin;

import eu.securebit.gungame.framework.Core;

public abstract class CustomArgument extends Argument<Plugin> {

	public CustomArgument() {
		super(Core.getPlugin());
	}
	
	public abstract void stageInformation(InfoLayout layout);

}
