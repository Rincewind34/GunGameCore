package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameFixException extends GunGameException {
	
	public static GunGameFixException variables() {
		return new GunGameFixException("Invalid variables-length!");
	}
	
	protected GunGameFixException(String msg) {
		super(msg);
	}

}
