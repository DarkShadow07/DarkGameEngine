package main.Engine.event.game;

import main.Engine.engine.GameEngine;

public class TickEvent extends GameEvent
{
	private GameEngine engine;

	public TickEvent(GameEngine engine)
	{
		this.engine = engine;
	}

	public GameEngine getEngine()
	{
		return engine;
	}
}
