package eu.securebit.gungame;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import eu.securebit.gungame.exception.ScoreboardExcepion;

public class GunGameScoreboard {
	
	private Scoreboard board;
	private GunGame gungame;
	private String name;
	
	public GunGameScoreboard(GunGame gungame, String name) {
		this.board = Bukkit.getScoreboardManager().getMainScoreboard();
		this.gungame = gungame;
		this.name = name;
	}
	
	private String getFormat() {
		String format = this.gungame.getSettings().getScoreboardFormat();
		return ChatColor.translateAlternateColorCodes('&', format);
	}
	
	public void setup() {
		if (!this.isEnabled()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (this.exists()) {
			Objective obj = this.board.getObjective(this.name);
			
			String format = this.getFormat();
			
			for (GunGamePlayer target : this.gungame.getPlayers()) {
				try {
					obj.getScore(format.replace("${player}", target.getHandle().getDisplayName())).setScore(target.getLevel());
				} catch (Exception ex) {
					if (Main.DEBUG) {
						System.err.println("Error occured while set the score of the player " + target.getHandle().getName() + " as " + target.getHandle().getDisplayName());
						ex.printStackTrace();
					}
					
					continue;
				}
			}
			
			this.refresh();
		} else {
			throw new ScoreboardExcepion("The objective does not exists!");
		}
	}
	
	public void update(Player player) {
		if (!this.isEnabled()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (this.exists()) {
			Objective obj = this.board.getObjective(this.name);
			
			String format = this.getFormat();
			
			obj.getScore(format.replace("${player}", player.getDisplayName())).setScore(this.gungame.getPlayer(player).getLevel());
			
			this.refresh();
		} else {
			throw new ScoreboardExcepion("The objective does not exists!");
		}
	}
	
	public void updateAll() {
		for (GunGamePlayer player : this.gungame.getPlayers()) {
			this.update(player.getHandle());
		}
	}
	
	public void delete() {
		if (!this.isEnabled()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (this.exists()) {
			this.board.getObjective(this.name).unregister();
		} else {
			throw new ScoreboardExcepion("The objective does not exists!");
		}
	}
	
	public void create() {
		if (!this.isEnabled()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (!this.exists()) {
			Objective obj = this.board.registerNewObjective(this.name, "dummy");
			obj.setDisplayName(this.gungame.getSettings().getScoreboardTitle());
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		} else {
			throw new ScoreboardExcepion("The objective does already exists!");
		}
	}
	
	public void clearFromPlayers() {
		if (!this.isEnabled()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (this.exists()) {
			this.board.getObjective(this.name).setDisplaySlot(null);
		} else {
			throw new ScoreboardExcepion("The objective does not exists!");
		}
	}
	
	public void refresh() {
		for (GunGamePlayer player : this.gungame.getPlayers()) {
			player.getHandle().setScoreboard(this.board);
		}
	}
	
	public boolean exists() {
		if (!this.isEnabled()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		return this.board.getObjective(this.name) != null;
	}
	
	public boolean isEnabled() {
		return this.gungame.getSettings().isScoreboardEnabled();
	}
	
}
