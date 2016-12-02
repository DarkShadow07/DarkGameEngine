package main.Engine.entity;

import main.Engine.engine.model.base.TexturedModel;
import main.Engine.util.math.Position;

public class Entity implements IEntity
{
	private Position position;
	private float rotX, rotY, rotZ, scale;
	private TexturedModel model;

	public Entity(Position position, float scale, TexturedModel model)
	{
		this.position = position;
		this.scale = scale;
		this.model = model;
	}

	@Override
	public void rotate(float rotX, float rotY, float rotZ)
	{
		this.rotX += rotX;
		this.rotY += rotY;
		this.rotZ += rotZ;
	}

	@Override
	public void move(float dx, float dy, float dz)
	{
		position.move(dx, dy, dz);
	}

	@Override
	public TexturedModel getModel()
	{
		return model;
	}

	@Override
	public Position getPosition()
	{
		return position;
	}

	@Override
	public float getRotX()
	{
		return rotX;
	}

	@Override
	public float getRotY()
	{
		return rotY;
	}

	@Override
	public float getRotZ()
	{
		return rotZ;
	}

	@Override
	public float getScale()
	{
		return scale;
	}
}
