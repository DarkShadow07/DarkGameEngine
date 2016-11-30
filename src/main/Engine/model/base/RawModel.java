package main.Engine.model.base;

import org.lwjgl.opengl.GL20;

public class RawModel
{
	protected int vao, vertexCount;

	public RawModel(int vao, int vertexCount)
	{
		this.vao = vao;
		this.vertexCount = vertexCount;
	}

	public void enableAttributes()
	{
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}

	public void disableAttributes()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
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
