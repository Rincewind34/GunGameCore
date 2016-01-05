package eu.securebit.gungame.game.states;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.securebit.gungame.GunGame;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.Permissions;
import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateDisabled;

public class DisabledStateEdit extends DefaultGameStateDisabled<GunGame> {
	
	public DisabledStateEdit(GunGame gungame) {
		super(gungame);
		
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, Messages.maintendanceJoin());
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, Messages.maintendanceQuit());
	}
	
	@Override
	public void start() {
		Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *Edit*");
		super.start();
	}
	
	@Override
	public void stop() {
		super.stop();
		Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *Edit*");
	}
	
	@Override
	public void updateScoreboard(GamePlayer player) {
		
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		this.getGame().stageEditInformation(layout);
	}
	
	@Override
	protected void onJoin(Player player) {
		super.onJoin(player);
	}
	
	@Override
	protected void onQuit(Player player) {
		super.onQuit(player);
	}

	@Override
	protected String getStaffPermission() {
		return Permissions.teammember();
	}

	@Override
	protected String getMaintenanceKickMessage() {
		return Messages.maintendanceKick();
	}

	@Override
	protected String getMaintenanceAdminMessage() {
		return Messages.maintendance();
	}

}