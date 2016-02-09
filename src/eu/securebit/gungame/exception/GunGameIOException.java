package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameIOException extends GunGameException {
	
	public static GunGameIOException fromOther(Exception ex) {
		return new GunGameIOException(ex);
	}
	
	public static GunGameIOException unknownWorld(String world) {
		return new GunGameIOException("The world '" + world + "' does not exist!");
	}
	
	public GunGameIOException(String msg) {
		super(msg);
	}
	
	protected GunGameIOException(Exception ex) {
		super(ex);
	}
	
}