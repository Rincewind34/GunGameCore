package lib.securebit;

import java.util.Collection;

public class Validate {

	public static void notEmpty(Collection<?> collection, String error) {
		if (collection.isEmpty()) {
			throw new IllegalArgumentException(error);
		}
	}
	
	public static void notNull(Object object, String error) {
		if (object == null) {
			throw new NullPointerException(error);
		}
	}
}
