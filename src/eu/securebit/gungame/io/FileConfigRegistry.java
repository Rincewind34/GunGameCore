package eu.securebit.gungame.io;

import java.util.List;

import eu.securebit.gungame.util.FileValidatable;

public interface FileConfigRegistry extends FileValidatable {
	
	public abstract void add(String file, String type);
	
	public abstract void remove(String file);
	
	public abstract boolean contains(String file);
	
	public abstract String get(String file);
	
	public abstract List<String> getEntry();
	
}
