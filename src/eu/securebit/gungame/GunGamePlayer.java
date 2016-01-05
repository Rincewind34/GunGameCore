package eu.securebit.gungame;

import org.bukkit.entity.Player;

import lib.securebit.game.impl.CraftGamePlayer;

public class GunGamePlayer extends CraftGamePlayer {
	
	private int level;
	
	public GunGamePlayer(Player player) {
		super(player);
		this.level = Main.instance().getFileConfig().getStartLevel();
	}
	
	public void refreshLevel() {
		Main.instance().getFileLevels().give(super.player, this.level);
		super.player.setLevel(this.level);
		super.player.setExp((float) (((double) this.level - 1) / ((double) Main.instance().getFileLevels().getLevelCount())));
	}
	
	public void resetLevel() {
		this.level = Main.instance().getFileConfig().getStartLevel();
		this.refreshLevel();
	}
	
	public int incrementLevel() {
		if (this.level < Main.instance().getFileLevels().getLevelCount()) {
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
