package eu.securebit.gungame.game;

import org.bukkit.entity.Player;

public interface GameInterface {
	
	public abstract void initShutdown();
	
	public abstract void initReload();
	
	public abstract void initRestart();
	
	public abstract void kickPlayer(Player player, String cause);
	
}
