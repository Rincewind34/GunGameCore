package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateIngame;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.CraftGunGame;
import eu.securebit.gungame.game.GunGamePlayer;
import eu.securebit.gungame.listeners.ListenerEntityDeath;
import eu.securebit.gungame.listeners.ListenerPlayerDeath;
import eu.securebit.gungame.listeners.ListenerPlayerRespawn;
import eu.securebit.gungame.util.Permissions;
import eu.securebit.gungame.util.Util;

public class GameStateIngame extends DefaultGameStateIngame<CraftGunGame> {
	
	public GameStateIngame(CraftGunGame gungame) {
		super(gungame);
		
		this.getListeners().add(new ListenerPlayerDeath(gungame));
		this.getListeners().add(new ListenerPlayerRespawn(gungame));
		this.getListeners().add(new ListenerEntityDeath(gungame));
		
		this.getSettings().setValue(StateSettings.ITEM_DROP, false);
		this.getSettings().setValue(StateSettings.ITEM_PICKUP, false);
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, null);
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, gungame.getMessanger().getServerQuit());
	}

	@Override
	public void start() {
		this.getGame().playConsoleMessage(Main.layout().format("Entering gamephase: *Ingame*"));
		super.start();
		
		Bukkit.getScheduler().runTaskLater(Main.instance(), () -> {
			this.getGame().calculateGameState();
		}, 5L);
	}

	@Override
	public void stop() {
		super.stop();
		
		if (this.getGame().getScoreboard().isEnabled()) { //TODO use bitboard
			this.getGame().getScoreboard().clearFromPlayers();
		}
		
		this.getGame().playConsoleMessage(Main.layout().format("Leaving gamephase: *Ingame*"));
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		for (GunGamePlayer player : this.getGame().getPlayers()) {
			layout.line("  $- " + player.getHandle().getName() + "(" + player.getLevel() + ")");
		}
	}

	@Override
	public void updateScoreboard(GamePlayer player) {
		
	}
	
	@Override
	public String getMotD() {
		return null; // TODO
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
