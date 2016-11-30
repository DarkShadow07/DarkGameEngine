package main.Engine.model.base;

public class RawModel
{
	protected int vao, vertexCount;

	public RawModel(int vao, int vertexCount)
	{
		this.vao = vao;
		this.vertexCount = vertexCount;
	}

	public int getVAO()
	{
		return vao;
	}

	public int getVertexCount()
	{
		return vertexCount;
	}
}
