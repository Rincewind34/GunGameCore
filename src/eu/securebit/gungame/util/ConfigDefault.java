package eu.securebit.gungame.util;

import org.bukkit.configuration.file.FileConfiguration;

import eu.securebit.gungame.exception.GunGameIOException;

public class ConfigDefault {
	
	private String path;
	
	private Object value;
	
	private Class<?> type;
	
	public ConfigDefault(String path, Object value, Class<?> type) {
		this.path = path;
		this.value = value;
		this.type = type;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public Object getValue() {
		return this.value;
	}
	
	public boolean validate(FileConfiguration config) {
		if (this.type == null) {
			return true;
		} else if (this.type == String.class) {
			return config.isString(this.path);
		} else if (this.type == int.class) {
			return config.isInt(this.path);
		} else if (this.type == double.class) {
			return config.isDouble(this.path);
		} else if (this.type == boolean.class) {
			return config.isBoolean(this.path);
		} else {
			throw new GunGameIOException("Unknown type '" + this.type.getName() + "'!");
		}
	}
	
}
