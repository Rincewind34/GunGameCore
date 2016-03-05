package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.Settings.StateSettings;
import lib.securebit.game.defaults.DefaultGameStateLobby;
import eu.securebit.gungame.Main;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.interpreter.Messanger.GunGameMotD;
import eu.securebit.gungame.util.Permissions;
import eu.securebit.gungame.util.Util;

public class GameStateLobby extends DefaultGameStateLobby<GunGame> {
	
	public GameStateLobby(GunGame gungame) {
		super(gungame,
				gungame.getLobbyLocation(),
				Permissions.premium(), Permissions.teammember(),
				gungame.getSize(),
				gungame.getMinPlayerCount(),
				gungame.getOptions().getCountdownLength(),
				gungame.getOptions().getPremiumSlots(),
				true, gungame.getOptions().premiumKick());
		
		this.getSettings().setValue(StateSettings.MESSAGE_JOIN, gungame.getMessanger().getJoinLobby());
		this.getSettings().setValue(StateSettings.MESSAGE_QUIT, gungame.getMessanger().getQuitLobby());
	}

	@Override
	public void start() {
		this.getGame().playConsoleDebugMessage("Entering gamephase: *Lobby*", Main.layout());
		
		super.start();
	}

	@Override
	public void stop() {
		this.getGame().playConsoleDebugMessage("Leaving gamephase: *Lobby*", Main.layout());
		
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
		
	}
	
	@Override
	public String getMotD() {
		if (this.canUserJoin()) {
			return this.getGame().getMessanger().getMotD(GunGameMotD.LOBBY_JOIN);
		} else if (this.canPremiumJoin()) {
			return this.getGame().getMessanger().getMotD(GunGameMotD.LOBBY_PREMIUM);
		} else {
			return this.getGame().getMessanger().getMotD(GunGameMotD.LOBBY_STAFF);
		}
	}

	@Override
	protected String getKickMessage(int levelKicked) {
		if (levelKicked == 1) {
			return this.getGame().getMessanger().getKickPremium();
		} else if (levelKicked == 2) {
			return this.getGame().getMessanger().getKickStaff();
		} else {
			throw new RuntimeException("Unknown kicklevel!");
		}
	}

	@Override
	protected String getMessageServerFull() {
		return this.getGame().getMessanger().getKickLobbyFull();
	}

	@Override
	protected String getMessageNotJoinable() {
		return this.getGame().getMessanger().getKickNotJoinable();
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
