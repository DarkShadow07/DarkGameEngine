package main.Engine.engine.model.shader;

import main.Engine.util.Log;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;

public abstract class BaseShader
{
	private int id, vertexShader, fragmentShader;

	private FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);

	public BaseShader(String vertexFile, String fragmentFile)
	{
		vertexShader = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShader = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);

		id = GL20.glCreateProgram();
		GL20.glAttachShader(id, vertexShader);
		GL20.glAttachShader(id, fragmentShader);
		bindAttributes();
		GL20.glLinkProgram(id);
		GL20.glValidateProgram(id);
		getUniformLocations();
	}

	protected void loadMatrix(int location, Matrix4f matrix)
	{
		matrix.store(floatBuffer);
		floatBuffer.flip();

		GL20.glUniformMatrix4(location, false, floatBuffer);
	}

	protected void loadBoolean(int location, boolean value)
	{
		GL20.glUniform1f(location, value ? 1 : 0);
	}

	protected void loadVector(int location, Vector3f vector)
	{
		GL20.glUniform3f(location, vector.getX(), vector.getY(), vector.getZ());
	}

	protected void loadFloat(int location, float value)
	{
		GL20.glUniform1f(location, value);
	}

	protected abstract void getUniformLocations();

	protected int getUniformLocation(String name)
	{
		return GL20.glGetUniformLocation(id, name);
	}

	public void start()
	{
		GL20.glUseProgram(id);
	}

	public void stop()
	{
		GL20.glUseProgram(0);
	}

	public void remove()
	{
		stop();

		GL20.glDetachShader(id, vertexShader);
		GL20.glDetachShader(id, fragmentShader);
		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);
		GL20.glDeleteProgram(id);
	}

	protected abstract void bindAttributes();

	protected void bindAttribute(int attribute, String name)
	{
		GL20.glBindAttribLocation(id, attribute, name);
	}

	private int loadShader(String file, int type)
	{
		StringBuilder builder = new StringBuilder();

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));

			reader.lines().forEach(line -> builder.append(line).append("\n"));

			reader.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		int id = GL20.glCreateShader(type);
		GL20.glShaderSource(id, builder);
		GL20.glCompileShader(id);
		if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			Log.error(GL20.glGetShaderInfoLog(id, 500));
			System.exit(-2);
		}

		return id;
	}
}
