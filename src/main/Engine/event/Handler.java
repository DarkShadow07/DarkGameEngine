package main.Engine.event;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler
{
	Class<? extends IEvent> value();

	@FunctionalInterface
	interface FunctionalHandler
	{
		void handle(IEvent event);
	}
}
