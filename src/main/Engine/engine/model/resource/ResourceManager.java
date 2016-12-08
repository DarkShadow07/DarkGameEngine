package main.Engine.engine.model.resource;

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

		File file = new File(resources.getFile(location));

		return file.isFile() ? new FileInputStream(file) : null;
	}

	private InputStream getResourceStream(ResourceLocation location)
	{
		return Class.class.getResourceAsStream(location.toString());
	}

	public Resources getResources()
	{
		return resources;
	}
}
