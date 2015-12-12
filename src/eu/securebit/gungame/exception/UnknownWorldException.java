package eu.securebit.gungame.exception;

public class UnknownWorldException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UnknownWorldException(String cause) {
		super(cause);
	}

}
