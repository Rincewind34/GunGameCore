package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameReflectException extends GunGameException {
	
	public static GunGameReflectException fromOther(Exception ex) {
		return new GunGameReflectException(ex);
	}
	
	protected GunGameReflectException(Exception ex) {
		super(ex);
	}
	
}
