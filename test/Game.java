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
import main.Engine.util.math.Position;

import java.io.File;

public class Game
{
	public Game()
	{
		GameEngine engine = GameEngineManager.instance.createGameEngine(60, 20, 1280, 720, false);

		EventHandler.instance.registerHandler(this);

		engine.start();
	}

	private Entity entity;

	@Handler
	public final FunctionalHandler<InitializationEvent> init = event ->
	{
		if (event.getState() != IEvent.State.POST) return;

		GameEngine engine = event.getEngine();

		ModelLoader.instance.setEngine(engine);
		AudioManager.instance.setEngine(engine);
		engine.modelRenderer = new ModelRenderer(new StaticShader());
		engine.resourceManager = new ResourceManager(new Resources(new File("C:\\Users\\Yonatan\\Desktop\\Proyectos\\Java\\DarkGameEngine"), "res/"));
		entity = new Entity(new Position(0, 0, 0), 1.8f, new ModelBuilder.Dynamic()
				.addQuad(-50, -50, -50, 50, 50, -50, 0, 0, 1, 1)
				.buildTextured(new ResourceLocation("icon.png")));

		engine.modelRenderer.setCamera(new Camera()
		{
			@Override
			public void updatePosition()
			{

			}
		});
		engine.modelRenderer.getCamera().move(0, 0, -50);
	};

	@Handler
	public final FunctionalHandler<TickEvent> tick = event ->
	{

	};

	@Handler
	public final FunctionalHandler<RenderEvent> render = event ->
	{
		GameEngine engine = event.getEngine();

		engine.modelRenderer.preRender();
		engine.modelRenderer.getShader().start();
		engine.modelRenderer.getShader().view(engine.modelRenderer.getCamera());
		engine.modelRenderer.renderEntity(entity, engine.modelRenderer.getShader());
		engine.modelRenderer.getShader().stop();
	};

	@Handler
	public final FunctionalHandler<StopEvent> stop = event ->
	{
		event.getEngine().modelRenderer.getShader().remove();
		ModelLoader.instance.cleanUp();
		AudioManager.instance.cleanUp();
	};
}
