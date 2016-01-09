package eu.securebit.gungame.game;

import lib.securebit.game.impl.CraftGamePlayer;

import org.bukkit.entity.Player;

import eu.securebit.gungame.util.LevelManager;

public class GunGamePlayer extends CraftGamePlayer {
	
	private int level;
	private GunGame gungame;
	
	public GunGamePlayer(Player player, GunGame gungame) {
		super(player);
		
		this.gungame = gungame;
		this.level = LevelManager.getStartLevel(this.gungame);
	}
	
	public void refreshLevel() {
		LevelManager.equip(this.gungame, this.level, super.player);
		super.player.setLevel(this.level);
		super.player.setExp((float) (((double) this.level - 1) / ((double) LevelManager.getCount(this.gungame))));
	}
	
	public void resetLevel() {
		this.level = LevelManager.getStartLevel(this.gungame);
		this.refreshLevel();
	}
	
	public int incrementLevel() {
		if (this.level < LevelManager.getCount(this.gungame)) {
			++this.level;
			this.refreshLevel();
		}
		
		return this.level;
	}
	
	public int decrementLevel() {
		if (this.level > 1) {
			--this.level;
			this.refreshLevel();
		}
		
		return this.level;
	}

	public int getLevel() {
		return this.level;
	}
}
