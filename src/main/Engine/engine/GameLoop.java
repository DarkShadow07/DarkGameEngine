package main.Engine.engine;

public class GameLoop
{
	long past = System.currentTimeMillis();
	private int targetFrames, targetUpdates, totalFrames, totalUpdates, frames, updates;
	private long nanoLast = System.nanoTime();
	private double deltaUpdate = 0, deltaFrame = 0;

	public GameLoop(int targetFrames, int targetUpdates)
	{
		this.targetFrames = targetFrames;
		this.targetUpdates = targetUpdates;
	}

	public void run()
	{
		long nanoNow = System.nanoTime();

		deltaUpdate += (nanoNow - nanoLast) / (1000_000_000D / targetUpdates);
		deltaFrame += (nanoNow - nanoLast) / (1000_000_000D / targetFrames);

		nanoLast = nanoNow;

		if (System.currentTimeMillis() - past > 1000)
		{
			past += 1000;

			frames = totalFrames;
			updates = totalUpdates;

			totalFrames = 0;
			totalUpdates = 0;
		}

		try
		{
			Thread.sleep(1);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public boolean shouldRender()
	{
		boolean render = deltaFrame >= 1;

		if (render)
		{
			deltaFrame--;
			totalFrames++;
		}

		return render;
	}

	public boolean shouldUpdate()
	{
		boolean update = deltaUpdate >= 1;

		if (update)
		{
			deltaUpdate--;
			totalUpdates++;
		}

		return update;
	}

	public int getFrames()
	{
		return frames;
	}

	public int getUpdates()
	{
		return updates;
	}
}