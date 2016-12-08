package main.Engine.engine.model.resource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Resources
{
	private final Map<String, String> resources = new HashMap<>();

	public Resources(File assets, String path)
	{
		addAllFiles(new File(assets, path));
	}

	public boolean fileExists(ResourceLocation location)
	{
		return resources.containsKey(location.getFile());
	}

	public String getFile(ResourceLocation location)
	{
		return resources.get(location.getFile());
	}

	private void addAllFiles(File file)
	{
		if (file.isFile() || file.listFiles() == null || file.listFiles().length < 1) return;

		for (File f : file.listFiles())
			if (f.isDirectory()) addAllFiles(f);
			else
				resources.put(f.getName(), f.getPath());
	}
}
