package main.Engine.util.math;

public class BoundingBox2D
{
	private float xPos, yPos, width, height;

	public BoundingBox2D(float xPos, float yPos, float width, float height)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
	}

	public BoundingBox2D expand(float expansion)
	{
		return expandX(expansion).expandY(expansion);
	}

	public BoundingBox2D expandX(float expansion)
	{
		return new BoundingBox2D(xPos - expansion / 2, yPos, width + expansion / 2, height);
	}

	public BoundingBox2D expandY(float expansion)
	{
		return new BoundingBox2D(xPos, yPos - expansion / 2, width, height + expansion / 2);
	}

	public boolean intersects(BoundingBox2D box)
	{
		return contains(box.xPos, box.yPos) || contains(box.width, box.height);
	}

	public boolean contains(float x, float y)
	{
		return x >= xPos && x <= xPos + width && y >= yPos && y <= yPos + height;
	}

	public float getX()
	{
		return xPos;
	}

	public float getY()
	{
		return yPos;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}
}
