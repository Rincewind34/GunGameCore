package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateDisabled;

import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.GameCheck;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.util.CoreMessages;
import eu.securebit.gungame.util.Permissions;

public abstract class DisabledStateEdit extends DefaultGameStateDisabled<GunGame> {
	
	public DisabledStateEdit(GunGame gungame) {
		super(gungame);
		
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, CoreMessages.maintendanceJoin());
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, CoreMessages.maintendanceQuit());
	}
	
	@Override
	public void start() {
		this.getGame().playConsoleMessage(Main.layout().format("Entering gamephase: *Edit*"));
		super.start();
	}
	
	@Override
	public void stop() {
		super.stop();
		this.getGame().playConsoleMessage(Main.layout().format("Leaving gamephase: *Edit*"));
	}
	
	@Override
	public void updateScoreboard(GamePlayer player) {
		
	}
	
	@Override
	public String getMotD() {
		return null; //TODO
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		for (GameCheck check : this.getGame().getChecks()) {
			check.stageStatus(layout);
		}
//		
//		this.stageFileData("config$-file", CustomFrame.instance().getFileConfig(), layout);
//		this.stageFileData("messages$-file", CustomFrame.instance().getFileMessages(), layout);
//		this.stageFileData("levels$-file", CustomFrame.instance().getFileLevels(), layout);
//		this.stageFileData("scoreboard$-file", CustomFrame.instance().getFileScoreboard(), layout);
//
//		layout.line("");
//
//		if (CustomFrame.instance().getFileConfig().getSpawns().size() < 1) {
//			layout.line("spawns: " + Util.parseBoolean(false, layout));
//			layout.line("  $- You have to set at least one spawn location!");
//		} else {
//			layout.line("spawns: " + Util.parseBoolean(true, layout));
//		}
//
//		if (CustomFrame.instance().getFileConfig().isEditMode()) {
//			layout.line("value: " + Util.parseBoolean(true, layout, true));
//			layout.line("  $- Turn the value 'EditMode' in 'config.yml' to *false*!");
//		} else {
//			layout.line("value: " + Util.parseBoolean(false, layout, true));
//		}
	}
	
	@Override
	protected String getStaffPermission() {
		return Permissions.teammember();
	}

	@Override
	protected String getMaintenanceKickMessage() {
		return CoreMessages.maintendanceKick();
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

//	private void stageFileData(String name, FileConfig config, InfoLayout layout) {
//		if (config.isReady()) {
//			layout.line(name + ": " + Util.parseBoolean(true, layout));
//		} else {
//			layout.line(name + ": " + Util.parseBoolean(false, layout));
//		}
//	}

}