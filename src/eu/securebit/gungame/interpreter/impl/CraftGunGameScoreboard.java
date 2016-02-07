package eu.securebit.gungame.interpreter.impl;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.exception.ScoreboardExcepion;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.game.GunGamePlayer;
import eu.securebit.gungame.interpreter.GunGameScoreboard;
import eu.securebit.gungame.io.configs.FileScoreboard;

public class CraftGunGameScoreboard implements GunGameScoreboard {
	
	private Scoreboard board;
	
	private FileScoreboard file;
	
	private GunGame gungame;
	
	public CraftGunGameScoreboard(GunGame gungame, FileScoreboard file) {
		if (!file.isAccessable()) {
			throw new GunGameException("Cannot interpret the scoreboard-file '" + file.getAbsolutePath() + "'!");
		}
		
		this.board = Bukkit.getScoreboardManager().getMainScoreboard();
		this.file = file;
		this.gungame = gungame;
	}
	
	private String getFormat() {
		String format = this.file.getScoreboardFormat();
		return ChatColor.translateAlternateColorCodes('&', format);
	}
	
	@Override
	public void setup() {
		if (!this.isEnabled()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (this.exists()) {
			Objective obj = this.board.getObjective(this.gungame.getName());
			
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
	
	@Override
	public void update(Player player) {
		if (!this.isEnabled()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (this.exists()) {
			Objective obj = this.board.getObjective(this.gungame.getName());
			
			String format = this.getFormat();
			
			obj.getScore(format.replace("${player}", player.getDisplayName())).setScore(this.gungame.getPlayer(player).getLevel());
			
			this.refresh();
		} else {
			throw new ScoreboardExcepion("The objective does not exists!");
		}
	}
	
	@Override
	public void updateAll() {
		for (GunGamePlayer player : this.gungame.getPlayers()) {
			this.update(player.getHandle());
		}
	}
	
	@Override
	public void delete() {
		if (!this.isEnabled()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (this.exists()) {
			this.board.getObjective(this.gungame.getName()).unregister();
		} else {
			throw new ScoreboardExcepion("The objective does not exists!");
		}
	}
	
	@Override
	public void create() {
		if (!this.isEnabled()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (!this.exists()) {
			Objective obj = this.board.registerNewObjective(this.gungame.getName(), "dummy");
			obj.setDisplayName(this.file.getScoreboardTitle());
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		} else {
			throw new ScoreboardExcepion("The objective does already exists!");
		}
	}
	
	@Override
	public void clearFromPlayers() {
		if (!this.isEnabled()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		if (this.exists()) {
			this.board.getObjective(this.gungame.getName()).setDisplaySlot(null);
		} else {
			throw new ScoreboardExcepion("The objective does not exists!");
		}
	}
	
	@Override
	public void refresh() {
		for (GunGamePlayer player : this.gungame.getPlayers()) {
			player.getHandle().setScoreboard(this.board);
		}
	}
	
	@Override
	public boolean exists() {
		if (!this.isEnabled()) {
			throw new ScoreboardExcepion("The scoreboard is disabled!");
		}
		
		return this.board.getObjective(this.gungame.getName()) != null;
	}
	
	@Override
	public boolean isEnabled() {
		return this.file.isScoreboardEnabled();
	}
	
	public FileScoreboard getFile() {
		return this.file;
	}
	
}
