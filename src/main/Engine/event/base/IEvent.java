package main.Engine.event.base;

public interface IEvent
{
	default boolean isCancelled()
	{
		return false;
	}

	default void setCancelled(boolean cancelled)
	{
		if (!isCancellable()) throw new IllegalStateException("Event " + this + " isn't Cancellable!");
	}

	default boolean isCancellable()
	{
		return true;
	}

	IEvent Pre();

	IEvent Post();

	State getState();

	enum State
	{
		PRE,
		EVENT,
		POST
	}
}
