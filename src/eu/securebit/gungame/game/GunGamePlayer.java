package eu.securebit.gungame.game;

import lib.securebit.game.impl.CraftGamePlayer;

import org.bukkit.entity.Player;

public class GunGamePlayer extends CraftGamePlayer {
	
	private int level;
	private GunGame gungame;
	
	public GunGamePlayer(Player player, GunGame gungame) {
		super(player);
		
		this.gungame = gungame;
		this.level = this.gungame.getSettings().getStartLevel().getId();
	}
	
	public void refreshLevel() {
		this.gungame.getSettings().getLevels().get(level).equip(super.player);
		super.player.setLevel(this.level);
		super.player.setExp((float) (((double) this.level - 1) / ((double) this.gungame.getSettings().getLevels().size())));
	}
	
	public void resetLevel() {
		this.level = this.gungame.getSettings().getStartLevel().getId();
		this.refreshLevel();
	}
	
	public int incrementLevel() {
		if (this.level < this.gungame.getSettings().getLevels().size()) {
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
