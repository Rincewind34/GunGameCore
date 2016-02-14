package eu.securebit.gungame.commands.abstracts;

import lib.securebit.InfoLayout;

import org.bukkit.command.CommandSender;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.commands.CustomArgument;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.util.CoreMessages;
import eu.securebit.gungame.util.Permissions;
import eu.securebit.gungame.util.Util;

public abstract class AbstractArgumentInfo extends CustomArgument {
	
	@Override
	public String getPermission() {
		return Permissions.commandGunGameInfo();
	}

	@Override
	public boolean isOnlyForPlayer() {
		return false;
	}

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Shows information about the current state");
	}
	
	protected boolean executeCore(CommandSender sender, GunGame gungame) {
		if (!gungame.isFileReady()) {
			sender.sendMessage(CoreMessages.gamefileNotPresent(gungame.getFileGameConfig()));
			return true;
		}
		
		InfoLayout layout = Main.layout();
		layout.begin();
		Util.stageInformation(layout, gungame);
		layout.commit(sender);
		
		return true;
	}

}
