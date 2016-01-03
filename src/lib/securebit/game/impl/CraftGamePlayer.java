package lib.securebit.game.impl;

import lib.securebit.game.GamePlayer;

import org.bukkit.entity.Player;

public class CraftGamePlayer implements GamePlayer {

	protected final Player player;
	
	public CraftGamePlayer(final Player player) {
		this.player = player;
	}
	
	@Override
	public Player getHandle() {
		return this.player;
	}

}
