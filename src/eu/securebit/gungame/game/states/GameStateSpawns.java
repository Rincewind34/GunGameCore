package eu.securebit.gungame.game.states;

import lib.securebit.InfoLayout;
import lib.securebit.game.GamePlayer;
import lib.securebit.game.defaults.DefaultGameStateSpawns;
import eu.securebit.gungame.game.CraftGunGame;

public class GameStateSpawns extends DefaultGameStateSpawns<CraftGunGame> {

	public GameStateSpawns(CraftGunGame game) {
		super(game, game.getMap().getSpawnPoints(), SpawnSpreading.RANDOM, 1);
	}

	@Override
	public void updateScoreboard(GamePlayer player) {
		
	}

	@Override
	public void stageInformation(InfoLayout layout) {
		
	}
	
	@Override
	public String getMotD() {
		return null;
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
