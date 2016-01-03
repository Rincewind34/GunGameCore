package lib.securebit.game;

import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public interface Game<P extends GamePlayer> extends Listener {
	
	public abstract List<P> getPlayers();
	
	public abstract List<World> getWorlds();
	
	public abstract void registerPlayer(P player);
	
	public abstract void unregisterPlayer(Player player);
	
	public abstract void resetPlayer(Player player);
		
	public abstract void registerWorld(World world);
	
	public abstract void unregisterWorld(World world);
	
	public abstract void unregisterWorld(String world);
	
	public abstract boolean containsWorld(World world);
	
	public abstract boolean containsWorld(String world);
	
	public abstract boolean containsPlayer(Player player);
	
	public abstract Plugin getPlugin();
	
	public abstract GamePlayer getPlayer(Player player);
	
	public abstract GameStateManager<?> getManager();
	
}
