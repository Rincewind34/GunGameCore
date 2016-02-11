package eu.securebit.gungame.io.abstracts;

public interface FileConfig extends SimpleFile {
	
	public abstract void save();
	
	public abstract void checkAccessability();
	
	public abstract boolean isAccessable();
	
}
