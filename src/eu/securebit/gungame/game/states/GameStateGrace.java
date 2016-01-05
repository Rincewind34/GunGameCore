package eu.securebit.gungame.game.states;

import java.util.List;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateGrace;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.securebit.gungame.GunGame;
import eu.securebit.gungame.GunGamePlayer;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.Permissions;
import eu.securebit.gungame.Util;

public class GameStateGrace extends DefaultGameStateGrace<GunGame> {
	
	public GameStateGrace(GunGame gungame) {
		super(gungame, 15);
		
		this.getSettings().setValue(StateSettings.ITEM_DROP, false);
		this.getSettings().setValue(StateSettings.ITEM_PICKUP, false);
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, null);
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, gungame.getSettings().messages().getServerQuit());
	}

	@Override
	public void start() {
		Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *Grace*");
		
		List<Location> spawns = Util.getSpawns(this.getGame());
		
		for (GunGamePlayer player : this.getGame().getPlayers()) {
			player.getHandle().teleport(spawns.get(Main.random().nextInt(spawns.size())));
			player.refreshLevel();
		}
		
		if (this.getGame().getScoreboard().isEnabled()) { //TODO use bitboard
			this.getGame().getScoreboard().setup();
		}
		
		this.getGame().broadcastMessage(this.getGame().getSettings().messages().getMapTeleport());
		
		super.start();
		
		this.getGame().broadcastMessage(this.getGame().getSettings().messages().getGracePeriodStarts());
		
		Bukkit.getScheduler().runTaskLater(Main.instance(), () -> {
			this.getGame().calculateGameState();
		}, 5L);
	}

	@Override
	public void stop() {
		super.stop();
		
		this.getGame().broadcastMessage(this.getGame().getSettings().messages().getGracePeriodStarts());
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
		return this.getGame().getSettings().messages().getCountdownGrace(secondsleft);
	}
	
	@Override
	protected String onLogin(Player player) {
		if (!player.hasPermission(Permissions.joinIngame())) {
			return "The game is already running!"; //TODO Message
		} else {
			return null;
		}
	}
	
	@Override
	protected void onQuit(Player player) {
		super.onQuit(player);
		Util.startCalculation(player, 2, this.getGame());
	}
	
}
