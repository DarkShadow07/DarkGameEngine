package main.Engine.entity;

import main.Engine.engine.model.base.TexturedModel;
import main.Engine.util.math.Position;

public interface IEntity
{
	void rotate(float rotX, float rotY, float rotZ);

	void move(float dx, float dy, float dz);

	TexturedModel getModel();

	Position getPosition();

	float getRotX();

	float getRotY();

	float getRotZ();

	float getScale();
}
