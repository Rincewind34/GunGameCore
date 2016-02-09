package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateEnd;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.game.GunGamePlayer;
import eu.securebit.gungame.util.CoreMessages;

public class GameStateEnd extends DefaultGameStateEnd<GunGame> {
	
	public GameStateEnd(GunGame gungame) {
		super(gungame, gungame.getLocationManager().getLobbyLocation(), 20);
		
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, null);
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, gungame.getMessanger().getServerQuit());
	}

	@Override
	public void start() {
		this.getGame().playConsoleMessage(Main.layout().format("Entering gamephase: *End*"));
		
		super.start();
		
		if (this.getGame().getWinner() == null) {
			int bestLevel = 0;
			GunGamePlayer bestPlayer = null;
			
			for (GunGamePlayer player : this.getGame().getPlayers()) {
				if (player.getLevel() > bestLevel) {
					bestLevel = player.getLevel();
					bestPlayer = player;
				}
			}
			
			this.getGame().initWinner(bestPlayer);
		}
		
		this.getGame().broadcastMessage(this.getGame().getMessanger().getWinner(this.getGame().getWinner().getHandle()));
	}

	@Override
	public void stop() {
		super.stop();
		
		this.getGame().playConsoleMessage(Main.layout().format("Leaving gamephase: *End*"));
	}
	
	@Override
	public void unload() {
		super.unload();
		
		this.getGame().getInterface().initShutdown();
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Winner: " + this.getGame().getWinner());
		layout.line("Seconds left: " + this.getCountdown().getSecondsLeft());
	}
	
	@Override
	public void updateScoreboard(GamePlayer player) {
		// TODO
	}
	
	@Override
	public String getMotD() {
		return null; // TODO
	}
	
	@Override
	protected String getMessageNotJoinable() {
		return CoreMessages.serverNotJoinable();
	}

	@Override
	protected String getMessageCountdown(int secondsLeft) {
		return this.getGame().getMessanger().getCountdownEnd(secondsLeft);
	}
	
	@EventHandler
	private void onRespawn(PlayerRespawnEvent event) {
		event.setRespawnLocation(this.getGame().getLocationManager().getLobbyLocation());
	}
}
