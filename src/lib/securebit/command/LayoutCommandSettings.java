package lib.securebit.command;

import lib.securebit.InfoLayout;

public final class LayoutCommandSettings implements CommandSettings {

	private final String messageNoPermission;
	private final String messageSyntax;
	private final String messageOnlyPlayer;
	private final String messageDefault;
	
	private final InfoLayout layout;

	public LayoutCommandSettings(InfoLayout layout) {
		this.layout = layout;

		this.messageNoPermission = this.layout.format("\\pre-You do not have the permission to do that!-");
		this.messageSyntax = this.layout.format("\\pre-Syntax: %s-");
		this.messageOnlyPlayer = this.layout.format("\\pre-You have to be a player!-");
		this.messageDefault = this.layout.format("\\pre-Please use a given argument!-");
	}
	
	@Override
	public String getMessageNoPermission() {
		return this.messageNoPermission;
	}

	@Override
	public String getMessageSyntax() {
		return this.messageSyntax;
	}

	@Override
	public String getMessageOnlyPlayer() {
		return this.messageOnlyPlayer;
	}

	@Override
	public String getMessageDefault() {
		return this.messageDefault;
	}
}
