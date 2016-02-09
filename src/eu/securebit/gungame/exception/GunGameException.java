package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameException extends RuntimeException {
	
	public static GunGameException fromOther(Exception ex) {
		return new GunGameException(ex);
	}
	
	protected GunGameException(Exception ex) {
		super("(" + ex.getClass().getSimpleName() + ")" + ex.getMessage(), ex);
	}
	
	protected GunGameException(String msg) {
		super(msg);
	}
	
}
