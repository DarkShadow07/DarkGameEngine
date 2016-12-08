package main.Engine.font;

import main.Engine.engine.model.builder.ModelBuilder;

public class FontLoader
{
	private FontRenderer fontRenderer;

	public FontLoader(FontRenderer fontRenderer)
	{
		this.fontRenderer = fontRenderer;
	}

	public Text loadText(Text text)
	{
		ModelBuilder builder = new ModelBuilder();

		float prevX = text.getPosition().getX();

		for (char c : text.getText().toCharArray())
		{
			FontRenderer.CharData data = fontRenderer.getCharData(c);

			builder.addQuad(
					prevX + data.getxOffset() * text.getSize(),
					text.getPosition().y + data.getyOffset() * text.getSize(),
					0,
					prevX + (data.getxOffset() + data.getWidth()) * text.getSize(),
					text.getPosition().y + (data.getyOffset() + data.getHeight()) * text.getSize(),
					0,
					data.getX(),
					data.getY(),
					data.getX() + data.getTextureWidth(),
					data.getY() + data.getTextureHeight());

			prevX += data.getxAdvance() * text.getSize();
		}

		text.setVAOData(builder.buildRaw().getVAO(), builder.getQuadCount());

		return text;
	}
}
