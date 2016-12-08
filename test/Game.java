import main.Engine.engine.GameEngine;
import main.Engine.engine.GameEngineManager;
import main.Engine.engine.audio.AudioManager;
import main.Engine.engine.camera.Camera;
import main.Engine.engine.model.ModelLoader;
import main.Engine.engine.model.ModelRenderer;
import main.Engine.engine.model.builder.ModelBuilder;
import main.Engine.engine.model.resource.ResourceLocation;
import main.Engine.engine.model.resource.ResourceManager;
import main.Engine.engine.model.resource.Resources;
import main.Engine.engine.model.shader.StaticShader;
import main.Engine.entity.Entity;
import main.Engine.event.EventHandler;
import main.Engine.event.base.FunctionalHandler;
import main.Engine.event.base.Handler;
import main.Engine.event.base.IEvent;
import main.Engine.event.game.InitializationEvent;
import main.Engine.event.game.RenderEvent;
import main.Engine.event.game.StopEvent;
import main.Engine.event.game.TickEvent;
import main.Engine.font.FontRenderer;
import main.Engine.font.Text;
import main.Engine.settings.GameSettings;
import main.Engine.util.GamePrintStream;
import main.Engine.util.math.Position;
import org.lwjgl.util.Color;

import java.io.File;

public class Game
{
	@Handler
	public final FunctionalHandler<TickEvent> tick = event ->
	{

	};
	@Handler
	public final FunctionalHandler<RenderEvent> render = event ->
	{
		GameEngine engine = event.getEngine();

		engine.modelRenderer.prepare();
		engine.modelRenderer.getShader().start();
		engine.modelRenderer.getShader().view(engine.modelRenderer.getCamera());
//		engine.modelRenderer.renderEntity(entity, engine.modelRenderer.getShader());
		engine.modelRenderer.getShader().stop();

		engine.fontRenderer.render();
	};
	@Handler
	public final FunctionalHandler<StopEvent> stop = event ->
	{
		if (event.getState() != IEvent.State.POST) return;

		GameEngine engine = event.getEngine();

		try
		{
			engine.gameSettings.saveSettings();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		engine.modelRenderer.getShader().remove();
		engine.fontRenderer.cleanUp();
		ModelLoader.instance.cleanUp();
		AudioManager.instance.cleanUp();
	};
	private Entity entity;
	private Text text;
	@Handler
	public final FunctionalHandler<InitializationEvent> init = event ->
	{
		if (event.getState() != IEvent.State.POST) return;

		GameEngine engine = event.getEngine();

		ModelLoader.instance.setEngine(engine);
		AudioManager.instance.setEngine(engine);
		engine.resourceManager = new ResourceManager(new Resources(new File(engine.gameSettings.<String>getValue("gameDir")), "res/"));
		engine.fontRenderer = new FontRenderer(new ResourceLocation("res/font/arial.fnt"));
		engine.modelRenderer = new ModelRenderer(new StaticShader());
		entity = new Entity(new Position(0, 0, 0), 1.8f, new ModelBuilder.Dynamic()
				.addQuad(-50, -50, -50, 50, 50, -50, 0, 0, 1, 1)
				.buildTextured(new ResourceLocation("icon.png")));

		text = new Text("hello!", 3.0f, new Position(0.5f, 0.5f), new Color(255, 0, 0));
		engine.fontRenderer.renderText(text);

		engine.modelRenderer.setCamera(new Camera()
		{
			@Override
			public void updatePosition()
			{

			}
		});
		engine.modelRenderer.getCamera().move(0, 0, -50);
	};

	public Game()
	{
//		SmartLog.instance.init("DarkEngine Log");
		System.setOut(new GamePrintStream(System.out));

		GameEngine engine = GameEngineManager.instance.createGameEngine(60, 20, 1280, 720, false);
		engine.gameDir = System.getenv("appdata") + "\\.darkEngine\\";
		engine.gameSettings = new GameSettings(engine);

		EventHandler.instance.registerHandler(this);

		engine.start();
	}
}
