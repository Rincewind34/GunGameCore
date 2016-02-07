package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameErrorPresentException extends RuntimeException {
	
	public GunGameErrorPresentException() {
		super("This operation is only supported without the present errors!");
	}
	
}
