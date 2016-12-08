package main.Engine.engine;

import main.Engine.engine.model.ModelLoader;
import main.Engine.engine.model.ModelRenderer;
import main.Engine.engine.model.resource.ResourceManager;
import main.Engine.engine.network.NetworkServer;
import main.Engine.event.EventHandler;
import main.Engine.event.game.InitializationEvent;
import main.Engine.event.game.RenderEvent;
import main.Engine.event.game.StopEvent;
import main.Engine.event.game.TickEvent;
import main.Engine.font.FontRenderer;
import main.Engine.settings.GameSettings;
import main.Engine.util.Log;
import main.Engine.util.Util;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

public class GameEngine
{
	public final String icon, name;
	public final int targetFrames, targetTicks, width, height;
	public final boolean fullscreen;
	public int frames, ticks;

	public GameLoop gameLoop;

	public NetworkServer networkServer;

	public ModelRenderer modelRenderer;
	public FontRenderer fontRenderer;
	public ResourceManager resourceManager;
	public GameSettings gameSettings;

	public String gameDir;

	private Thread mainThread = Thread.currentThread();

	public GameEngine(String icon, String name, int frames, int ticks, int width, int height, boolean fullscreen)
	{
		this.icon = icon;
		this.name = name;
		targetFrames = frames;
		targetTicks = ticks;
		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
	}

	public GameEngine(String name, int frames, int ticks, int width, int height, boolean fullscreen)
	{
		this("icon", name, frames, ticks, width, height, fullscreen);
	}

	public GameEngine(int frames, int ticks, int width, int height, boolean fullscreen)
	{
		this("Dark Engine", frames, ticks, width, height, fullscreen);
	}

	public void start()
	{
		EventHandler.instance.sendEvent(new InitializationEvent(this).Pre());

		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			if (fullscreen)
				Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			try
			{
				Display.setIcon(new ByteBuffer[]{Util.imageToByteBuffer(Class.class.getResourceAsStream(String.format("/%s.png", icon)))});
			} catch (Exception e)
			{
				Log.error("Failed to set Icon", icon, e);
			}

			Display.create(new PixelFormat(), new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true));
			Display.setTitle(name);
			Display.setResizable(!fullscreen);
			Display.setInitialBackground(0, 0, 0);
			Display.sync(targetFrames);

			GL11.glViewport(0, 0, width, height);

			EventHandler.instance.sendEvent(new InitializationEvent(this).Post());

			Log.info(String.format("%s Started", name == null ? "GameEngine" : name));

			runGameLoop();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void runGameLoop()
	{
		gameLoop = new GameLoop(targetFrames, targetTicks);

		while (!Display.isCloseRequested())
		{
			gameLoop.run();

			if (gameLoop.shouldUpdate())
				EventHandler.instance.sendEvent(new TickEvent(this));

			if (gameLoop.shouldRender())
			{
				Display.update();

				if (Display.wasResized() && modelRenderer != null)
					modelRenderer.createProjectionMatrix();

				EventHandler.instance.sendEvent(new RenderEvent(this));
			}
		}

		stopGameLoop();
	}

	private void stopGameLoop()
	{
		EventHandler.instance.sendEvent(new StopEvent(this).Pre());

		ModelLoader.instance.cleanUp();

		EventHandler.instance.sendEvent(new StopEvent(this).Post());

		Display.destroy();
	}
}
