package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameSessionException extends GunGameException {
	
	public static GunGameSessionException gameAlreadyExists(String name) {
		return new GunGameSessionException("The game '" + name + "' does already exist!");
	}
	
	public static GunGameSessionException gameNotExists(String name) {
		return new GunGameSessionException("The game '" + name + "' does not exist!");
	}
	
	protected GunGameSessionException(String msg) {
		super(msg);
	}

}
