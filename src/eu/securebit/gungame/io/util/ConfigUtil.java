package eu.securebit.gungame.io.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import eu.securebit.gungame.exception.UnknownWorldException;

public class ConfigUtil {

	public static void setLocation(FileConfiguration config, String path, Location loc, boolean addDefault) {
		if (addDefault) {
			config.addDefault(path + ".world", loc.getWorld().getName());
			config.addDefault(path + ".x", loc.getX());
			config.addDefault(path + ".y", loc.getY());
			config.addDefault(path + ".z", loc.getZ());
			config.addDefault(path + ".yaw", loc.getYaw());
			config.addDefault(path + ".pitch", loc.getPitch());
		} else {
			config.set(path + ".world", loc.getWorld().getName());
			config.set(path + ".x", loc.getX());
			config.set(path + ".y", loc.getY());
			config.set(path + ".z", loc.getZ());
			config.set(path + ".yaw", loc.getYaw());
			config.set(path + ".pitch", loc.getPitch());
		}
	}
	
	public static Location getLocation(FileConfiguration config, String path) throws UnknownWorldException {
		String worldName = config.getString(path + ".world");
		World world = Bukkit.getWorld(worldName);
		
		if (world == null) {
			throw new UnknownWorldException("The given world '" + worldName + "' does not exists!");
		}
		
		double x = config.getDouble(path + ".x");
		double y = config.getDouble(path + ".y");
		double z = config.getDouble(path + ".z");
		float yaw = (float) config.getDouble(path + ".yaw");
		float pitch = (float) config.getDouble(path + ".pitch");
		
		return new Location(world, x, y, z, yaw, pitch);
	}
}
