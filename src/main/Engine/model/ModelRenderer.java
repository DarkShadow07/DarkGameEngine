package main.Engine.model;

import main.Engine.entity.IEntity;
import main.Engine.model.base.DynamicRawModel;
import main.Engine.model.base.RawModel;
import main.Engine.model.base.TexturedModel;
import main.Engine.shader.StaticShader;
import main.Engine.util.Log;
import main.Engine.util.math.Maths;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

public class ModelRenderer
{
	private Matrix4f projectionMatrix;
	private StaticShader shader;

	public ModelRenderer(StaticShader shader)
	{
		this.shader = shader;

		createProjectionMatrix();
	}

	public void preRender()
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
		if (model instanceof DynamicRawModel)
			((DynamicRawModel) model).enableAttributes();
		else
		{
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
		}
		Matrix4f transformation = Maths.transformMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.transform(transformation);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getId());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		if (model instanceof DynamicRawModel)
			((DynamicRawModel) model).disableAttributes();
		else
		{
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
		}
		GL30.glBindVertexArray(0);
	}

	public void renderTextured(TexturedModel texturedModel)
	{
		RawModel model = texturedModel.getRawModel();

		GL30.glBindVertexArray(model.getVAO());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getId());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
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

	public void createProjectionMatrix()
	{
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = 1;
		projectionMatrix.m11 = (float) Display.getWidth() / Display.getHeight();

		shader.start();
		shader.project(projectionMatrix);
		shader.stop();
	}
}
