package eu.securebit.gungame.exception;

public class InvalidLevelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidLevelException(String cause) {
		super(cause);
	}
}
