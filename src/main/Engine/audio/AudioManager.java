package main.Engine.audio;

import main.Engine.GameEngine;
import main.Engine.resource.ResourceLocation;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.WaveData;

import java.util.ArrayList;
import java.util.List;

public class AudioManager
{
	public static final AudioManager instance = new AudioManager();

	private GameEngine engine;

	private List<Integer> sounds = new ArrayList<>();

	public AudioManager()
	{
		try
		{
			AL.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}

	public int loadSound(ResourceLocation location)
	{
		int buffer = AL10.alGenBuffers();
		sounds.add(buffer);

		WaveData data = WaveData.create(location.toString());
		AL10.alBufferData(buffer, data.format, data.data, data.samplerate);
		data.dispose();

		return buffer;
	}

	public void cleanUp()
	{
		sounds.forEach(AL10::alDeleteBuffers);
		AL.destroy();
	}

	public void setEngine(GameEngine engine)
	{
		this.engine = engine;
	}
}
