package main.Engine.util.math;

import main.Engine.engine.camera.Camera;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Maths
{
	private static Vector3f vec1 = new Vector3f(), vec2 = new Vector3f(), vec3 = new Vector3f(), vec4 = new Vector3f(), vec5 = new Vector3f();
	private static Matrix4f matrix = new Matrix4f();

	public static Matrix4f transformMatrix(Vector3f translation, float rotX, float rotY, float rotZ, float scale)
	{
		vec1.set(1, 0, 0);
		vec2.set(0, 1, 0);
		vec3.set(0, 0, 1);
		vec4.set(scale, scale, scale);

		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotX), vec1, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotY), vec2, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotZ), vec3, matrix, matrix);
		Matrix4f.scale(vec4, matrix, matrix);

		return matrix;
	}

	public static Matrix4f viewMatrix(Camera camera)
	{
		vec1.set(1, 0, 0);
		vec2.set(0, 1, 0);
		vec3.set(0, 0, 1);
		vec4.set(1f / Display.getWidth() * camera.getScale(), 1f / Display.getWidth() * camera.getScale(), 1f);
		vec5.set(-camera.getPosition().getX(), -camera.getPosition().getY(), -camera.getPosition().getZ());

		matrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), vec1, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), vec2, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), vec3, matrix, matrix);
		Matrix4f.scale(vec4, matrix, matrix);
		Matrix4f.translate(vec5, matrix, matrix);

		return matrix;
	}
}
