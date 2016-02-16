package eu.securebit.gungame.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lib.securebit.InfoLayout;
import lib.securebit.game.GameState;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.framework.Core;
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
		layout.category("Gerneral");
		layout.line("Running: " + Util.parseBoolean(!(gungame.getManager().getCurrent() instanceof DisabledStateEdit), layout));
		layout.line("Phase: " + gungame.getManager().getCurrent().getName());
		layout.line("Online: " + gungame.getPlayers().size());
		layout.line("Muted: " + Util.parseBoolean(gungame.isMuted(), layout));
		layout.line("");
		layout.line("*Game*");
		layout.line("Spawns: " + gungame.getMap().getSpawnPointCount());
		layout.line("Levels: " + gungame.getLevelManager().getLevelCount());
		layout.category("Detail");
		
		GameState current = gungame.getManager().getCurrent();
		current.stageInformation(layout);
		
		layout.barrier();
	}
	
	public static void startCalculation(Player player, int delay, GunGame gungame) {
		Bukkit.getScheduler().runTaskLater(Core.getPlugin(), () -> {
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
	
	public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map) {
		List<Entry<K, V>> list = new LinkedList<>(map.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
			
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		
		return result;
	}
	
}
