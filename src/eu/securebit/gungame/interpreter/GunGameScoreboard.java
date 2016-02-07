package eu.securebit.gungame.interpreter;

import org.bukkit.entity.Player;

import eu.securebit.gungame.game.GunGame;
import eu.securebit.gungame.interpreter.impl.CraftGunGameScoreboard;
import eu.securebit.gungame.io.configs.FileScoreboard;

public interface GunGameScoreboard {
	
	public static GunGameScoreboard create(FileScoreboard file, GunGame gungame) {
		return new CraftGunGameScoreboard(gungame, file);
	}
	
	public abstract void setup();

	public abstract void update(Player player);

	public abstract void updateAll();

	public abstract void delete();

	public abstract void create();

	public abstract void clearFromPlayers();

	public abstract void refresh();

	public abstract boolean exists();

	public abstract boolean isEnabled();
	
}