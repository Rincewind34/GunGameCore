package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateLobby;

import org.bukkit.Bukkit;

import eu.securebit.gungame.GunGame;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.Messages;
import eu.securebit.gungame.Permissions;
import eu.securebit.gungame.Util;

public class GameStateLobby extends DefaultGameStateLobby {
	
	public GameStateLobby(GunGame gungame) {
		super(gungame,
				gungame.getSettings().lobby().getLobbyLocation(),
				Permissions.premium(), Permissions.teammember(),
				gungame.getSettings().getMaxPlayerCount(),
				gungame.getSettings().getMinPlayerCount(),
				gungame.getSettings().lobby().getLobbyCountdownLength(),
				true);
		
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, gungame.getSettings().lobby().getJoinMessage());
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, gungame.getSettings().lobby().getQuitMessage());
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
		layout.line("Enough players: " + Util.parseBoolean(Bukkit.getOnlinePlayers().size() >= ((GunGame) this.getGame()).getSettings().getMinPlayerCount(), layout));
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
		return ((GunGame) this.getGame()).getSettings().lobby().getCountdownMessage(secondsLeft);
	}
	
	@Override
	protected void onCountdownStop() {
		this.getGame().getManager().next(); //TODO skip(2)
	}
	
}
