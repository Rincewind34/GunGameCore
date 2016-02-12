package eu.securebit.gungame.interpreter.impl;

import org.bukkit.entity.Player;

import eu.securebit.gungame.interpreter.Messanger;
import eu.securebit.gungame.io.configs.FileMessages;

public class CraftMessanger extends AbstractInterpreter<FileMessages> implements Messanger {
	
	public CraftMessanger(FileMessages file) {
		super(file, Messanger.ERROR_MAIN, Messanger.ERROR_INTERPRET);
	}
	
	@Override
	public String getJoinLobby() {
		return super.config.getJoinLobby();
	}

	@Override
	public String getQuitLobby() {
		return super.config.getQuitLobby();
	}

	@Override
	public String getCountdownLobby(int secondsLeft) {
		return super.config.getCountdownLobby(secondsLeft);
	}

	@Override
	public String getCountdownGrace(int secondsLeft) {
		return super.config.getCountdownGrace(secondsLeft);
	}

	@Override
	public String getCountdownEnd(int secondsLeft) {
		return super.config.getCountdownEnd(secondsLeft);
	}
	
	@Override
	public String getCountdownLobbyCancle(int currentPlayers, int minimalPlayers) {
		return super.config.getCountdownLobbyCancle(currentPlayers, minimalPlayers);
	}

	@Override
	public String getMapTeleport() {
		return super.config.getMapTeleport();
	}

	@Override
	public String getGracePeriodStarts() {
		return super.config.getGracePeriodStarts();
	}

	@Override
	public String getGracePeriodEnds() {
		return super.config.getGracePeriodEnds();
	}

	@Override
	public String getServerQuit() {
		return super.config.getServerQuit();
	}

	@Override
	public String getWinner(Player player) {
		return super.config.getWinner(player);
	}

	@Override
	public String getKillBroadcast(Player victim, Player killer) {
		return super.config.getKillBroadcast(victim, killer);
	}

	@Override
	public String getDeathBroadcast(Player victim) {
		return super.config.getDeathBroadcast(victim);
	}

	@Override
	public String getRespawn(int level) {
		return super.config.getRespawn(level);
	}
	
}
