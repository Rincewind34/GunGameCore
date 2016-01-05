package eu.securebit.gungame;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import eu.securebit.gungame.exception.ScoreboardExcepion;

public class GunGameScoreboard {
	
	public static final String OBJECTIVE_NAME = "gungame_level";
	
	private static Scoreboard board;
	
	static {
		GunGameScoreboard.board = Bukkit.getScoreboardManager().getMainScoreboard();
	}
	
	private static String getFormat() {
		String format = Main.instance().getFileConfig().getScoreboardFormat();
		return ChatColor.translateAlternateColorCodes('&', format);
	}
	
	public static void setup() {
		if (!Main.instance().getFileConfig().isScoreboard()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (GunGameScoreboard.exists()) {
			Objective obj = GunGameScoreboard.board.getObjective(GunGameScoreboard.OBJECTIVE_NAME);
			
			String format = GunGameScoreboard.getFormat();
			
			GunGame game = Main.instance().getGame();
			
			for (Player target : Bukkit.getOnlinePlayers()) {
				try {
					obj.getScore(format.replace("${player}", target.getDisplayName())).setScore(game.getPlayer(target).getLevel());
				} catch (Exception ex) {
					if (Main.DEBUG) {
						System.err.println("Error occured while set the score of the player " + target.getName() + " as " + target.getDisplayName());
						ex.printStackTrace();
					}
					
					continue;
				}
			}
			
			GunGameScoreboard.refresh();
		} else {
			throw new ScoreboardExcepion("The objective does not exists!");
		}
	}
	
	public static void update(Player player) {
		if (!Main.instance().getFileConfig().isScoreboard()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (GunGameScoreboard.exists()) {
			Objective obj = GunGameScoreboard.board.getObjective(GunGameScoreboard.OBJECTIVE_NAME);
			
			String format = GunGameScoreboard.getFormat();
			
			GunGame game = Main.instance().getGame();
			
			obj.getScore(format.replace("${player}", player.getDisplayName())).setScore(game.getPlayer(player).getLevel());
			
			GunGameScoreboard.refresh();
		} else {
			throw new ScoreboardExcepion("The objective does not exists!");
		}
	}
	
	public static void updateAll() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			GunGameScoreboard.update(player);
		}
	}
	
	public static void delete() {
		if (!Main.instance().getFileConfig().isScoreboard()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (GunGameScoreboard.exists()) {
			GunGameScoreboard.board.getObjective(GunGameScoreboard.OBJECTIVE_NAME).unregister();
		} else {
			throw new ScoreboardExcepion("The objective does not exists!");
		}
	}
	
	public static void create() {
		if (!Main.instance().getFileConfig().isScoreboard()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (!GunGameScoreboard.exists()) {
			Objective obj = GunGameScoreboard.board.registerNewObjective(GunGameScoreboard.OBJECTIVE_NAME, "dummy");
			obj.setDisplayName(Main.instance().getFileConfig().getScoreboardTitle());
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		} else {
			throw new ScoreboardExcepion("The objective does already exists!");
		}
	}
	
	public static void clearFromPlayers() {
		if (!Main.instance().getFileConfig().isScoreboard()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (GunGameScoreboard.exists()) {
			GunGameScoreboard.board.getObjective(GunGameScoreboard.OBJECTIVE_NAME).setDisplaySlot(null);
		} else {
			throw new ScoreboardExcepion("The objective does not exists!");
		}
	}
	
	public static void refresh() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.setScoreboard(GunGameScoreboard.board);
		}
	}
	
	public static boolean exists() {
		if (!Main.instance().getFileConfig().isScoreboard()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		return GunGameScoreboard.board.getObjective(GunGameScoreboard.OBJECTIVE_NAME) != null;
	}
	
}
