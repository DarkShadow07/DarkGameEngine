package main.Engine.world;

import main.Engine.util.math.Position;

import java.util.Map;

public interface IChunkProvider
{
	default void tickChunk(IChunk chunk)
	{
		chunk.update();
	}

	void setChunk(IChunk IChunk, int xPos, int yPos);

	IChunk getChunk(int xPos, int yPos);

	default void clearChunks()
	{
		getChunks().values().forEach(IChunk::clear);
	}

	Map<Position, IChunk> getChunks();
}