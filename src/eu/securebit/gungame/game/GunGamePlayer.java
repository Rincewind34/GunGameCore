package eu.securebit.gungame.game;

import lib.securebit.game.impl.CraftGamePlayer;

import org.bukkit.entity.Player;

public class GunGamePlayer extends CraftGamePlayer {
	
	private int level;
	private GunGame gungame;
	
	public GunGamePlayer(Player player, GunGame gungame) {
		super(player);
		
		this.gungame = gungame;
		this.level = this.gungame.getOptions().getStartLevel();
	}
	
	public void refreshLevel() {
		this.gungame.getLevelManager().equipPlayer(super.player, this.level);
		super.player.setLevel(this.level);
		super.player.setExp((float) (((double) this.level - 1) / ((double) this.gungame.getLevelManager().getLevelCount())));
	}
	
	public void resetLevel() {
		this.level = this.gungame.getOptions().getStartLevel();
		this.refreshLevel();
	}
	
	public int incrementLevel() {
		if (this.level < this.gungame.getLevelManager().getLevelCount()) {
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
