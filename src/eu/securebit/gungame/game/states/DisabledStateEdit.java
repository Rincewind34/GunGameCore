package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.defaults.DefaultGameStateDisabled;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.Permissions;
import eu.securebit.gungame.Util;
import eu.securebit.gungame.exception.MalformedConfigException;

public class DisabledStateEdit extends DefaultGameStateDisabled {
	
	public DisabledStateEdit() {
		super(Main.instance().getGame());
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
	public void stageInformation(InfoLayout layout) {
		try {
			Main.instance().getFileConfig().validate();
			layout.line("config.yml: " + Util.parseBoolean(true, layout));
		} catch (MalformedConfigException exception) {
			layout.line("config.yml: " + Util.parseBoolean(false, layout));
			layout.line("  $- " + InfoLayout.replaceKeys(exception.getMessage()));
		}
		
		try {
			Main.instance().getFileLevels().validate();
			layout.line("levels.yml: " + Util.parseBoolean(true, layout));
		} catch (MalformedConfigException exception) {
			layout.line("levels.yml: " + Util.parseBoolean(false, layout));
			layout.line("  $- " + InfoLayout.replaceKeys(exception.getMessage()));
		}
		
		if (Main.instance().getFileConfig().getSpawns().size() < 1) {
			layout.line("spawns: " + Util.parseBoolean(false, layout));
			layout.line("  $- You have to set at least one spawn location!");
		} else {
			layout.line("spawns: " + Util.parseBoolean(true, layout));
		}
		
		if (Main.instance().getFileConfig().isEditMode()) {
			layout.line("value: " + Util.parseBoolean(true, layout, true));
			layout.line("  $- Turn the value 'EditMode' in 'config.yml' to *false*!");
		} else {
			layout.line("value: " + Util.parseBoolean(false, layout, true));
		}
	}
	
	@Override
	protected void onJoin(Player player) {
		super.onJoin(player);
		Main.layout().broadcast("*" + player + "* joined the server!");
	}
	
	@Override
	protected void onQuit(Player player) {
		super.onQuit(player);
		Main.layout().broadcast("*" + player + "* left the server!");
	}

	@Override
	protected String getStaffPermission() {
		return Permissions.teammember();
	}

	@Override
	protected String getMaintenanceKickMessage() {
		return Messages.maintenanceKick();
	}

	@Override
	protected String getMaintenanceAdminMessage() {
		return Messages.maintenance();
	}
	
}