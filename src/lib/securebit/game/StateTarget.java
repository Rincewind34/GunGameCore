package lib.securebit.game;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public abstract @interface StateTarget {
	
	public abstract String[] states();
	
	public abstract boolean invert() default false;
	
}
