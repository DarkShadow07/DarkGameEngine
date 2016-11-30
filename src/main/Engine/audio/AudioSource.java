package main.Engine.audio;

import main.Engine.util.math.Position;
import org.lwjgl.openal.AL10;

public class AudioSource
{
	private int id;

	public AudioSource(float gain, float pitch, Position position)
	{
		id = AL10.alGenSources();
		AL10.alSourcef(id, AL10.AL_GAIN, gain);
		AL10.alSourcef(id, AL10.AL_PITCH, pitch);
		AL10.alSource3f(id, AL10.AL_POSITION, position.getX(), position.getY(), position.getZ());
	}

	public void setGain(float gain)
	{
		AL10.alSourcef(id, AL10.AL_GAIN, gain);
	}

	public void setPitch(float pitch)
	{
		AL10.alSourcef(id, AL10.AL_PITCH, pitch);
	}

	public void setPosition(Position position)
	{
		AL10.alSource3f(id, AL10.AL_POSITION, position.getX(), position.getY(), position.getZ());
	}

	public void play(int sound)
	{
		AL10.alSourcei(id, AL10.AL_BUFFER, sound);
		AL10.alSourcePlay(id);
	}

	public void stop()
	{
		AL10.alDeleteSources(id);
	}
}
