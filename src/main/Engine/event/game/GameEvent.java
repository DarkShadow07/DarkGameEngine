package main.Engine.event.game;

import main.Engine.event.base.IEvent;

public class GameEvent implements IEvent
{
	private boolean cancelled = false, cancellable = true;
	private State state = State.EVENT;

	public GameEvent()
	{
		this(true);
	}

	public GameEvent(boolean cancellable)
	{
		this.cancellable = cancellable;
	}

	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancellable()
	{
		return cancellable;
	}

	@Override
	public IEvent Pre()
	{
		state = State.PRE;

		return this;
	}

	@Override
	public IEvent Post()
	{
		state = State.POST;

		return this;
	}

	@Override
	public State getState()
	{
		return state;
	}
}
