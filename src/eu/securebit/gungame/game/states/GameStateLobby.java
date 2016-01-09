package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateLobby;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.util.CoreMessages;
import eu.securebit.gungame.util.Permissions;
import eu.securebit.gungame.util.Util;

public class GameStateLobby extends DefaultGameStateLobby<GunGame> {
	
	public GameStateLobby(GunGame gungame) {
		super(gungame,
				gungame.getSettings().locations().getLobbyLocation(),
				Permissions.premium(), Permissions.teammember(),
				gungame.getSettings().options().getMaxPlayerCount(),
				gungame.getSettings().options().getMinPlayerCount(),
				gungame.getSettings().options().getLobbyCountdownLength(),
				true);
		
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, gungame.getSettings().files().getMessages().getJoinLobby());
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, gungame.getSettings().files().getMessages().getQuitLobby());
	}

	@Override
	public void start() {
		this.getGame().playConsoleMessage(Main.layout().format("Entering gamephase: *Lobby*"));
		
		super.start();
	}

	@Override
	public void stop() {
		this.getGame().playConsoleMessage(Main.layout().format("Leaving gamephase: *Lobby*"));
		
		super.stop();
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Enough players: " + Util.parseBoolean(this.getGame().getPlayers().size() >= this.getGame().getSettings().options().getMinPlayerCount(), layout));
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
			return CoreMessages.lobbyKickPremium();
		} else if (levelKicked == 2) {
			return CoreMessages.lobbyKickStaffMember();
		} else {
			throw new RuntimeException("Unknown kicklevel!");
		}
	}

	@Override
	protected String getMessageServerFull() {
		return CoreMessages.lobbyFull();
	}

	@Override
	protected String getMessageNotJoinable() {
		return CoreMessages.serverNotJoinable();
	}

	@Override
	protected String getMessageCountdown(int secondsLeft) {
		return this.getGame().getSettings().files().getMessages().getCountdownLobby(secondsLeft);
	}
	
	@Override
	protected void onCountdownStop() {
		this.getGame().getManager().next(); //TODO skip(2)
	}
	
}
