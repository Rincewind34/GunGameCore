package lib.securebit.game.defaults;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import lib.securebit.InfoLayout;
import lib.securebit.game.Game;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.impl.CraftGameState;

public abstract class DefaultGameStateDisabled extends CraftGameState {

	public DefaultGameStateDisabled(Game<? extends GamePlayer> game) {
		super(game);
		this.getSettings().setValue(StateSettings.BLOCK_BREAK, Arrays.asList(Material.values()));
		this.getSettings().setValue(StateSettings.BLOCK_PLACE, Arrays.asList(Material.values()));
		this.getSettings().setValue(StateSettings.DAY_CYCLE, true);
		this.getSettings().setValue(StateSettings.ITEM_DROP, true);
		this.getSettings().setValue(StateSettings.ITEM_MOVE, true);
		this.getSettings().setValue(StateSettings.ITEM_PICKUP, true);
		this.getSettings().setValue(StateSettings.PLAYER_DAMAGE_FALL, true);
		this.getSettings().setValue(StateSettings.PLAYER_DAMAGE_FIGHT, true);
		this.getSettings().setValue(StateSettings.PLAYER_DAMAGE_NATURAL, true);
		this.getSettings().setValue(StateSettings.PLAYER_FOODLEVEL_CHANGE, true);
		this.getSettings().setValue(StateSettings.TIME, 0);
		this.getSettings().setValue(StateSettings.WEATHER, 0);

	}

	@Override
	public void start() {
		Bukkit.getOnlinePlayers().forEach((player) -> {
			player.setGameMode(GameMode.CREATIVE);
		});
	}

	@Override
	public void stop() {}

	@Override
	public void updateScoreboard(GamePlayer player) {}

	@Override
	public void stageInformation(InfoLayout layout) {}

	@Override
	public String getName() {
		return "disabled";
	}
	
	@Override
	protected void onJoin(Player player) {
		super.onJoin(player);
		player.setGameMode(GameMode.CREATIVE);
		player.sendMessage(this.getMaintenanceAdminMessage());
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPermission(this.getStaffPermission())) {
			event.disallow(Result.KICK_OTHER, this.getMaintenanceKickMessage());
		}
	}
	
	protected abstract String getStaffPermission();

	protected abstract String getMaintenanceKickMessage();
	
	protected abstract String getMaintenanceAdminMessage();
}
