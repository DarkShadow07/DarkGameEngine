package main.Engine.engine.model.builder;

import main.Engine.engine.model.ModelLoader;
import main.Engine.engine.model.base.*;
import main.Engine.engine.model.resource.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ModelBuilder
{
	private List<Float> quads = new ArrayList<>(), uvs = new ArrayList<>();
	private List<Integer> indices = new ArrayList<>();

	private int quadCount = 0;

	public ModelBuilder()
	{

	}

	public ModelBuilder addQuad(float x1, float y1, float z1, float x2, float y2, float z2, float u1, float v1, float u2, float v2)
	{
		quads.add(x1);
		quads.add(y2);
		quads.add(z1);
		quads.add(x1);
		quads.add(y1);
		quads.add(z1);
		quads.add(x2);
		quads.add(y1);
		quads.add(z2);
		quads.add(x2);
		quads.add(y2);
		quads.add(z2);

		uvs.add(u1);
		uvs.add(v1);
		uvs.add(u1);
		uvs.add(v2);
		uvs.add(u2);
		uvs.add(v2);
		uvs.add(u2);
		uvs.add(v1);

		indices.add(4 * quadCount);
		indices.add(1 + 4 * quadCount);
		indices.add(3 + 4 * quadCount);
		indices.add(3 + 4 * quadCount);
		indices.add(1 + 4 * quadCount);
		indices.add(2 + 4 * quadCount);

		quadCount++;

		return this;
	}

	public List<Float> getQuads()
	{
		return quads;
	}

	public int getQuadCount()
	{
		return quadCount;
	}

	public List<Float> getUVs()
	{
		return uvs;
	}

	public List<Integer> getIndices()
	{
		return indices;
	}

	public RawModel buildRaw()
	{
		float[] quads = new float[this.quads.size()], uvs = new float[this.uvs.size()];
		int[] indices = new int[this.indices.size()];

		for (int i = 0; i < this.quads.size(); i++)
			quads[i] = this.quads.get(i);
		for (int i = 0; i < this.uvs.size(); i++)
			uvs[i] = this.uvs.get(i);
		for (int i = 0; i < this.indices.size(); i++)
			indices[i] = this.indices.get(i);

		return ModelLoader.instance.loadVAO(quads, uvs, indices);
	}

	public TexturedModel buildTextured(ResourceLocation location)
	{
		return new TexturedModel(buildRaw(), ModelLoader.instance.loadTexture(location));
	}

	public static class Dynamic
	{
		private List<Float> quads = new ArrayList<>(), uvs = new ArrayList<>();
		private List<Integer> indices = new ArrayList<>();

		private int quadCount = 0;

		public Dynamic()
		{

		}

		public Dynamic addQuad(float x1, float y1, float z1, float x2, float y2, float z2, float u1, float v1, float u2, float v2)
		{
			quads.add(x1);
			quads.add(y2);
			quads.add(z1);
			quads.add(x1);
			quads.add(y1);
			quads.add(z1);
			quads.add(x2);
			quads.add(y1);
			quads.add(z2);
			quads.add(x2);
			quads.add(y2);
			quads.add(z2);

			uvs.add(u1);
			uvs.add(v1);
			uvs.add(u1);
			uvs.add(v2);
			uvs.add(u2);
			uvs.add(v2);
			uvs.add(u2);
			uvs.add(v1);

			indices.add(4 * quadCount);
			indices.add(1 + 4 * quadCount);
			indices.add(3 + 4 * quadCount);
			indices.add(3 + 4 * quadCount);
			indices.add(1 + 4 * quadCount);
			indices.add(2 + 4 * quadCount);

			quadCount++;

			return this;
		}

		public List<Float> getQuads()
		{
			return quads;
		}

		public int getQuadCount()
		{
			return quadCount;
		}

		public List<Float> getUVs()
		{
			return uvs;
		}

		public List<Integer> getIndices()
		{
			return indices;
		}

		public DynamicRawModel buildRaw(DynamicVBO... customVBOs)
		{
			float[] quads = new float[this.quads.size()], uvs = new float[this.uvs.size()];
			int[] indices = new int[this.indices.size()];

			for (int i = 0; i < this.quads.size(); i++)
				quads[i] = this.quads.get(i);
			for (int i = 0; i < this.uvs.size(); i++)
				uvs[i] = this.uvs.get(i);
			for (int i = 0; i < this.indices.size(); i++)
				indices[i] = this.indices.get(i);

			DynamicVBO[] newArray = new DynamicVBO[customVBOs.length + 2];

			System.arraycopy(customVBOs, 0, newArray, 2, customVBOs.length);

			newArray[0] = new DynamicVBO(3, 0, quads);
			newArray[1] = new DynamicVBO(2, 1, uvs);

			return ModelLoader.instance.loadDynamicVAO(indices, newArray);
		}

		public DynamicTexturedModel buildTextured(ResourceLocation location, DynamicVBO... customVBOs)
		{
			return new DynamicTexturedModel(buildRaw(customVBOs), ModelLoader.instance.loadTexture(location));
		}
	}

	public static class TwoDimensional
	{
		private List<Float> quads = new ArrayList<>(), uvs = new ArrayList<>();

		private int quadCount = 0;

		public TwoDimensional()
		{

		}

		public TwoDimensional addQuad(float x1, float y1, float x2, float y2, float u1, float v1, float u2, float v2)
		{
			quads.add(x1);
			quads.add(y1);
			quads.add(x1);
			quads.add(y2);
			quads.add(x2);
			quads.add(y2);
			quads.add(x2);
			quads.add(y2);
			quads.add(x2);
			quads.add(y1);
			quads.add(x1);
			quads.add(y1);

			uvs.add(u1);
			uvs.add(v1);
			uvs.add(u1);
			uvs.add(v2);
			uvs.add(u2);
			uvs.add(v2);
			uvs.add(u2);
			uvs.add(v2);
			uvs.add(u2);
			uvs.add(v1);
			uvs.add(u1);
			uvs.add(v1);

			quadCount++;

			return this;
		}

		public List<Float> getQuads()
		{
			return quads;
		}

		public float[] getQuadsArray()
		{
			float[] quads = new float[this.quads.size()];

			for (int i = 0; i < this.quads.size(); i++)
				quads[i] = this.quads.get(i);

			return quads;
		}

		public int getQuadCount()
		{
			return quadCount;
		}

		public List<Float> getUVs()
		{
			return uvs;
		}

		public float[] getUVsArray()
		{
			float[] uvs = new float[this.uvs.size()];

			for (int i = 0; i < this.uvs.size(); i++)
				uvs[i] = this.uvs.get(i);

			return uvs;
		}

		public int buildRaw()
		{
			float[] quads = new float[this.quads.size()], uvs = new float[this.uvs.size()];

			for (int i = 0; i < this.quads.size(); i++)
				quads[i] = this.quads.get(i);
			for (int i = 0; i < this.uvs.size(); i++)
				uvs[i] = this.uvs.get(i);

			return ModelLoader.instance.loadTextVAO(quads, uvs);
		}
	}
}
