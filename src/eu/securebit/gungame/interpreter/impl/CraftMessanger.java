package eu.securebit.gungame.interpreter.impl;

import org.bukkit.entity.Player;

import eu.securebit.gungame.exception.GunGameException;
import eu.securebit.gungame.interpreter.Messanger;
import eu.securebit.gungame.io.configs.FileMessages;

public class CraftMessanger implements Messanger {
	
	private FileMessages file;
	
	public CraftMessanger(FileMessages file) {
		if (!file.isAccessable()) {
			throw new GunGameException("Cannot interpret message-file '" + file.getAbsolutePath() + "'!");
		}
		
		this.file = file;
	}
	
	@Override
	public String getJoinLobby() {
		return this.file.getJoinLobby();
	}

	@Override
	public String getQuitLobby() {
		return this.file.getQuitLobby();
	}

	@Override
	public String getCountdownLobby(int secondsLeft) {
		return this.file.getCountdownLobby(secondsLeft);
	}

	@Override
	public String getCountdownGrace(int secondsLeft) {
		return this.file.getCountdownGrace(secondsLeft);
	}

	@Override
	public String getCountdownEnd(int secondsLeft) {
		return this.file.getCountdownEnd(secondsLeft);
	}

	@Override
	public String getMapTeleport() {
		return this.file.getMapTeleport();
	}

	@Override
	public String getGracePeriodStarts() {
		return this.file.getGracePeriodStarts();
	}

	@Override
	public String getGracePeriodEnds() {
		return this.file.getGracePeriodEnds();
	}

	@Override
	public String getServerQuit() {
		return this.file.getServerQuit();
	}

	@Override
	public String getWinner(Player player) {
		return this.file.getWinner(player);
	}

	@Override
	public String getKillBroadcast(Player victim, Player killer) {
		return this.file.getKillBroadcast(victim, killer);
	}

	@Override
	public String getDeathBroadcast(Player victim) {
		return this.file.getDeathBroadcast(victim);
	}

	@Override
	public String getRespawn(int level) {
		return this.file.getRespawn(level);
	}
	
	public FileMessages getFile() {
		return this.file;
	}

}
