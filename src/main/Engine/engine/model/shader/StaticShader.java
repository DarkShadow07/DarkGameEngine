package main.Engine.engine.model.shader;

import main.Engine.engine.camera.Camera;
import main.Engine.util.math.Maths;
import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends BaseShader
{
	private int transformation, projection, view;

	public StaticShader()
	{
		super("res/shader/staticShader.vertex", "res/shader/staticShader.fragment");
	}

	public void transform(Matrix4f matrix4f)
	{
		loadMatrix(transformation, matrix4f);
	}

	public void project(Matrix4f matrix4f)
	{
		loadMatrix(projection, matrix4f);
	}

	public void view(Camera camera)
	{
		loadMatrix(view, Maths.viewMatrix(camera));
	}

	@Override
	protected void getUniformLocations()
	{
		transformation = getUniformLocation("transformation");
		projection = getUniformLocation("projection");
		view = getUniformLocation("view");
	}

	@Override
	protected void bindAttributes()
	{
		bindAttribute(0, "position");
		bindAttribute(1, "uvs");
	}
}
