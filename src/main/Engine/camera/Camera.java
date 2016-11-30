package main.Engine.camera;

import main.Engine.util.math.Position;

public abstract class Camera
{
	protected Position position = new Position(0, 0, 0);
	protected float pitch, yaw, roll, scale = 1;

	public Camera()
	{

	}

	public abstract void updatePosition();

	public void scale(float scale)
	{
		this.scale += scale;
	}

	public void rotate(float pitch, float yaw, float roll)
	{
		this.pitch += pitch;
		this.yaw += yaw;
		this.roll += roll;
	}

	public void move(float dx, float dy, float dz)
	{
		position.move(dx, dy, dz);
	}

	public Position getPosition()
	{
		return position;
	}

	public float getPitch()
	{
		return pitch;
	}

	public float getYaw()
	{
		return yaw;
	}

	public float getRoll()
	{
		return roll;
	}

	public float getScale()
	{
		return scale;
	}
}
