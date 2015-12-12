package lib.securebit.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public abstract @interface ListenerBundle {

	public abstract String[] name() default { "bundle.all" };
	
}
