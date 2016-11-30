package main.Engine.model.base;

public class DynamicVBO
{
	private float[] data;
	private int size, index, vbo;

	public DynamicVBO(int size, int attribute, float[] data)
	{
		this.size = size;
		this.index = attribute;
		this.data = data;
	}

	public float[] getData()
	{
		return data;
	}

	public int getSize()
	{
		return size;
	}

	public int getAttribute()
	{
		return index;
	}

	public int getVBO()
	{
		return vbo;
	}

	public void setVBO(int vbo)
	{
		this.vbo = vbo;
	}
}
