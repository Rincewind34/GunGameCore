package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameException extends RuntimeException {
	
	public static GunGameException fromOther(Exception ex) {
		return new GunGameException(ex);
	}
	
	public static GunGameException invalidWorld(String world) {
		return new GunGameException("The world '" + world + "' is invalid!");
	}
	
	protected GunGameException(Exception ex) {
		super("(" + ex.getClass().getSimpleName() + ") " + ex.getMessage(), ex);
	}
	
	protected GunGameException(String msg) {
		super(msg);
	}
	
}
