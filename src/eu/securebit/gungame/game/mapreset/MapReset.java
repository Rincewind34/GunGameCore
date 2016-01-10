package eu.securebit.gungame.game.mapreset;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public abstract class MapReset {

	private static MapReset instance;
	
	public static MapReset getInstance() {
		return MapReset.instance == null ? MapReset.instance = new SimpleMapReset() : MapReset.instance;
	}
	
	public abstract void add(World world);
	
	public abstract void remove(World world);
	
	public abstract void startRecord();
	
	public abstract void breakBlock(Location location, Block original);
	
	public abstract void placeBlock(Location location);
	
	/**
	 * @return Number of discarded block changes
	 */
	public abstract int discard();
	
	/**
	 * @return Number of restored blocks on rollback
	 */
	public abstract int rollback();
}
