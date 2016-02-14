package eu.securebit.gungame.commands.abstracts;

import lib.securebit.InfoLayout;

import org.bukkit.command.CommandSender;

import eu.securebit.gungame.commands.CustomArgument;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.util.CoreMessages;
import eu.securebit.gungame.util.Permissions;

public abstract class AbstractArgumentMute extends CustomArgument {

	@Override
	public String getPermission() {
		return Permissions.commandGunGameMute();
	}

	@Override
	public boolean isOnlyForPlayer() {
		return false;
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Mutes the game. As result, the plugin will not send gamemessages in the console.");
	}
	
	protected boolean executeCore(CommandSender sender, GunGame gungame, String givenMuteState) {
		String muteState;
		
		if (givenMuteState == null) {
			muteState = gungame.isMuted() ? "off" : "on";
		} else {
			muteState = givenMuteState.toLowerCase();
		}
		
		if (muteState.equals("on")) {
			if (!gungame.isMuted()) {
				gungame.mute(true);
			} else {
				sender.sendMessage(CoreMessages.alreadyMuted());
				return true;
			}
		} else if (muteState.equals("off")) {
			if (gungame.isMuted()) {
				gungame.mute(false);
			} else {
				sender.sendMessage(CoreMessages.alreadyUnmuted());
				return true;
			}
		} else {
			return false;
		}
		
		sender.sendMessage(CoreMessages.muteSwitch(muteState));
		return true;
	}
	
}
