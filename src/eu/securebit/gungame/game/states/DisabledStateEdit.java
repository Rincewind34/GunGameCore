package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GameState;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.Permissions;
import eu.securebit.gungame.Util;
import eu.securebit.gungame.exception.MalformedConfigException;

public class DisabledStateEdit extends GameState {
	
	@Override
	public void onEnter() {
		Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *Edit*");
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.setGameMode(GameMode.CREATIVE);
		}
	}

	@Override
	public void onLeave() {
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
	
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		if (!event.getPlayer().hasPermission(Permissions.joinEditMode())) {
			event.disallow(Result.KICK_OTHER, "The server is currently down for maintenance!");
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.getPlayer().setGameMode(GameMode.CREATIVE);
		event.getPlayer().sendMessage(Messages.maintenance());
		
		Main.layout().broadcast("*" + event.getPlayer().getName() + "* joined the server!");
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Main.layout().broadcast("*" + event.getPlayer().getName() + "* left the server!");
	}
	
}
