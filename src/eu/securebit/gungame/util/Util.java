package eu.securebit.gungame.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lib.securebit.InfoLayout;
import lib.securebit.game.GameState;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
		layout.category("Config");
		layout.line("Spawns: " + gungame.getSettings().getSpawnPoints().size());
		layout.line("Levels: " + gungame.getSettings().getLevels().size());
		layout.category("Detail");
		
		GameState current = gungame.getManager().getCurrent();
		current.stageInformation(layout);
		
		layout.barrier();
	}
	
	public static void startCalculation(Player player, int delay, GunGame gungame) {
		Bukkit.getScheduler().runTaskLater(Main.instance(), () -> {
			if (player == null || Bukkit.getPlayerExact(player.getName()) == null) {
				gungame.handleDisconnect(player);
			} else {
				if (delay >= 100L) {
					if (Main.DEBUG) {
						System.err.println("Unable to handle disconnect of player " + player.getName());
					}
				}
				
				if (delay >= 20L) {
					player.kickPlayer("");
				}
				
				Util.startCalculation(player, delay * 2, gungame);
			}
		}, delay);
	}
	
	public static List<Location> getSpawns(GunGame gungame) {
		Collection<Location> locs = gungame.getSettings().getSpawnPoints().values();
		
		return Arrays.asList(locs.toArray(new Location[locs.size()]));
	}
	
}
