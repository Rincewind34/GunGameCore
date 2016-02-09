package eu.securebit.gungame.ioutil;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import eu.securebit.gungame.exception.GunGameIOException;
import eu.securebit.gungame.util.ConfigDefault;

public class ConfigUtil {
	
	public static void setLocation(FileConfiguration config, String path, Location loc) {
		config.set(path + ".world", loc.getWorld().getName());
		config.set(path + ".x", loc.getX());
		config.set(path + ".y", loc.getY());
		config.set(path + ".z", loc.getZ());
		config.set(path + ".yaw", loc.getYaw());
		config.set(path + ".pitch", loc.getPitch());
	}
	
	public static void setLocation(String path, Location loc, List<ConfigDefault> defaults) {
		defaults.add(new ConfigDefault(path + ".world", loc.getWorld().getName(), String.class));
		defaults.add(new ConfigDefault(path + ".x", loc.getX(), double.class));
		defaults.add(new ConfigDefault(path + ".y", loc.getY(), double.class));
		defaults.add(new ConfigDefault(path + ".z", loc.getZ(), double.class));
		defaults.add(new ConfigDefault(path + ".yaw", loc.getYaw(), double.class));
		defaults.add(new ConfigDefault(path + ".pitch", loc.getPitch(), double.class));
	}
	
	public static Location getLocation(FileConfiguration config, String path) {
		String worldName = config.getString(path + ".world");
		World world = Bukkit.getWorld(worldName);
		
		if (world == null) {
			throw GunGameIOException.unknownWorld(worldName);
		}
		
		double x = config.getDouble(path + ".x");
		double y = config.getDouble(path + ".y");
		double z = config.getDouble(path + ".z");
		float yaw = (float) config.getDouble(path + ".yaw");
		float pitch = (float) config.getDouble(path + ".pitch");
		
		return new Location(world, x, y, z, yaw, pitch);
	}
}
