package eu.securebit.gungame.game.states;

import java.util.List;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateGrace;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.game.GunGamePlayer;
import eu.securebit.gungame.listeners.ListenerEntityDeath;
import eu.securebit.gungame.util.Permissions;
import eu.securebit.gungame.util.Util;

public class GameStateGrace extends DefaultGameStateGrace<GunGame> {
	
	public GameStateGrace(GunGame gungame) {
		super(gungame, 15);
		
		this.getListeners().add(new ListenerEntityDeath(gungame));
		
		this.getSettings().setValue(StateSettings.ITEM_DROP, false);
		this.getSettings().setValue(StateSettings.ITEM_PICKUP, false);
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, null);
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, gungame.getSettings().files().getMessages().getServerQuit());
	}

	@Override
	public void start() {
		this.getGame().playConsoleMessage(Main.layout().format("Entering gamephase: *Grace*"));
		
		List<Location> spawns = Util.getSpawns(this.getGame());
		
		for (GunGamePlayer player : this.getGame().getPlayers()) {
			player.getHandle().teleport(spawns.get(Main.random().nextInt(spawns.size())));
			player.refreshLevel();
		}
		
		if (this.getGame().getScoreboard().isEnabled()) { //TODO use bitboard
			this.getGame().getScoreboard().setup();
		}
		
		this.getGame().broadcastMessage(this.getGame().getSettings().files().getMessages().getMapTeleport());
		
		super.start();
		
		this.getGame().broadcastMessage(this.getGame().getSettings().files().getMessages().getGracePeriodStarts());
		
		Bukkit.getScheduler().runTaskLater(Main.instance(), () -> {
			this.getGame().calculateGameState();
		}, 5L);
	}

	@Override
	public void stop() {
		super.stop();
		
		this.getGame().broadcastMessage(this.getGame().getSettings().files().getMessages().getGracePeriodStarts());
		this.getGame().playConsoleMessage(Main.layout().format("Leaving gamephase: *Grace*"));
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
		return this.getGame().getSettings().files().getMessages().getCountdownGrace(secondsleft);
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
	
	@Override
	protected void onBlockBreak(Block block, Player player, boolean allowed) {
		if (allowed) {
			block.setType(Material.AIR);
		}
	}
	
}
