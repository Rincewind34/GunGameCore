package eu.securebit.gungame;

import java.util.HashMap;
import java.util.Map;

import lib.securebit.game.impl.CraftGame;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import eu.securebit.gungame.exception.MalformedConfigException;

public class GunGame extends CraftGame<GunGamePlayer> {

	private Map<Player, Integer> levels; // TODO Improve with GunGamePlayer
	private Player winner;
	
	public GunGame() {
		super(Main.instance());
		this.levels = new HashMap<>();
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
	
	public boolean isReady() {
		try {
			Main.instance().getFileConfig().validate();
		} catch (MalformedConfigException exception) {
			if (Main.DEBUG) {
				System.err.println("Invalid config.yml file! Error: " + exception.getMessage());
				exception.printStackTrace();
			}
			return false;
		}
		
		try {
			Main.instance().getFileLevels().validate();
		} catch (MalformedConfigException exception) {
			if (Main.DEBUG) {
				System.err.println("Invalid levels.yml file! Error: " + exception.getMessage());
				exception.printStackTrace();
			}
			return false;
		}
		
		if (Main.instance().getFileConfig().getSpawns().size() < 1) {
			System.err.println("You have to set at least one spawn location!");
			return false;
		}
		
		return !Main.instance().getFileConfig().isEditMode();
	}
	
	public void handleDisconnect(Player player) {
		if (this.winner == player) {
			this.winner = null;
		}
		
		this.levels.remove(player);
		
		this.calculateGameState();
	}
	
	public void calculateGameState() {
		if (Bukkit.getOnlinePlayers().size() == 1) {
			Main.instance().getGameStateManager().skipAll();
		}
		
		if (Bukkit.getOnlinePlayers().size() == 0) {
			if (Main.DEBUG) {
				Bukkit.reload();
			} else {
				Bukkit.shutdown();
			}
		}
	}
	
	public boolean addLevel(Player player) {
		this.insertPlayer(player);
		
		int current = this.getCurrentLevel(player);
		
		if (current == Main.instance().getFileLevels().getLevelCount()) {
			return true;
		} else {
			current = current + 1;
			
			this.levels.put(player, current);
			this.refresh(player);
			
			return false;
		}
	}
	
	public void removeLevel(Player player) {
		this.insertPlayer(player);
		
		if (this.levels.get(player) != 1) {
			this.levels.put(player, this.getCurrentLevel(player) - 1);
		}
		
		this.refresh(player);
	}
	
	public void resetLevel(Player player) {
		this.insertPlayer(player);
		this.levels.put(player, Main.instance().getFileConfig().getStartLevel());
		this.refresh(player);
	}
	
	public void insertPlayer(Player player) {
		if (!this.levels.containsKey(player)) {
			this.levels.put(player, Main.instance().getFileConfig().getStartLevel());
			this.refresh(player);
		}
	}
	
	public void refresh(Player player) {
		Main.instance().getFileLevels().give(player, this.getCurrentLevel(player));
		player.setLevel(this.getCurrentLevel(player));
		player.setExp((float) (((double) this.getCurrentLevel(player) - 1) / ((double) Main.instance().getFileLevels().getLevelCount())));
	}
	
	public int getCurrentLevel(Player player) {
		return this.levels.get(player);
	}
	
	public void initWinner(Player player) {
		this.winner = player;
	}
	
	public Player getWinner() {
		return this.winner;
	}

}