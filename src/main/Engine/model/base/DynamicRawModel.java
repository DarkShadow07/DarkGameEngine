package main.Engine.model.base;

import main.Engine.model.ModelLoader;
import org.lwjgl.opengl.GL20;

public class DynamicRawModel extends RawModel
{
	private DynamicVBO[] vbos;

	public DynamicRawModel(int vao, DynamicVBO[] vbos, int vertexCount)
	{
		super(vao, vertexCount);

		this.vbos = vbos;
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

	public float[] getDynamicData(int index)
	{
		return vbos[index].getData();
	}

	public void updateDynamicData(int index, float[] data)
	{
		ModelLoader.instance.updateDynamicVBO(vao, vbos[index], data);
	}

	public DynamicVBO[] getVBOs()
	{
		return vbos;
	}
}
