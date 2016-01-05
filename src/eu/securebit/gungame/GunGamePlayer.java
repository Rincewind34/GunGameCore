package eu.securebit.gungame;

import org.bukkit.entity.Player;

import lib.securebit.game.impl.CraftGamePlayer;

public class GunGamePlayer extends CraftGamePlayer {
	
	private int level;
	
	public GunGamePlayer(Player player) {
		super(player);
	}

}
