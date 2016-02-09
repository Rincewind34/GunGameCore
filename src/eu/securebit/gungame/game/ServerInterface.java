package eu.securebit.gungame.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerInterface implements GameInterface {

	@Override
	public void initShutdown() {
		Bukkit.shutdown();
	}

	@Override
	public void initReload() {
		Bukkit.reload();
	}

	@Override
	public void initRestart() {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
	}
	
	@Override
	public void kickPlayer(Player player, String cause) {
		player.kickPlayer(cause);
	}

}
