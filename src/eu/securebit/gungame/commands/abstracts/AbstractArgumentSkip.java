package eu.securebit.gungame.commands.abstracts;

import lib.securebit.InfoLayout;
import lib.securebit.game.GameStateManager;

import org.bukkit.command.CommandSender;

import eu.securebit.gungame.commands.CustomArgument;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.game.states.DisabledStateEdit;
import eu.securebit.gungame.util.CoreMessages;
import eu.securebit.gungame.util.Permissions;

public abstract class AbstractArgumentSkip extends CustomArgument {
	
	@Override
	public String getPermission() {
		return Permissions.commandGunGameSkip();
	}

	@Override
	public boolean isOnlyForPlayer() {
		return false;
	}

	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Skips the current gamestate and jumps to the next.");
	}
	
	protected boolean executeCore(CommandSender sender, GunGame gungame) {
		GameStateManager<?> manager = gungame.getManager();
		
		if (!(manager.getCurrent() instanceof DisabledStateEdit)) {
			if (manager.getSize() - 1 == manager.getCurrentIndex()) {
				manager.setRunning(false);
			} else {
				manager.next();
			}
			
			sender.sendMessage(CoreMessages.gamestateSkiped());
			sender.sendMessage(CoreMessages.currentGamestate(manager.getCurrent().getName()));
		} else {
			sender.sendMessage(CoreMessages.wrongMode("EditMode"));
		}
		
		return true;
	}
	
}
