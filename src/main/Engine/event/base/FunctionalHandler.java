package main.Engine.event.base;

@FunctionalInterface
public interface FunctionalHandler<E extends IEvent>
{
	void handle(E event);
}
