package main.Engine.engine.model.resource;

import main.Engine.engine.GameEngineManager;

public class ResourceLocation
{
	private String file;

	public ResourceLocation(String filePath)
	{
		file = filePath;
	}

	public String getFile()
	{
		return file;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ResourceLocation)
		{
			ResourceLocation location = (ResourceLocation) obj;

			if (location.toString().equalsIgnoreCase(toString()))
				return true;
		}

		return false;
	}

	@Override
	public String toString()
	{
		return GameEngineManager.instance.getGameEngine().gameDir + file;
	}
}
