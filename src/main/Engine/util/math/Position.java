package main.Engine.util.math;

import org.lwjgl.util.vector.Vector3f;

public class Position extends Vector3f
{
	private float xPos, yPos, zPos;

	public Position(float xPos, float yPos, float zPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
	}

	public Position move(float dx, float dy, float dz)
	{
		xPos += dx;
		yPos += dy;
		zPos += dz;

		return this;
	}

	@Override
	public String toString()
	{
		return String.format("Position[%s, %s, %s]", xPos, yPos, zPos);
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Position && ((Position) obj).xPos == xPos && ((Position) obj).yPos == yPos && ((Position) obj).zPos == zPos;
	}
}
