package eu.securebit.gungame.game;

import lib.securebit.game.impl.CraftGame;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.framework.ActionInterface;
import eu.securebit.gungame.framework.Settings;

public abstract class GunGame extends CraftGame<GunGamePlayer> {

	private GunGamePlayer winner;
	private GunGameScoreboard board;
	
	private ActionInterface actionInterface;
	private Settings settings;
	
	public GunGame(Settings settings, ActionInterface actionInterface) {
		super(Main.instance());
		
		this.settings = settings;
		this.actionInterface = actionInterface;
		this.board = new GunGameScoreboard(this, this.getSettings().getUUID());
		
		if (this.board.isEnabled()) {
			if (this.board.exists()) {
				this.board.delete();
			}
			
			this.board.create();
		}
	}
	
	@Override
	public void playConsoleMessage(String msg) {
		super.playConsoleMessage(Main.layout().colorSecondary + this.settings.getUUID() + ": " + msg);
	}
	
	@Override
	public void resetPlayer(Player player) {
		player.setGameMode(GameMode.SURVIVAL);
		player.setHealth(20.0);
		player.setVelocity(new Vector(0, 0, 0));
		player.setFoodLevel(20);
		player.setExp(0.0F);
		player.setLevel(0);
		player.setFireTicks(0);
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[] { null, null, null, null });
		
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}
	
	public ActionInterface getInterface() {
		return this.actionInterface;
	}
	
	public Settings getSettings() {
		return this.settings;
	}
	
	public GunGameScoreboard getScoreboard() {
		return this.board;
	}
	
	public void handleDisconnect(Player player) {
		if (this.winner == player) {
			this.winner = null;
		}
		
		this.calculateGameState();
	}
	
	public void calculateGameState() {
		this.playConsoleMessage(Main.layout().format("Calculating..."));
		
		if (this.getPlayers().size() == 1) {
			this.playConsoleMessage(Main.layout().format("Skiping all phases!"));
			this.getManager().skipAll();
		}
		
		if (this.getPlayers().size() == 0) {
			this.playConsoleMessage(Main.layout().format("Shutdown!"));
			
			this.actionInterface.shutdown();
		}
	}
	
	public void initWinner(GunGamePlayer player) {
		this.winner = player;
	}
	
	public GunGamePlayer getWinner() {
		return this.winner;
	}
	
	public abstract boolean isReady();
	
}