package lib.securebit.game.util;

public class PingResult {
	
	private String motd;
	private String currentState;
	
	private int onlinePlayers;
	private int gameSize;
	
	public PingResult(String motd, String currentState, int onlinePlayers, int gameSize) {
		this.motd = motd;
		this.currentState = currentState;
		this.onlinePlayers = onlinePlayers;
		this.gameSize = gameSize;
	}

	public String getMotd() {
		return this.motd;
	}

	public String getCurrentState() {
		return this.currentState;
	}

	public int getOnlinePlayers() {
		return this.onlinePlayers;
	}

	public int getGameSize() {
		return this.gameSize;
	}
	
}
