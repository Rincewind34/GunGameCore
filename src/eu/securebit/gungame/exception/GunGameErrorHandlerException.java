package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameErrorHandlerException extends GunGameException {
	
	public static GunGameErrorHandlerException unknownObjectID(String objectId) {
		return new GunGameErrorHandlerException("The objectId '" + objectId + "' is unknown!");
	}
	
	public static GunGameErrorHandlerException variables() {
		return new GunGameErrorHandlerException("All variables should be present!");
	}
	
	public static GunGameErrorHandlerException layoutType(String objectId) {
		return new GunGameErrorHandlerException("Invalid layouttype for objectId '" + objectId+ "'!");
	}
	
	public static GunGameErrorHandlerException unpresentError(String objectId) {
		return new GunGameErrorHandlerException("The error with the objectId '" + objectId+ "' has to be present!");
	}
	
	protected GunGameErrorHandlerException(String msg) {
		super(msg);
	}

}
