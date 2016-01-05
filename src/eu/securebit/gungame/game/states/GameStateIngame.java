package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateIngame;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.securebit.gungame.GunGameScoreboard;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.Util;
import eu.securebit.gungame.listeners.ListenerBlockIgnite;
import eu.securebit.gungame.listeners.ListenerPlayerDeath;
import eu.securebit.gungame.listeners.ListenerPlayerLogin;
import eu.securebit.gungame.listeners.ListenerPlayerRespawn;

public class GameStateIngame extends DefaultGameStateIngame {
	
	public GameStateIngame() {
		super(Main.instance().getGame());
		
		this.getListeners().add(new ListenerPlayerDeath());
		this.getListeners().add(new ListenerBlockIgnite());
		this.getListeners().add(new ListenerPlayerLogin());
		this.getListeners().add(new ListenerPlayerRespawn());
		
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, null);
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, Main.instance().getFileConfig().getMessageQuit());
	}

	@Override
	public void start() {
		Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *Ingame*");
		super.start();
	}

	@Override
	public void stop() {
		super.stop();
		
		if (Main.instance().getFileConfig().isScoreboard()) { //TODO use bitboard
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

	@Override
	public void updateScoreboard(GamePlayer player) {
		
	}
	
	@Override
	protected void onQuit(Player player) {
		super.onQuit(player);
		Util.startCalculation(player, 2);
	}
	
}
