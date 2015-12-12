package eu.securebit.gungame.test;

import lib.securebit.command.Argument;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;

public class ArgumentTestMessages extends Argument<Main> {

	public ArgumentTestMessages() {
		super(Main.instance());
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		sender.sendMessage("�7Testing...");
		
		sender.sendMessage("�7Prefix: " + super.plugin.getFileConfig().getPrefix());
		sender.sendMessage(super.plugin.getFileConfig().getMessageCountdownEnd(20));
		sender.sendMessage(super.plugin.getFileConfig().getMessageGraceCountdown(13));
		sender.sendMessage(super.plugin.getFileConfig().getMessageGraceEnd());
		sender.sendMessage(super.plugin.getFileConfig().getMessageGraceStart());
		sender.sendMessage(super.plugin.getFileConfig().getMessageJoin(player));
		sender.sendMessage(super.plugin.getFileConfig().getMessageKillBroadcast(player, player));
		sender.sendMessage(super.plugin.getFileConfig().getMessageLobbyCountdown(91));
		sender.sendMessage(super.plugin.getFileConfig().getMessageMapTeleport());
		sender.sendMessage(super.plugin.getFileConfig().getMessageNaturalDeath(player));
		sender.sendMessage(super.plugin.getFileConfig().getMessageQuit(player));
		sender.sendMessage(super.plugin.getFileConfig().getMessageRespawn(25));
		sender.sendMessage(super.plugin.getFileConfig().getMessageWinner(player));
		
		sender.sendMessage("�7Done!");
		return true;
	}
	
	// Really? Of course.
	@Override public String getSyntax() {return null;}
	@Override public String getPermission() {return null;}
	@Override public boolean isOnlyForPlayer() {return true;}
}