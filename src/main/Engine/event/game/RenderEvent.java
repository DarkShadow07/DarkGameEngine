package main.Engine.event.game;

import main.Engine.engine.GameEngine;

public class RenderEvent extends GameEvent
{
	private GameEngine engine;

	public RenderEvent(GameEngine engine)
	{
		this.engine = engine;
	}

	public GameEngine getEngine()
	{
		return engine;
	}
}
