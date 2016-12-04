package main.Engine.font;

import main.Engine.engine.model.shader.BaseShader;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class FontShader extends BaseShader
{
	private int color, translation;

	public FontShader()
	{
		super("res/shader/fontShader.vertex", "res/shader/fontShader.fragment");
	}

	@Override
	protected void getUniformLocations()
	{
		color = getUniformLocation("color");
		translation = getUniformLocation("translation");
	}

	@Override
	protected void bindAttributes()
	{
		bindAttribute(0, "position");
		bindAttribute(1, "uvs");
	}

	public void color(Vector3f color)
	{
		loadVector(this.color, color);
	}

	public void translation(Vector2f translation)
	{
		loadVector2(this.translation, translation);
	}
}
