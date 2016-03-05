package eu.securebit.gungame.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerInterface implements GameInterface {

	@Override
	public void shutdown() {
		Bukkit.shutdown();
	}
	
	@Override
	public void kickPlayer(Player player, String cause) {
		player.kickPlayer(cause);
	}
	
	@Override
	public void closeRescources() {
		
	}
	

}
