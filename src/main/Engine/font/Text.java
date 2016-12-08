package main.Engine.font;

import main.Engine.util.math.Position;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

public class Text
{
	private String text;
	private float size;
	private Position position;
	private int vao, vertexCount;
	private Vector3f color;

	public Text(String text, float size, Position position, Color color)
	{
		this.text = text;
		this.size = size;
		this.position = position;
		this.color = new Vector3f(color.getRed(), color.getGreen(), color.getBlue());
	}

	public void setVAOData(int vao, int vertexCount)
	{
		this.vao = vao;
		this.vertexCount = vertexCount;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public float getSize()
	{
		return size;
	}

	public void setSize(float size)
	{
		this.size = size;
	}

	public Position getPosition()
	{
		return position;
	}

	public void setPosition(Position position)
	{
		this.position = position;
	}

	public int getVao()
	{
		return vao;
	}

	public int getVertexCount()
	{
		return vertexCount;
	}

	public Vector3f getColor()
	{
		return color;
	}

	public void setColor(Vector3f color)
	{
		this.color = color;
	}
}
