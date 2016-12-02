package main.Engine.event.base;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler
{
	@FunctionalInterface
	interface FunctionalHandler<E extends IEvent>
	{
		void handle(E event);
	}
}
