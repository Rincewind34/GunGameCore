package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameErrorPresentException extends RuntimeException {
	
	public static GunGameErrorPresentException create() {
		return new GunGameErrorPresentException();
	}
	
	protected GunGameErrorPresentException() {
		super("This operation is only supported without the present errors!");
	}
	
}
