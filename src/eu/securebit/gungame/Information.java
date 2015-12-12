package eu.securebit.gungame;

import lib.securebit.InfoLayout;
import lib.securebit.game.GameState;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.securebit.gungame.exception.MalformedConfigException;
import eu.securebit.gungame.game.states.DisabledStateEdit;
import eu.securebit.gungame.game.states.GameStateEnd;
import eu.securebit.gungame.game.states.GameStateGrace;
import eu.securebit.gungame.game.states.GameStateIngame;
import eu.securebit.gungame.game.states.GameStateLobby;

public class Information {
		
	public static void gameStatus(InfoLayout layout) {
		Information.gameStatus(layout, true);
	}
	
	public static void gameStatus(InfoLayout layout, boolean category) {
		if (category) {
			layout.category("Game-Status");
		}
		
		try {
			Main.instance().getFileConfig().validate();
			layout.line("config.yml: " + Information.parseBoolean(true, layout));
		} catch (MalformedConfigException exception) {
			layout.line("config.yml: " + Information.parseBoolean(false, layout));
			layout.line("  $- " + InfoLayout.replaceKeys(exception.getMessage()));
		}
		
		try {
			Main.instance().getFileLevels().validate();
			layout.line("levels.yml: " + Information.parseBoolean(true, layout));
		} catch (MalformedConfigException exception) {
			layout.line("levels.yml: " + Information.parseBoolean(false, layout));
			layout.line("  $- " + InfoLayout.replaceKeys(exception.getMessage()));
		}
		
		if (Main.instance().getFileConfig().getSpawns().size() < 1) {
			layout.line("spawns: " + Information.parseBoolean(false, layout));
			layout.line("  $- You have to set at least one spawn location!");
		} else {
			layout.line("spawns: " + Information.parseBoolean(true, layout));
		}
		
		if (Main.instance().getFileConfig().isEditMode()) {
			layout.line("value: " + Information.parseBoolean(true, layout, true));
			layout.line("  $- Turn the value 'EditMode' in 'config.yml' to *false*!");
		} else {
			layout.line("value: " + Information.parseBoolean(false, layout, true));
		}
	}
	
	public static void generalInfo(InfoLayout layout) {
		layout.category("Gerneral");
		layout.line("Running: " + Information.parseBoolean(!(Main.instance().getGameStateManager().getCurrent() instanceof DisabledStateEdit), layout));
		layout.line("Phase: " + Util.getGameStateName());
		layout.line("Online: " + Bukkit.getOnlinePlayers().size());
		layout.category("Detail");
		
		GameState current = Main.instance().getGameStateManager().getCurrent();
		
		if (current instanceof GameStateLobby) {
			layout.line("Enough players: " + Information.parseBoolean(Bukkit.getOnlinePlayers().size() >= Main.instance().getFileConfig().getMinPlayers(), layout));
			layout.line("Seconds left: " + ((GameStateLobby) current).getSecondsLeft());
		} else if (current instanceof GameStateGrace) {
			layout.line("Seconds left: " + ((GameStateGrace) current).getSecondsLeft());
		} else if (current instanceof GameStateIngame) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				layout.line("  $- " + player.getName() + "(" + Main.instance().getGame().getCurrentLevel(player) + ")");
			}
		} else if (current instanceof GameStateEnd) {
			layout.line("Winner: " + Main.instance().getGame().getWinner());
			layout.line("Seconds left: " + ((GameStateEnd) current).getSecondsLeft());
		} else if (current instanceof DisabledStateEdit) {
			Information.gameStatus(layout, false);
		}
	}
	
	private static String parseBoolean(boolean input, InfoLayout layout) {
		return Information.parseBoolean(input, layout, false);
	}
	
	private static String parseBoolean(boolean input, InfoLayout layout, boolean invert) {
		return input ? ((!invert ? "+" : "-") + "true"  + (!invert ? "+" : "-")) : ((!invert ? "-" : "+") + "false" + (!invert ? "-" : "+"));
	}
	
}
