package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.defaults.DefaultGameStateLobby;

import org.bukkit.Bukkit;

import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.Permissions;
import eu.securebit.gungame.Util;

public class GameStateLobby extends DefaultGameStateLobby {
	
	public GameStateLobby() {
		super(Main.instance().getGame(),
				Main.instance().getFileConfig().getLocationLobby(),
				Permissions.premium(), Permissions.teammember(),
				Main.instance().getFileConfig().getMaxPlayers(),
				Main.instance().getFileConfig().getMinPlayers(),
				120,
				true);
	}

	@Override
	public void start() {
		Main.layout().message(Bukkit.getConsoleSender(), "Entering gamephase: *Lobby*");
		
		super.start();
	}

	@Override
	public void stop() {
		Main.layout().message(Bukkit.getConsoleSender(), "Leaving gamephase: *Lobby*");
		
		super.stop();
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Enough players: " + Util.parseBoolean(Bukkit.getOnlinePlayers().size() >= Main.instance().getFileConfig().getMinPlayers(), layout));
		layout.line("Seconds left: " + this.getCountdown().getSecondsLeft());
	}
	
	@Override
	public void updateScoreboard(GamePlayer player) {
		// TODO
	}
	
	@Override
	public String getName() {
		return "lobby";
	}

	@Override
	protected String getKickMessage(int levelKicked) {
		if (levelKicked == 1) {
			return Messages.lobbyKickPremium();
		} else if (levelKicked == 2) {
			return Messages.lobbyKickStaffMember();
		} else {
			throw new RuntimeException("Unknown kicklevel!");
		}
	}

	@Override
	protected String getMessageServerFull() {
		return Messages.lobbyFull();
	}

	@Override
	protected String getMessageNotJoinable() {
		return Messages.serverNotJoinable();
	}

	@Override
	protected String getMessageCountdown(int secondsLeft) {
		return Main.instance().getFileConfig().getMessageLobbyCountdown(secondsLeft);
	}
	
	@Override
	protected void onCountdownStop() {
		this.getGame().getManager().skip(2);
	}
	
}
