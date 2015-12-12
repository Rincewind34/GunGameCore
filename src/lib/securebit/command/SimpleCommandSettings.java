package lib.securebit.command;

public class SimpleCommandSettings implements CommandSettings {

	private final String messagePrefix;
	private final String messageNoPermission;
	private final String messageSyntax;
	private final String messageOnlyPlayer;
	private final String messageDefault;
	
	public SimpleCommandSettings(String prefix) {
		this.messagePrefix = prefix;
		this.messageNoPermission = this.messagePrefix + "§cDu hast hierzu keine Berechtigung.";
		this.messageSyntax = this.messagePrefix + "§cSyntax: %s";
		this.messageOnlyPlayer = this.messagePrefix + "§cDieser Befehl ist nur für Spieler.";
		this.messageDefault = this.messagePrefix + "§cBitte benutze ein spezifisches Argument.";
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
