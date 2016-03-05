package lib.securebit.game.impl;

import java.util.Arrays;

import lib.securebit.game.Game;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;

import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class CraftGameStateLobby<G extends Game<? extends GamePlayer>> extends CraftGameState<G> {
	
	private Location lobby;
	
	public CraftGameStateLobby(G game, Location lobby) {
		super(game);
		
		this.lobby = lobby;
		
		this.getSettings().setValue(StateSettings.BLOCK_BREAK, Arrays.asList());
		this.getSettings().setValue(StateSettings.BLOCK_PLACE, Arrays.asList());
		this.getSettings().setValue(StateSettings.DAY_CYCLE, false);
		this.getSettings().setValue(StateSettings.ITEM_DROP, false);
		this.getSettings().setValue(StateSettings.ITEM_PICKUP, false);
		this.getSettings().setValue(StateSettings.ITEM_MOVE, false);
		this.getSettings().setValue(StateSettings.PLAYER_DAMAGE_FALL, false);
		this.getSettings().setValue(StateSettings.PLAYER_DAMAGE_FIGHT, false);
		this.getSettings().setValue(StateSettings.PLAYER_DAMAGE_NATURAL, false);
		this.getSettings().setValue(StateSettings.PLAYER_FOODLEVEL_CHANGE, false);
		this.getSettings().setValue(StateSettings.TIME, 0);
		this.getSettings().setValue(StateSettings.WEATHER, 0);
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, "§e${player} joined the game!");
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, "§e${player} left the game!");
		this.getSettings().setValue(StateSettings.DIFFICULTY, Difficulty.PEACEFUL);
		this.getSettings().setValue(StateSettings.FIRE_SPREAD, false);
	}
	
	@Override
	protected void teleportPlayer(Player player) {
		player.teleport(this.lobby);
	}
	
	@Override
	protected void onJoin(Player player) {
		super.onJoin(player);
		
		this.getGame().resetPlayer(player);
		
		player.teleport(this.lobby);
	}
	
	public Location getLobby() {
		return this.lobby;
	}
}
