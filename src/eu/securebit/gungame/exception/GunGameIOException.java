package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameIOException extends RuntimeException {
	
	public GunGameIOException(String msg) {
		super(msg);
	}
	
	public GunGameIOException(String msg, Exception ex) {
		super(msg, ex);
	}
	
}