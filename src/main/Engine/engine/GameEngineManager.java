package main.Engine.engine;

import main.Engine.util.Log;

public class GameEngineManager
{
	public static final GameEngineManager instance = new GameEngineManager();

	private GameEngine gameEngine;

	public GameEngine createGameEngine(String name, int frames, int ticks, int width, int height, boolean fullscreen)
	{
		Log.info(String.format("Created GameEngine, \"%s\", %s frames, %s ticks, %s x %s, %s", name, frames, ticks, width, height, fullscreen ? "Fullscreen" : "Windowed"));

		return gameEngine = new GameEngine(name, frames, ticks, width, height, fullscreen);
	}

	public GameEngine createGameEngine(String icon, String name, int frames, int ticks, int width, int height, boolean fullscreen)
	{
		Log.info(String.format("Created GameEngine, \"%s\", %s frames, %s ticks, %s x %s, %s", name, frames, ticks, width, height, fullscreen ? "Fullscreen" : "Windowed"));

		return gameEngine = new GameEngine(icon, name, frames, ticks, width, height, fullscreen);
	}

	public GameEngine createGameEngine(int frames, int ticks, int width, int height, boolean fullscreen)
	{
		Log.info(String.format("Created GameEngine, %s frames, %s ticks, %s x %s, %s", frames, ticks, width, height, fullscreen ? "Fullscreen" : "Windowed"));

		return gameEngine = new GameEngine(frames, ticks, width, height, fullscreen);
	}

	public GameEngine getGameEngine()
	{
		return gameEngine;
	}
}
