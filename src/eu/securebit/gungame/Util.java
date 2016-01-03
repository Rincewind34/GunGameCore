package eu.securebit.gungame;

import lib.securebit.InfoLayout;
import lib.securebit.game.GameState;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.securebit.gungame.game.states.DisabledStateEdit;

public class Util {
	
	public static boolean isInt(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public static String parseBoolean(boolean input, InfoLayout layout) {
		return Util.parseBoolean(input, layout, false);
	}
	
	public static String parseBoolean(boolean input, InfoLayout layout, boolean invert) {
		return input ? ((!invert ? "+" : "-") + "true"  + (!invert ? "+" : "-")) : ((!invert ? "-" : "+") + "false" + (!invert ? "-" : "+"));
	}
	
	public static void stageInformation(InfoLayout layout) {
		layout.category("Gerneral");
		layout.line("Running: " + Util.parseBoolean(!(Main.instance().getGameStateManager().getCurrent() instanceof DisabledStateEdit), layout));
		layout.line("Phase: " + Main.instance().getGameStateManager().getCurrent().getName());
		layout.line("Online: " + Bukkit.getOnlinePlayers().size());
		layout.category("Detail");
		
		GameState current = Main.instance().getGameStateManager().getCurrent();
		current.stageInformation(layout);
		
		layout.barrier();
	}
	
	public static void startCalculation(Player player, int delay) {
		Bukkit.getScheduler().runTaskLater(Main.instance(), () -> {
			if (player == null || Bukkit.getPlayerExact(player.getName()) == null) {
				Main.instance().getGame().handleDisconnect(player);
			} else {
				if (delay >= 100L) {
					if (Main.DEBUG) {
						System.err.println("Unable to handle disconnect of player " + player.getName());
					}
				}
				
				if (delay >= 20L) {
					player.kickPlayer("");
				}
				
				Util.startCalculation(player, delay * 2);
			}
		}, delay);
	}
	
}
