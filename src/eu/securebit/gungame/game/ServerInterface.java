package eu.securebit.gungame.game;

import org.bukkit.Bukkit;

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

}
