package eu.securebit.gungame;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import lib.securebit.game.impl.CraftGame;

public abstract class GunGame extends CraftGame<GunGamePlayer> {

	private Player winner;
	private Settings settings;
	
	public GunGame(Settings settings) {
		super(Main.instance());
		
		this.settings = settings;
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
	
	public Settings getSettings() {
		return this.settings;
	}
	
//	public boolean isReady() {
//		try {
//			Main.instance().getFileConfig().validate();
//		} catch (MalformedConfigException exception) {
//			if (Main.DEBUG) {
//				System.err.println("Invalid config.yml file! Error: " + exception.getMessage());
//				exception.printStackTrace();
//			}
//			return false;
//		}
//		
//		try {
//			Main.instance().getFileLevels().validate();
//		} catch (MalformedConfigException exception) {
//			if (Main.DEBUG) {
//				System.err.println("Invalid levels.yml file! Error: " + exception.getMessage());
//				exception.printStackTrace();
//			}
//			return false;
//		}
//		
//		if (Main.instance().getFileConfig().getSpawns().size() < 1) {
//			System.err.println("You have to set at least one spawn location!");
//			return false;
//		}
//		
//		return !Main.instance().getFileConfig().isEditMode();
//	}
	
	public void handleDisconnect(Player player) {
		if (this.winner == player) {
			this.winner = null;
		}
		
		this.calculateGameState();
	}
	
	public void calculateGameState() {
		Main.layout().message(Bukkit.getConsoleSender(), "Calculating...");
		
		if (Bukkit.getOnlinePlayers().size() == 1) {
			Main.layout().message(Bukkit.getConsoleSender(), "Skiping all phases!");
			Main.instance().getGameStateManager().skipAll();
		}
		
		if (Bukkit.getOnlinePlayers().size() == 0) {
			Main.layout().message(Bukkit.getConsoleSender(), "Shutdown!");
			
			if (Main.DEBUG) {
				Bukkit.reload();
			} else {
				Bukkit.shutdown();
			}
		}
	}
	
	public void initWinner(Player player) {
		this.winner = player;
	}
	
	public Player getWinner() {
		return this.winner;
	}

	public abstract boolean isReady();
}