package eu.securebit.gungame.ioimpl.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.util.NumberConversions;

import eu.securebit.gungame.errorhandling.CraftErrorHandler;
import eu.securebit.gungame.io.configs.FileMap;
import eu.securebit.gungame.ioutil.DataUtil;
import eu.securebit.gungame.util.ConfigDefault;

public class CraftFileMap extends CraftFileGunGameConfig implements FileMap {

	public CraftFileMap(File file, CraftErrorHandler handler) {
		super(file, handler, FileMap.ERROR_LOAD, FileMap.ERROR_FOLDER, FileMap.ERROR_CREATE, FileMap.ERROR_MALFORMED, "map");
		
		this.getDefaults().add(new ConfigDefault("map.name", "BasicMap", String.class));
		this.getDefaults().add(new ConfigDefault("map.builders", Arrays.asList("BasicBuilder"), List.class));
		this.getDefaults().add(new ConfigDefault("map.location.spawns", Arrays.asList(), List.class));
		this.getDefaults().add(new ConfigDefault("map.next-spawn-id", 0, int.class));
	}

	@Override
	public void setNextSpawnId(int nextId) {
		this.checkReady();
		
		super.config.set("map.next-spawn-id", nextId);
		this.save();
	}

	@Override
	public void setSpawns(Map<Integer, Location> spawns) {
		this.checkReady();
		
		List<String> list = new ArrayList<>();
		
		for (Entry<Integer, Location> entry : spawns.entrySet()) {
			list.add(this.createCSV(entry));
		}
		
		super.config.set("map.location.spawns", list);
		this.save();
	}

	@Override
	public int getNextSpawnId() {
		this.checkReady();
		
		return super.config.getInt("map.next-spawn-id");
	}

	@Override
	public String getMapName() {
		this.checkReady();
		
		return super.config.getString("map.name");
	}

	@Override
	public List<String> getBuilders() {
		this.checkReady();
		
		return super.config.getStringList("map.builders");
	}

	@Override
	public Map<Integer, Location> getSpawns() {
		this.checkReady();
		
		List<String> list = super.config.getStringList("map.location.spawns");
		Map<Integer, Location> spawns = new HashMap<>();
		
		for (String csv : list) {
			spawns.put(this.readSpawnLocation(csv).getKey(), this.readSpawnLocation(csv).getValue());
		}
		
		return spawns;
	}
	
	private Entry<Integer, Location> readSpawnLocation(String csv) {
		int id = NumberConversions.toInt(DataUtil.getFromCSV(csv, 0));
		
		double x = NumberConversions.toDouble(DataUtil.getFromCSV(csv, 1));
		double y = NumberConversions.toDouble(DataUtil.getFromCSV(csv, 2));
		double z = NumberConversions.toDouble(DataUtil.getFromCSV(csv, 3));
		float yaw = NumberConversions.toFloat(DataUtil.getFromCSV(csv, 4));
		float pitch = NumberConversions.toFloat(DataUtil.getFromCSV(csv, 5));
		
		return new Entry<Integer, Location>() {
			
			@Override
			public Location setValue(Location value) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public Location getValue() {
				return new Location(null, x, y, z, yaw, pitch);
			}
			
			@Override
			public Integer getKey() {
				return id;
			}
		};
	}
	
	private String createCSV(Entry<Integer, Location> entry) {
		return DataUtil.toCSV(entry.getKey(),
				entry.getValue().getX(),
				entry.getValue().getY(),
				entry.getValue().getZ(),
				entry.getValue().getYaw(),
				entry.getValue().getPitch());
	}

}
