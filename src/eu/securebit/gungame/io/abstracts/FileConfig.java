package eu.securebit.gungame.io.abstracts;

public interface FileConfig extends SimpleFile {
	
	public abstract void save();
	
	public abstract void validate();
	
	public abstract boolean isAccessable();
	
}
