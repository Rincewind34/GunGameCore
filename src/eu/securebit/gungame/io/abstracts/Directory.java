package eu.securebit.gungame.io.abstracts;

public interface Directory extends SimpleFile {
	
	public abstract String getAbsolutPath();
	
	public abstract String getRelativPath();
	
}
