package eu.securebit.gungame.game;

import java.util.List;

import org.bukkit.entity.Player;

import eu.securebit.gungame.interpreter.GameOptions;
import eu.securebit.gungame.interpreter.GunGameMap;
import eu.securebit.gungame.interpreter.GunGameScoreboard;
import eu.securebit.gungame.interpreter.LevelManager;
import eu.securebit.gungame.interpreter.Messanger;

public interface GunGame {

	public abstract int getSize();

	public abstract void kickPlayer(Player player, String cause);

	public abstract void quitPlayer(Player player);

	public abstract void playConsoleMessage(String msg);

	public abstract void resetPlayer(Player player);

	public abstract void mute(boolean mute);

	public abstract void setEditMode(boolean value);

	public abstract void initWinner(GunGamePlayer player);

	public abstract void calculateGameState();

	public abstract boolean isEditMode();

	public abstract boolean isReady();

	public abstract int getMinPlayerCount();

	public abstract int getStartLevel();

	public abstract String getName();

	public abstract GunGameScoreboard getScoreboard();

	public abstract Messanger getMessanger();

	public abstract LevelManager getLevelManager();

	public abstract GameOptions getOptions();

	public abstract GunGameMap getLocationManager();

	public abstract GameInterface getInterface();

	public abstract GunGamePlayer getWinner();

	public abstract List<GameCheck> getChecks();

}