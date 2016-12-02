package main.Engine.engine.model.base;

import main.Engine.engine.model.texture.ModelTexture;

public class DynamicTexturedModel extends TexturedModel
{
	public DynamicTexturedModel(DynamicRawModel rawModel, ModelTexture texture)
	{
		super(rawModel, texture);
	}

	@Override
	public DynamicRawModel getRawModel()
	{
		return (DynamicRawModel) rawModel;
	}
}
