package eu.securebit.gungame.commands.abstracts;

import lib.securebit.InfoLayout;

import org.bukkit.command.CommandSender;

import eu.securebit.gungame.commands.CustomArgument;
import eu.securebit.gungame.game.CraftGunGame;
import eu.securebit.gungame.game.states.DisabledStateEdit;
import eu.securebit.gungame.util.CoreMessages;
import eu.securebit.gungame.util.Permissions;

public abstract class AbstractArgumentToggle extends CustomArgument {
	
	@Override
	public String getPermission() {
		return Permissions.commandGunGameToggle();
	}

	@Override
	public boolean isOnlyForPlayer() {
		return false;
	}

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Toggles between EditMode / GameMode.");
	}
	
	protected boolean executeCore(CommandSender sender, CraftGunGame gungame) {
		if (gungame.getManager().getCurrent() instanceof DisabledStateEdit) {
			gungame.setEditMode(false);
			
			sender.sendMessage(CoreMessages.changeMode("GameMode"));
			sender.sendMessage(CoreMessages.suggestReload());
		} else {
			gungame.setEditMode(true);
			
			sender.sendMessage(CoreMessages.changeMode("EditMode"));
			sender.sendMessage(CoreMessages.suggestReload());
		}
		
		return true;
	}

}
