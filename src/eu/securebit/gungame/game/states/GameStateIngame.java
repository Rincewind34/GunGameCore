package eu.securebit.gungame.game.states;

import lib.securebit.game.GameState;

import org.bukkit.Bukkit;

import eu.securebit.gungame.GunGameScoreboard;
import eu.securebit.gungame.Main;

public class GameStateIngame implements GameState {
	
	@Override
	public Runnable getEnterListener() {
		return () -> {
			Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *Ingame*");
			
			Main.instance().getListenerHandler().register("bundle.ingame");
		};
	}

	@Override
	public Runnable getLeaveListener() {
		return () -> {
			Main.instance().getListenerHandler().unregister("bundle.ingame");
			
			if (Main.instance().getFileConfig().isScoreboard()) {
				GunGameScoreboard.clearFromPlayers();
			}
			
			Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *Ingame*");
		};		
	}
	
}
