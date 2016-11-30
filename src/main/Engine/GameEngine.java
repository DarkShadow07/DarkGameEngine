package main.Engine;

import com.sun.istack.internal.NotNull;
import main.Engine.font.FontRenderer;
import main.Engine.model.ModelLoader;
import main.Engine.model.ModelRenderer;
import main.Engine.network.NetworkServer;
import main.Engine.resource.ResourceManager;
import main.Engine.util.Log;
import main.Engine.util.Util;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class GameEngine
{
	public final String icon, name;
	public final int targetFrames, targetTicks, width, height;
	public final boolean fullscreen;
	public int frames, ticks;

	public List<Runnable> startHooks = new ArrayList<>(), tickHooks = new ArrayList<>(), renderHooks = new ArrayList<>(), stopHooks = new ArrayList<>();

	public NetworkServer networkServer;

	public ModelRenderer modelRenderer;
	public FontRenderer fontRenderer;
	public ResourceManager resourceManager;

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

	public void addStartHook(@NotNull Runnable hook)
	{
		if (startHooks.contains(hook))
			throw new IllegalArgumentException("StartHook previously registered!");
		startHooks.add(hook);
	}

	public void addTickHook(@NotNull Runnable hook)
	{
		if (tickHooks.contains(hook))
			throw new IllegalArgumentException("TickHook previously registered!");
		tickHooks.add(hook);
	}

	public void addRenderHook(@NotNull Runnable hook)
	{
		if (renderHooks.contains(hook))
			throw new IllegalArgumentException("RenderHook previously registered!");
		renderHooks.add(hook);
	}

	public void addStopHook(@NotNull Runnable hook)
	{
		if (stopHooks.contains(hook))
			throw new IllegalArgumentException("StopHook previously registered!");
		stopHooks.add(hook);
	}

	public void start()
	{
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

			startHooks.forEach(Runnable::run);

			Log.info(String.format("%s Started", name == null ? "GameEngine" : name));

			runGameLoop();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void runGameLoop()
	{
		long nanoLast = System.nanoTime();
		double deltaTick = 0, deltaFrame = 0;

		int ticks = 0, frames = 0;

		long timer = System.currentTimeMillis();

		while (!Display.isCloseRequested())
		{
			long nanoNow = System.nanoTime();

			deltaTick += (nanoNow - nanoLast) / (1000_000_000D / targetTicks);
			deltaFrame += (nanoNow - nanoLast) / (1000_000_000D / targetFrames);

			nanoLast = nanoNow;

			if (deltaTick >= 1)
			{
				tickHooks.forEach(Runnable::run);

				deltaTick--;

				ticks++;
			}

			if (deltaFrame >= 1)
			{
				Display.update();

				if (Display.wasResized() && modelRenderer != null)
					modelRenderer.createProjectionMatrix();

				renderHooks.forEach(Runnable::run);

				deltaFrame--;

				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;

				this.ticks = ticks;
				this.frames = frames;

				ticks = 0;
				frames = 0;
			}

			try
			{
				Thread.sleep(1);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		stopGameLoop();
	}

	private void stopGameLoop()
	{
		stopHooks.forEach(Runnable::run);

		ModelLoader.instance.cleanUp();

		Display.destroy();
	}
}
