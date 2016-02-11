package eu.securebit.gungame.interpreter.impl;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.exception.GunGameScoreboardException;
import eu.securebit.gungame.game.CraftGunGame;
import eu.securebit.gungame.game.GunGamePlayer;
import eu.securebit.gungame.interpreter.GunGameScoreboard;
import eu.securebit.gungame.io.configs.FileScoreboard;

public class CraftGunGameScoreboard extends AbstractInterpreter<FileScoreboard> implements GunGameScoreboard {
	
	private Scoreboard board;
	
	private CraftGunGame gungame;
	
	public CraftGunGameScoreboard(CraftGunGame gungame, FileScoreboard file) {
		super(file);
		
		this.gungame = gungame;
		this.board = Bukkit.getScoreboardManager().getMainScoreboard();
	}
	
	private String getFormat() {
		String format = super.config.getScoreboardFormat();
		return ChatColor.translateAlternateColorCodes('&', format);
	}
	
	@Override
	public void setup() {
		this.checkEnabled();
		
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
			throw GunGameScoreboardException.noObjective();
		}
	}
	
	@Override
	public void update(Player player) {
		this.checkEnabled();
		
		if (this.exists()) {
			Objective obj = this.board.getObjective(this.gungame.getName());
			
			String format = this.getFormat();
			
			obj.getScore(format.replace("${player}", player.getDisplayName())).setScore(this.gungame.getPlayer(player).getLevel());
			
			this.refresh();
		} else {
			throw GunGameScoreboardException.noObjective();
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
		this.checkEnabled();
		
		if (this.exists()) {
			this.board.getObjective(this.gungame.getName()).unregister();
		} else {
			throw GunGameScoreboardException.noObjective();
		}
	}
	
	@Override
	public void create() {
		this.checkEnabled();
		
		if (!this.exists()) {
			Objective obj = this.board.registerNewObjective(this.gungame.getName(), "dummy");
			obj.setDisplayName(super.config.getScoreboardTitle());
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		} else {
			throw GunGameScoreboardException.existingObjective();
		}
	}
	
	@Override
	public void clearFromPlayers() {
		this.checkEnabled();
		
		if (this.exists()) {
			this.board.getObjective(this.gungame.getName()).setDisplaySlot(null);
		} else {
			throw GunGameScoreboardException.noObjective();
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
		this.checkEnabled();
		
		return this.board.getObjective(this.gungame.getName()) != null;
	}
	
	@Override
	public boolean isEnabled() {
		return super.config.isScoreboardEnabled();
	}
	
	private void checkEnabled() {
		if (!this.isEnabled()) {
			throw GunGameScoreboardException.boardIsDisabled();
		}
	}
	
}
