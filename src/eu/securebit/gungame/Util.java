package eu.securebit.gungame;

import org.bukkit.Bukkit;

import eu.securebit.gungame.game.states.DisabledStateEdit;
import lib.securebit.InfoLayout;
import lib.securebit.game.GameState;

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
		layout.line("Phase: " + Main.instance().getGameStateManager().getCurrentName());
		layout.line("Online: " + Bukkit.getOnlinePlayers().size());
		layout.category("Detail");
		
		GameState current = Main.instance().getGameStateManager().getCurrent();
		current.stageInformation(layout);
		
		layout.barrier();
	}
	
}
