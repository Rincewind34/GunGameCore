package eu.securebit.gungame.interpreter.impl;

import org.bukkit.entity.Player;

import eu.securebit.gungame.interpreter.Messanger;
import eu.securebit.gungame.io.configs.FileMessages;
import eu.securebit.gungame.ioutil.IOUtil;

public class CraftMessanger extends AbstractInterpreter<FileMessages> implements Messanger {
	
	public CraftMessanger(FileMessages file) {
		super(file, Messanger.ERROR_MAIN, Messanger.ERROR_INTERPRET);
	}
	
	@Override
	public String getJoinLobby() {
		this.checkSuccess();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getJoinLobby());
	}

	@Override
	public String getQuitLobby() {
		this.checkSuccess();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getQuitLobby());
	}
	
	@Override
	public String getServerQuit() {
		this.checkSuccess();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getServerQuit());
	}

	@Override
	public String getCountdownLobby(int secondsLeft) {
		this.checkSuccess();
		
		String msg = super.config.getCountdownLobby();
		msg = IOUtil.replace(msg, "time", secondsLeft);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getCountdownGrace(int secondsLeft) {
		this.checkSuccess();
		
		String msg = super.config.getCountdownGrace();
		msg = IOUtil.replace(msg, "time", secondsLeft);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getCountdownEnd(int secondsLeft) {
		this.checkSuccess();
		
		String msg = super.config.getCountdownEnd();
		msg = IOUtil.replace(msg, "time", secondsLeft);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}
	
	@Override
	public String getCountdownLobbyCancle(int currentPlayers, int minimalPlayers) {
		this.checkSuccess();
		
		String msg = super.config.getCountdownLobbyCancle();
		msg = IOUtil.replace(msg, "current", currentPlayers);
		msg = IOUtil.replace(msg, "minimal", minimalPlayers);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getMapTeleport() {
		this.checkSuccess();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getMapTeleport());
	}

	@Override
	public String getGracePeriodStarts() {
		this.checkSuccess();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getGracePeriodStarts());
	}

	@Override
	public String getGracePeriodEnds() {
		this.checkSuccess();
		
		return IOUtil.prepare(this.getPrefix(), super.config.getGracePeriodEnds());
	}

	@Override
	public String getWinner(Player player) {
		this.checkSuccess();
		
		String msg = super.config.getWinner();
		msg = IOUtil.replace(msg, "winner", player.getDisplayName());
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getKillBroadcast(Player victim, Player killer) {
		this.checkSuccess();
		
		String msg =  super.config.getKillBroadcast();
		msg = IOUtil.replace(msg, "victim", victim.getDisplayName());
		msg = IOUtil.replace(msg, "killer", killer.getDisplayName());
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getDeathBroadcast(Player victim) {
		this.checkSuccess();
		
		String msg = super.config.getDeathBroadcast();
		msg = IOUtil.replace(msg, "victim", victim.getDisplayName());
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}

	@Override
	public String getRespawn(int level) {
		this.checkSuccess();
		
		String msg = super.config.getRespawn();
		msg = IOUtil.replace(msg, "level", level);
		
		return IOUtil.prepare(this.getPrefix(), msg);
	}
	
	private String getPrefix() {
		return super.config.getPrefix();
	}

	@Override
	public String getMotD(GunGameMotD motd) {
		switch (motd) {
		case END:
			return super.config.getMotDEnd();
		case GRACE:
			return super.config.getMotDGrace();
		case INGAME:
			return super.config.getMotDIngame();
		case LOBBY_JOIN:
			return super.config.getMotDLobbyJoin();
		case LOBBY_PREMIUM:
			return super.config.getMotDLobbyPremium();
		case LOBBY_STAFF:
			return super.config.getMotDLobbyStaff();
		case MAINTENDANCE:
			return super.config.getMotDMaintendance();
		default:
			return null;
		}
	}

	@Override
	public String getKickGameRunning() {
		this.checkSuccess();
		
		return super.config.getKickGameRunning();
	}

	@Override
	public String getKickPremium() {
		this.checkSuccess();
		
		return super.config.getKickPremium();
	}

	@Override
	public String getKickStaff() {
		this.checkSuccess();
		
		return super.config.getKickStaff();
	}

	@Override
	public String getKickLobbyFull() {
		this.checkSuccess();
		
		return super.config.getKickLobbyFull();
	}

	@Override
	public String getKickNotJoinable() {
		this.checkSuccess();
		
		return super.config.getKickNotJoinable();
	}

	@Override
	public String getKickMaintendance() {
		this.checkSuccess();
		
		return super.config.getKickMaintendance();
	}

}
