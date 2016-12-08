package main.Engine.settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.Engine.engine.GameEngine;
import main.Engine.engine.model.resource.ResourceLocation;
import main.Engine.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class GameSettings
{
	private ResourceLocation settingsFile;

	private SettingsFile settings = new SettingsFile();

	public GameSettings(GameEngine engine)
	{
		this.settingsFile = new ResourceLocation(engine.gameDir);

		try
		{
			loadSettings();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getGameDir()
	{
		return settingsFile.getFile();
	}

	public void setValue(String property, Object value)
	{
		settings.setValue(property, value);
	}

	public <T> T getValue(String property)
	{
		return settings.getValue(property);
	}

	private void loadSettings() throws Exception
	{
		File dir = new File(settingsFile.getFile()), file = new File(dir, "settings\\GameSettings.game");

		if (!file.exists() || !dir.exists())
		{
			dir.mkdirs();
			file.createNewFile();
			loadDefaultSettings();

			Log.info(getClass(), "Created Settings File");
		} else
		{
			settings = new Gson().fromJson(new FileReader(file), settings.getClass());

			Log.info(getClass(), "Successfully Loaded Settings from File");
		}
	}

	private void loadDefaultSettings()
	{
		settings.setValue("gameDir", settingsFile.toString());
	}

	public void saveSettings() throws Exception
	{
		File dir = new File(settingsFile.getFile()), file = new File(dir, "settings\\GameSettings.game");

		if (!file.exists() || !dir.exists())
		{
			dir.mkdirs();
			file.createNewFile();
			loadDefaultSettings();

			Log.info(getClass(), "Re-Created Settings File");
		}

		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		Gson gson = builder.create();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		writer.write(gson.toJson(settings));

		writer.close();

		Log.info(getClass(), "Saved Settings to File");
	}
}
