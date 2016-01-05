package eu.securebit.gungame.commands;

import lib.securebit.InfoLayout;
import lib.securebit.command.Argument;
import eu.securebit.gungame.Main;

public abstract class CustomArgument extends Argument<Main> {

	public CustomArgument() {
		super(Main.instance());
	}
	
	public abstract void stageInformation(InfoLayout layout);

}
