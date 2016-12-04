package main.Engine.engine.model;

import main.Engine.engine.GameEngine;
import main.Engine.engine.model.base.DynamicRawModel;
import main.Engine.engine.model.base.DynamicVBO;
import main.Engine.engine.model.base.RawModel;
import main.Engine.engine.model.resource.ResourceLocation;
import main.Engine.engine.model.texture.ModelTexture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelLoader
{
	public static final ModelLoader instance = new ModelLoader();

	private GameEngine engine;

	private List<Integer> vaoList = new ArrayList<>(), vboList = new ArrayList<>(), textureList = new ArrayList<>();

	public void updateDynamicVBO(int vao, DynamicVBO vbo, float[] data)
	{
		GL30.glBindVertexArray(vao);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo.getVBO());

		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils.createFloatBuffer(data.length).put(data).flip(), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(vbo.getAttribute(), vbo.getSize(), GL11.GL_FLOAT, false, 0, 0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}

	public DynamicRawModel loadDynamicVAO(int[] indices, DynamicVBO... dynamicData)
	{
		int vao = GL30.glGenVertexArrays();
		vaoList.add(vao);
		GL30.glBindVertexArray(vao);

		storeIndices(indices);
		Arrays.stream(dynamicData).forEach(vbo -> vbo.setVBO(storeData(vbo.getAttribute(), vbo.getSize(), vbo.getData())));

		GL30.glBindVertexArray(0);

		return new DynamicRawModel(vao, dynamicData, indices.length);
	}

	public int loadTextVAO(float[] positions, float[] uvs)
	{
		int vao = GL30.glGenVertexArrays();
		vaoList.add(vao);
		GL30.glBindVertexArray(vao);

		storeData(0, 2, positions);
		storeData(1, 2, uvs);

		return vao;
	}

	public RawModel loadVAO(float[] positions, float[] uvs, int[] indices)
	{
		int vao = GL30.glGenVertexArrays();
		vaoList.add(vao);
		GL30.glBindVertexArray(vao);

		storeIndices(indices);
		storeData(0, 3, positions);
		storeData(1, 2, uvs);

		GL30.glBindVertexArray(0);

		return new RawModel(vao, indices.length);
	}

	public ModelTexture loadTexture(ResourceLocation location)
	{
		Texture texture = null;

		try
		{
			texture = TextureLoader.getTexture("png", engine.resourceManager.getResourceStreamAssets(location));
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		int id = texture.getTextureID();

		textureList.add(id);

		return new ModelTexture(id);
	}

	private int storeData(int attribute, int dataSize, float[] data)
	{
		int vbo = GL15.glGenBuffers();
		vboList.add(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils.createFloatBuffer(data.length).put(data).flip(), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attribute, dataSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		return vbo;
	}

	private void storeIndices(int[] indices)
	{
		int vbo = GL15.glGenBuffers();
		vboList.add(vbo);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) BufferUtils.createIntBuffer(indices.length).put(indices).flip(), GL15.GL_STATIC_DRAW);
	}

	public void cleanUp()
	{
		vaoList.forEach(GL30::glDeleteVertexArrays);
		vboList.forEach(GL15::glDeleteBuffers);
		textureList.forEach(GL11::glDeleteTextures);
	}

	public void setEngine(GameEngine engine)
	{
		this.engine = engine;
	}
}
