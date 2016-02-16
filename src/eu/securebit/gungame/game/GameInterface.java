package eu.securebit.gungame.game;

import org.bukkit.entity.Player;

public interface GameInterface {
	
	public abstract void shutdown();
	
	public abstract void kickPlayer(Player player, String cause);
	
}
