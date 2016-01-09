package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.defaults.DefaultGameStateSpawns;
import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.util.Util;

public class GameStateSpawns extends DefaultGameStateSpawns<GunGame> {

	public GameStateSpawns(GunGame game) {
		super(game, Util.getSpawns(game), SpawnSpreading.RANDOM, 1);
	}

	@Override
	public void updateScoreboard(GamePlayer player) {
		
	}

	@Override
	public void stageInformation(InfoLayout layout) {
		
	}

	@Override
	public boolean intoSpreading(GamePlayer player) {
		return true;
	}

	@Override
	protected String getMessageCountdown(int secondsleft) {
		return null;
	}

}
