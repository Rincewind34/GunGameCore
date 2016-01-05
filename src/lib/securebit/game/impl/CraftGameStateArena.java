package lib.securebit.game.impl;

import java.util.Arrays;

import lib.securebit.game.Game;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;

import org.bukkit.Difficulty;
import org.bukkit.Material;

public abstract class CraftGameStateArena extends CraftGameState {

	public CraftGameStateArena(Game<? extends GamePlayer> game) {
		super(game);
		
		this.getSettings().setValue(StateSettings.BLOCK_BREAK, Arrays.asList(Material.LEAVES, Material.LEAVES_2, Material.WEB));
		this.getSettings().setValue(StateSettings.BLOCK_PLACE, Arrays.asList(Material.LEAVES, Material.LEAVES_2, Material.WEB));
		this.getSettings().setValue(StateSettings.DAY_CYCLE, true);
		this.getSettings().setValue(StateSettings.ITEM_DROP, true);
		this.getSettings().setValue(StateSettings.ITEM_PICKUP, true);
		this.getSettings().setValue(StateSettings.ITEM_MOVE, true);
		this.getSettings().setValue(StateSettings.PLAYER_DAMAGE_FALL, false);
		this.getSettings().setValue(StateSettings.PLAYER_DAMAGE_FIGHT, true);
		this.getSettings().setValue(StateSettings.PLAYER_DAMAGE_NATURAL, true);
		this.getSettings().setValue(StateSettings.PLAYER_FOODLEVEL_CHANGE, true);
		this.getSettings().setValue(StateSettings.TIME, 0);
		this.getSettings().setValue(StateSettings.WEATHER, 0);
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, null);
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, "§e${player} left the game!");
		this.getSettings().setValue(StateSettings.DIFFICULTY, Difficulty.PEACEFUL);
	}
	
}
