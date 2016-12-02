package main.Engine.engine.model.base;

import main.Engine.engine.model.texture.ModelTexture;

public class TexturedModel
{
	protected RawModel rawModel;
	protected ModelTexture texture;

	public TexturedModel(RawModel rawModel, ModelTexture texture)
	{
		this.rawModel = rawModel;
		this.texture = texture;
	}

	public RawModel getRawModel()
	{
		return rawModel;
	}

	public ModelTexture getTexture()
	{
		return texture;
	}
}
