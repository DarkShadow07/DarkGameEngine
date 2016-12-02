import main.Engine.engine.GameEngine;
import main.Engine.engine.GameEngineManager;
import main.Engine.engine.audio.AudioManager;
import main.Engine.engine.camera.Camera;
import main.Engine.engine.model.resource.ResourceLocation;
import main.Engine.engine.model.shader.StaticShader;
import main.Engine.entity.Entity;
import main.Engine.event.base.Handler;
import main.Engine.event.game.InitializationEvent;
import main.Engine.event.game.RenderEvent;
import main.Engine.event.game.StopEvent;
import main.Engine.event.game.TickEvent;
import main.Engine.side.Side;
import main.Engine.side.SideOnly;
import main.Engine.util.math.Position;

@SideOnly(Side.CLIENT)
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

//		engine.addStartHook(() ->
//		{
//			ModelLoader.instance.setEngine(engine);
//			AudioManager.instance.setEngine(engine);
//			shader[0] = new StaticShader();
//			engine.modelRenderer = new ModelRenderer(shader[0]);
//			engine.resourceManager = new ResourceManager(new Resources(new File("C:\\Users\\Yonatan\\Desktop\\Proyectos\\Java\\DarkGameEngine"), "res/"));
//			entity[0] = new Entity(new Position(0, 0, -50), 1.8f, new ModelBuilder.Dynamic()
//					.addQuad(-50, -50, -50, 50, 50, -50, 0, 0, 1, 1)
//					.buildTextured(new ResourceLocation("icon.png")));
//		});

//		engine.addTickHook(() ->
//		{
//
//		});

//		engine.addRenderHook(() ->
//		{
//			engine.modelRenderer.preRender();
//			shader[0].view(camera);
//			shader[0].start();
//			engine.modelRenderer.renderEntity(entity[0], shader[0]);
//			shader[0].stop();
//		});

//		engine.addStopHook(() ->
//		{
//			shader[0].remove();
//			ModelLoader.instance.cleanUp();
//			AudioManager.instance.cleanUp();
//		});

		engine.start();
	}

	@Handler(InitializationEvent.class)
	static final Handler.FunctionalHandler init = event ->
	{

	};

	@Handler(TickEvent.class)
	static final Handler.FunctionalHandler tick = event ->
	{

	};

	@Handler(RenderEvent.class)
	static final Handler.FunctionalHandler render = event ->
	{

	};

	@Handler(StopEvent.class)
	static final Handler.FunctionalHandler stop = event ->
	{

	};
}
