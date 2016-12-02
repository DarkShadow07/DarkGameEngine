package main.Engine.engine.audio;

import main.Engine.engine.GameEngine;
import main.Engine.engine.model.resource.ResourceLocation;
import main.Engine.util.math.Position;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import java.util.HashMap;
import java.util.Map;

public class AudioManager
{
	public static final AudioManager instance = new AudioManager();

	private GameEngine engine;

	private Map<String, Integer> sounds = new HashMap<>();

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

	public void playLoop(ResourceLocation sound, Position pos, float pitch, float gain)
	{
		AudioSource source = new AudioSource(gain, pitch, pos);
		source.setLoop(true);
		source.play(sounds.get(sound.toString()));
	}

	public void playSound(ResourceLocation sound, Position pos, float pitch, float gain)
	{
		AudioSource source = new AudioSource(gain, pitch, pos);
		source.play(sounds.get(sound.toString()));
	}

	public void loadSound(ResourceLocation location)
	{
		int buffer = AL10.alGenBuffers();
		sounds.put(location.toString(), buffer);

		WaveData data = WaveData.create(Class.class.getResource(location.toString()));

		AL10.alBufferData(buffer, data.format, data.data, data.samplerate);

		data.dispose();
	}

	public void cleanUp()
	{
		sounds.values().forEach(AL10::alDeleteBuffers);
		AL.destroy();
	}

	public void setEngine(GameEngine engine)
	{
		this.engine = engine;
	}
}
