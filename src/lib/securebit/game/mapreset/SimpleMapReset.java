package lib.securebit.game.mapreset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

public class SimpleMapReset implements MapReset {

	private final List<World> worlds;
	private final Map<Location, MaterialData> saves;
	
	private boolean recording;
	
	public SimpleMapReset() {
		this.worlds = new ArrayList<>();
		this.saves = new HashMap<>();
		this.recording = false;
	}
	
	@Override
	public void add(World world) {
		if (!this.worlds.contains(world)) {
			this.worlds.add(world);
		}
	}

	@Override
	public void remove(World world) {
		if (this.worlds.contains(world)) {
			this.worlds.remove(world);
		}
	}

	@Override
	public void startRecord() {
		this.recording = true;
	}
	
	@Override
	public void stopRecord() {
		this.recording = false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void breakBlock(Location location, Block original) {
		if (this.recording && this.worlds.contains(location.getWorld())) {
			this.saves.put(location, new MaterialData(original.getType(), original.getData()));
		}
	}

	@Override
	public void placeBlock(Location location) {
		if (this.recording && this.worlds.contains(location.getWorld())) {
			this.saves.put(location, new MaterialData(Material.AIR));
		}
	}

	@Override
	public int discard() {
		int count = this.saves.size();
		this.saves.clear();
		this.recording = false;
		return count;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int rollback() {
		int count = this.saves.size();
		this.saves.forEach((location, data) -> {
			location.getBlock().setType(data.getItemType());
			location.getBlock().setData(data.getData());
		});
		this.saves.clear();
		this.recording = false;
		return count;
	}
}