package lib.securebit.game.mapreset;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public interface MapReset {
	
	public abstract void add(World world);
	
	public abstract void remove(World world);
	
	public abstract void startRecord();
	
	public abstract void stopRecord();
	
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
