package main.Engine.event.game;

import main.Engine.engine.GameEngine;

public class InitializationEvent extends GameEvent
{
	private GameEngine engine;

	public InitializationEvent(GameEngine engine)
	{
		this.engine = engine;
	}

	public GameEngine getEngine()
	{
		return engine;
	}
}
