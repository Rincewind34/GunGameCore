package eu.securebit.gungame.util;

import lib.securebit.InfoLayout;
import lib.securebit.game.GameState;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.GunGame;
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
	
	public static void stageInformation(InfoLayout layout, GunGame gungame) {
		layout.category("Server");
		layout.line("Core$-Version: " + InfoLayout.replaceKeys(Main.instance().getDescription().getVersion()));
		layout.line("Frame$-Name: " + InfoLayout.replaceKeys(Main.instance().getFrame().getName()));
		layout.line("Frame$-Version: " + InfoLayout.replaceKeys(Main.instance().getFrame().getVersion()));
		layout.category("Gerneral");
		layout.line("Running: " + Util.parseBoolean(!(gungame.getManager().getCurrent() instanceof DisabledStateEdit), layout));
		layout.line("Phase: " + gungame.getManager().getCurrent().getName());
		layout.line("Online: " + gungame.getPlayers().size());
		layout.line("Muted: " + Util.parseBoolean(gungame.isMuted(), layout));
		layout.category("Config");
		layout.line("Spawns: " + gungame.getLocationManager().getSpawnPointCount());
		layout.line("Levels: " + gungame.getLevelManager().getLevelCount());
		layout.category("Detail");
		
		GameState current = gungame.getManager().getCurrent();
		current.stageInformation(layout);
		
		layout.barrier();
	}
	
	public static void startCalculation(Player player, int delay, GunGame gungame) {
		Bukkit.getScheduler().runTaskLater(Main.instance(), () -> {
			if (player == null || Bukkit.getPlayerExact(player.getName()) == null) {
				gungame.calculateGameState();
			} else {
				if (delay >= 100L) {
					if (Main.DEBUG) {
						System.err.println("Unable to handle disconnect of player " + player.getName());
					}
				}
				
				Util.startCalculation(player, delay * 2, gungame);
			}
		}, delay);
	}
	
	public static String stripPath(String path) {
		if (path.contains("plugins/GunGame")) {
			int i = path.lastIndexOf("plugins/GunGame/");
			return path.substring(i + "plugins/GunGame/".length(), path.length());
		} else {
			return path;
		}
	}
	
}
