package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateGrace;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.CraftGunGame;
import eu.securebit.gungame.game.GunGamePlayer;
import eu.securebit.gungame.interpreter.Messanger.GunGameMotD;
import eu.securebit.gungame.listeners.ListenerEntityDeath;
import eu.securebit.gungame.util.Permissions;
import eu.securebit.gungame.util.Util;

public class GameStateGrace extends DefaultGameStateGrace<CraftGunGame> {
	
	public GameStateGrace(CraftGunGame gungame) {
		super(gungame, 15);
		
		this.getListeners().add(new ListenerEntityDeath(gungame));
		
		this.getSettings().setValue(StateSettings.ITEM_DROP, false);
		this.getSettings().setValue(StateSettings.ITEM_PICKUP, false);
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, null);
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, gungame.getMessanger().getServerQuit());
	}

	@Override
	public void start() {
		this.getGame().playConsoleMessage(Main.layout().format("Entering gamephase: *Grace*"));
		
		for (GunGamePlayer player : this.getGame().getPlayers()) {
			player.refreshLevel();
		}
		
		if (this.getGame().getScoreboard().isEnabled()) {
			this.getGame().getScoreboard().setup();
		}
		
		this.getGame().broadcastMessage(this.getGame().getMessanger().getMapTeleport());
		
		super.start();
		
		this.getGame().broadcastMessage(this.getGame().getMessanger().getGracePeriodStarts());
		
		Bukkit.getScheduler().runTaskLater(Main.instance(), () -> {
			this.getGame().calculateGameState();
		}, 5L);
	}

	@Override
	public void stop() {
		super.stop();
		
		this.getGame().broadcastMessage(this.getGame().getMessanger().getGracePeriodEnds());
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
	public String getMotD() {
		return this.getGame().getMessanger().getMotD(GunGameMotD.GRACE);
	}
	
	@Override
	protected String getMessageCountdown(int secondsleft) {
		return this.getGame().getMessanger().getCountdownGrace(secondsleft);
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
