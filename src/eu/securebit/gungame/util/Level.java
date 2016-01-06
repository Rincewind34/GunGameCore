package eu.securebit.gungame.util;

import org.bukkit.entity.Player;

public interface Level {
	
	public abstract void equip(Player player);
	
	public abstract int getId();
	
}
