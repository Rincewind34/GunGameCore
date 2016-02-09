package eu.securebit.gungame.exception;

@SuppressWarnings("serial")
public class GunGameJarException extends GunGameIOException {
	
	public static GunGameJarException parsingUnable() {
		return new GunGameJarException("Cannot parse jarfile!");
	}
	
	public static GunGameJarException noMainclass(Class<?> superClass) {
		return new GunGameJarException("The jar doesn't contain a mainclass (subclass of abstract " + superClass.getSimpleName() + ")!");
	}
	
	protected GunGameJarException(String msg) {
		super(msg);
	}
	
}
