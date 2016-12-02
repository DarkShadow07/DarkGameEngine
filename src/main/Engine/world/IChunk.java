package main.Engine.world;

import main.Engine.entity.IEntity;
import main.Engine.world.tile.ITickable;
import main.Engine.world.tile.ITile;
import main.Engine.util.math.Position;

import java.util.List;
import java.util.Map;

public interface IChunk
{
	void update();

	boolean removeTickingEntity(ITickable tickingEntity);

	void addTickingEntity(ITickable tickingEntity);

	Map<Position, ITickable> getTickingEntities();

	void addEntity(IEntity entity);

	List<IEntity> getEntities(Position pos);

	boolean removeTickingTile(ITickable tickingTile);

	void addTickingTile(ITickable tickingTile, int xPos, int yPos);

	Map<Position, ITickable> getTickingTiles();

	void setTile(ITile tile, int xPos, int yPos);

	ITile getTile(int xPos, int yPos);

	void clear();

	void clearEntities();

	void clearTiles();

	int getXSize();

	int getYSize();
}
