package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateDisabled;

import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.GameCheck;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.interpreter.Messanger.GunGameMotD;
import eu.securebit.gungame.util.CoreMessages;
import eu.securebit.gungame.util.Permissions;

public class DisabledStateEdit extends DefaultGameStateDisabled<GunGame> {
	
	public DisabledStateEdit(GunGame gungame) {
		super(gungame);
		
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, CoreMessages.maintendanceJoin());
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, CoreMessages.maintendanceQuit());
	}
	
	@Override
	public void start() {
		this.getGame().playConsoleDebugMessage("Entering gamephase: *Edit*", Main.layout());
		super.start();
	}
	
	@Override
	public void stop() {
		super.stop();
		this.getGame().playConsoleDebugMessage("Leaving gamephase: *Edit*", Main.layout());
	}
	
	@Override
	public void updateScoreboard(GamePlayer player) {
		
	}
	
	@Override
	public String getMotD() {
		return this.getGame().getMessanger().getMotD(GunGameMotD.MAINTENDANCE);
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		for (GameCheck check : this.getGame().getChecks()) {
			check.stageStatus(layout);
		}
	}
	
	@Override
	protected String getStaffPermission() {
		return Permissions.teammember();
	}

	@Override
	protected String getMaintenanceKickMessage() {
		return this.getGame().getMessanger().getKickMaintendance();
	}

	@Override
	protected String getMaintenanceAdminMessage() {
		return CoreMessages.maintendance();
	}
	
	@Override
	protected void onJoin(Player player) {
		super.onJoin(player);
	}
	
	@Override
	protected void onQuit(Player player) {
		super.onQuit(player);
	}
	
}