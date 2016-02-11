package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateLobby;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.CraftGunGame;
import eu.securebit.gungame.util.CoreMessages;
import eu.securebit.gungame.util.Permissions;
import eu.securebit.gungame.util.Util;

public class GameStateLobby extends DefaultGameStateLobby<CraftGunGame> {
	
	public GameStateLobby(CraftGunGame gungame) {
		super(gungame,
				gungame.getLobbyLocation(),
				Permissions.premium(), Permissions.teammember(),
				gungame.getMinPlayerCount(),
				gungame.getSize(),
				120, // TODO config lobbycountdownlength
				0, //TODO config premiumSlots
				true, true); //TODO config premiumKick
		
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, gungame.getMessanger().getJoinLobby());
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, gungame.getMessanger().getQuitLobby());
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
	public void load() {
		super.load();
	}
	
	@Override
	public void stageInformation(InfoLayout layout) {
		layout.line("Enough players: " + Util.parseBoolean(this.getGame().getPlayers().size() >= this.getGame().getMinPlayerCount(), layout));
		layout.line("Seconds left: " + this.getCountdown().getSecondsLeft());
	}
	
	@Override
	public void updateScoreboard(GamePlayer player) {
		// TODO
	}
	
	@Override
	public String getMotD() {
		return null; // TODO
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
		return this.getGame().getMessanger().getCountdownLobby(secondsLeft);
	}
	
	@Override
	protected String getMessageCountdownCancle() {
		return this.getGame().getMessanger().getCountdownLobbyCancle(this.getGame().getPlayers().size(), this.getGame().getMinPlayerCount());
	}
	
	@Override
	protected void onCountdownStop() {
		this.getGame().getManager().skip(2);
	}
	
}
