package main.Engine.event.game;

import main.Engine.engine.GameEngine;

public class StopEvent extends GameEvent
{
	private GameEngine engine;

	public StopEvent(GameEngine engine)
	{
		this.engine = engine;
	}

	public GameEngine getEngine()
	{
		return engine;
	}
}
