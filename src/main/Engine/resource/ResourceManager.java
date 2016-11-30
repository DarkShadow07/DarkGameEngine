package main.Engine.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ResourceManager
{
	private Resources resources;

	public ResourceManager(Resources resources)
	{
		this.resources = resources;
	}

	public InputStream getResourceStreamAssets(ResourceLocation location) throws FileNotFoundException
	{
		if (!resources.fileExists(location)) return null;

		File file = resources.getFile(location);

		return file.isFile() ? new FileInputStream(file) : null;
	}

	private InputStream getResourceStream(ResourceLocation location)
	{
		return Class.class.getResourceAsStream(String.format("/%s/%s", location.getPath(), location.getFile()));
	}

	public Resources getResources()
	{
		return resources;
	}
}
