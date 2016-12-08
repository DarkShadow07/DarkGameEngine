package main.Engine.engine.model;

import main.Engine.engine.camera.Camera;
import main.Engine.engine.model.base.DynamicRawModel;
import main.Engine.engine.model.base.RawModel;
import main.Engine.engine.model.base.TexturedModel;
import main.Engine.engine.model.shader.StaticShader;
import main.Engine.entity.IEntity;
import main.Engine.util.math.Maths;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

public class ModelRenderer
{
	private Matrix4f projectionMatrix = new Matrix4f();
	private StaticShader shader;
	private Camera camera;

	public ModelRenderer(StaticShader shader)
	{
		this.shader = shader;

		createProjectionMatrix();
	}

	public void prepare()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(0, 0, 1, 1);
	}

	public void renderDynamicRaw(DynamicRawModel model)
	{
		GL30.glBindVertexArray(model.getVAO());
		model.enableAttributes();
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		model.disableAttributes();
		GL30.glBindVertexArray(0);
	}

	public void renderEntity(IEntity entity, StaticShader shader)
	{
		TexturedModel texturedModel = entity.getModel();
		RawModel model = texturedModel.getRawModel();

		GL30.glBindVertexArray(model.getVAO());
		model.enableAttributes();
		Matrix4f transformation = Maths.transformMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.transform(transformation);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getId());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		model.disableAttributes();
		GL30.glBindVertexArray(0);
	}

	public void renderTextured(TexturedModel texturedModel)
	{
		RawModel model = texturedModel.getRawModel();

		GL30.glBindVertexArray(model.getVAO());
		model.enableAttributes();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getId());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		model.disableAttributes();
		GL30.glBindVertexArray(0);
	}

	public void renderRaw(RawModel model)
	{
		GL30.glBindVertexArray(model.getVAO());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public Camera getCamera()
	{
		return camera;
	}

	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}

	public StaticShader getShader()
	{
		return shader;
	}

	public void setShader(StaticShader shader)
	{
		this.shader = shader;
	}

	public void createProjectionMatrix()
	{
		projectionMatrix.setIdentity();
		projectionMatrix.m00 = 1;
		projectionMatrix.m11 = (float) Display.getWidth() / Display.getHeight();

		shader.start();
		shader.project(projectionMatrix);
		shader.stop();
	}
}
