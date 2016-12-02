package main.Engine.engine.audio;

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

	public void play(int sound)
	{
		AL10.alSourcei(id, AL10.AL_BUFFER, sound);
		AL10.alSourcePlay(id);
	}

	public boolean isPlaying()
	{
		return AL10.alGetSourcei(id, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}

	public void resume()
	{
		AL10.alSourcePlay(id);
	}

	public void pause()
	{
		AL10.alSourcePause(id);
	}

	public void setLoop(boolean loop)
	{
		AL10.alSourcei(id, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
	}

	public void setVelocity(float x, float y, float z)
	{
		AL10.alSource3f(id, AL10.AL_VELOCITY, x, y, z);
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

	public void stop()
	{
		AL10.alDeleteSources(id);
	}
}
