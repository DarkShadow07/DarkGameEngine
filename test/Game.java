import main.Engine.GameEngine;
import main.Engine.GameEngineManager;
import main.Engine.audio.AudioManager;
import main.Engine.camera.Camera;
import main.Engine.entity.Entity;
import main.Engine.model.ModelLoader;
import main.Engine.model.ModelRenderer;
import main.Engine.model.builder.ModelBuilder;
import main.Engine.resource.ResourceLocation;
import main.Engine.resource.ResourceManager;
import main.Engine.resource.Resources;
import main.Engine.shader.StaticShader;
import main.Engine.util.math.Position;

import java.io.File;

public class Game
{
	public Game()
	{
		GameEngine engine = GameEngineManager.instance.createGameEngine(60, 20, 1280, 720, false);

		final StaticShader[] shader = new StaticShader[1];
		Camera camera = new Camera()
		{
			@Override
			public void updatePosition()
			{

			}
		};
		camera.move(0, 0, -50);

		final Entity[] entity = new Entity[1];

		engine.addStartHook(() ->
		{
			ModelLoader.instance.setEngine(engine);
			AudioManager.instance.setEngine(engine);
			shader[0] = new StaticShader();
			engine.modelRenderer = new ModelRenderer(shader[0]);
			engine.resourceManager = new ResourceManager(new Resources(new File("C:\\Users\\Yonatan\\Desktop\\Proyectos\\Java\\DarkGameEngine"), "res/"));
			entity[0] = new Entity(new Position(0, 0, -90), 1.0f, new ModelBuilder.Dynamic()
					.addQuad(-50, -50, -50, 50, 50, -50, 0, 0, 1, 1)
					.buildTextured(new ResourceLocation("icon.png")));
		});

		engine.addTickHook(() ->
		{

		});

		engine.addRenderHook(() ->
		{
			if (engine.modelRenderer == null) return;

			engine.modelRenderer.preRender();
			shader[0].view(camera);
			shader[0].start();
			//engine.modelRenderer.renderEntity(entity[0], shader[0]);
			shader[0].stop();
		});

		engine.addStopHook(() ->
		{
			shader[0].remove();
			ModelLoader.instance.cleanUp();
			AudioManager.instance.cleanUp();
		});

		engine.start();
	}
}
