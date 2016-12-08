package main.Engine.util.math;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Position extends Vector3f
{
	public Position(float xPos, float yPos)
	{
		super(xPos, yPos, 0);
	}

	public Position(float xPos, float yPos, float zPos)
	{
		super(xPos, yPos, zPos);
	}

	public Position move(float dx, float dy, float dz)
	{
		x += dx;
		y += dy;
		z += dz;

		return this;
	}

	public Vector2f toVector2()
	{
		return new Vector2f(x, y);
	}

	public Vector3f toVector()
	{
		return new Vector3f(x, y, z);
	}

	@Override
	public String toString()
	{
		return String.format("Position[%s, %s, %s]", x, y, z);
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Position && ((Position) obj).x == x && ((Position) obj).y == y && ((Position) obj).z == z;
	}
}
