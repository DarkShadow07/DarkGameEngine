package main.Engine.resource;

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
	public String toString()
	{
		return String.format("%s%s", path, file);
	}
}
