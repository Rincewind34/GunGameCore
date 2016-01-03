package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GameState;
import lib.securebit.game.listeners.ListenerBlocks;
import lib.securebit.game.listeners.ListenerPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.securebit.gungame.GunGameScoreboard;
import eu.securebit.gungame.Main;

public class GameStateIngame extends GameState {
	
	public GameStateIngame() {
		this.getListeners().add(new ListenerBlocks());
		this.getListeners().add(new ListenerPlayer());
	}
	
	@Override
	public void onEnter() {
		Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *Ingame*");
	}

	@Override
	public void onLeave() {
		if (Main.instance().getFileConfig().isScoreboard()) {
			GunGameScoreboard.clearFromPlayers();
		}
		
		Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *Ingame*");
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			layout.line("  $- " + player.getName() + "(" + Main.instance().getGame().getCurrentLevel(player) + ")");
		}
	}
	
}
