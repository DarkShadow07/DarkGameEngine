package main.Engine.engine.model.resource;

public class ResourceLocation
{
	private String path, file;

	public ResourceLocation(String filePath)
	{
		if (filePath.contains("/"))
		{
			path = filePath.substring(0, filePath.lastIndexOf("/"));
			file = filePath.substring(filePath.lastIndexOf("/"));
		} else
		{
			path = "";
			file = filePath;
		}
	}

	public ResourceLocation(String path, String file)
	{
		this.path = path;
		this.file = file;
	}

	public String getFile()
	{
		return file;
	}

	public String getPath()
	{
		return path;
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
		return String.format("%s%s", path, file);
	}
}
