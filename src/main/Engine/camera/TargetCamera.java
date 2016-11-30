package main.Engine.camera;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import main.Engine.entity.IEntity;
import main.Engine.util.math.BoundingBox2D;

public class TargetCamera extends Camera
{
	private IEntity target;
	@Nullable
	private BoundingBox2D bounds;
	private boolean smooth;

	public TargetCamera(@NotNull IEntity target, boolean smooth)
	{
		this(target, null, smooth);
	}

	public TargetCamera(IEntity target, @Nullable BoundingBox2D bounds, boolean smooth)
	{
		this.target = target;
		this.bounds = bounds;
		this.smooth = smooth;
	}

	@Override
	public void updatePosition()
	{
		position.move(target.getPosition().getX() - position.getX() * (smooth ? 0.2f : 1.0f), target.getPosition().getY() - position.getY() * (smooth ? 0.2f : 1.0f), 0.0f);

		if (bounds == null) return;

		if (position.getX() < bounds.getX())
			position.setX(bounds.getX());
		if (position.getY() < bounds.getY())
			position.setY(bounds.getY());
		if (position.getX() > bounds.getX() + bounds.getWidth())
			position.setX(bounds.getX() + bounds.getWidth());
		if (position.getY() > bounds.getY() + bounds.getHeight())
			position.setY(bounds.getY() + bounds.getHeight());
	}

	public IEntity getTarget()
	{
		return target;
	}

	public void setTarget(@NotNull IEntity target)
	{
		this.target = target;
	}

	@Nullable
	public BoundingBox2D getBounds()
	{
		return bounds;
	}

	public void setBounds(@Nullable BoundingBox2D bounds)
	{
		this.bounds = bounds;
	}

	public boolean isSmoothMovement()
	{
		return smooth;
	}

	public void setSmoothMovement(boolean smooth)
	{
		this.smooth = smooth;
	}
}
