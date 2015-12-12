package eu.securebit.gungame.game.states;

import lib.securebit.game.GameState;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;

public class DisabledStateEdit implements GameState {
	
	@Override
	public Runnable getEnterListener() {
		return () -> {
			Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *Edit*");
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.setGameMode(GameMode.CREATIVE);
			}
			
			Main.instance().getListenerHandler().register("bundle.edit");
		};
	}

	@Override
	public Runnable getLeaveListener() {
		return () -> {
			Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *Edit*");
			
			Main.instance().getListenerHandler().unregister("bundle.edit");
		};
	}
	
}
