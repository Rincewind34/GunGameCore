package eu.securebit.gungame.game.states;

import java.util.List;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.defaults.DefaultGameStateGrace;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.securebit.gungame.GunGameScoreboard;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.Util;
import eu.securebit.gungame.listeners.ListenerBlockIgnite;
import eu.securebit.gungame.listeners.ListenerPlayerLogin;

public class GameStateGrace extends DefaultGameStateGrace {
	
	public GameStateGrace() {
		super(Main.instance().getGame(), 15);
		
		this.getListeners().add(new ListenerBlockIgnite());
		this.getListeners().add(new ListenerPlayerLogin());
	}

	@Override
	public void start() {
		Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *Grace*");
		
		List<Location> spawns = Main.instance().getFileConfig().getSpawns();
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.teleport(spawns.get(Main.random().nextInt(spawns.size())));
			Main.instance().getGame().insertPlayer(player);
		}
		
		if (Main.instance().getFileConfig().isScoreboard()) { //TODO use bitboard
			GunGameScoreboard.setup();
		}
		
		Main.broadcast(Main.instance().getFileConfig().getMessageMapTeleport());
		Main.broadcast(Main.instance().getFileConfig().getMessageGraceStart());
		
		Main.instance().getGame().calculateGameState();
	}

	@Override
	public void stop() {
		super.stop();
		
		Bukkit.broadcastMessage(Main.instance().getFileConfig().getMessageGraceEnd());
		Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *Grace*");
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Seconds left: " + this.getCountdown().getSecondsLeft());
	}

	@Override
	public void updateScoreboard(GamePlayer player) {
		// TODO
	}

	@Override
	protected String getMessageCountdown(int secondsleft) {
		return Main.instance().getFileConfig().getMessageGraceCountdown(secondsleft);
	}
	
	@Override
	protected void onQuit(Player player) {
		super.onQuit(player);
		Util.startCalculation(player, 2);
	}
	
}
