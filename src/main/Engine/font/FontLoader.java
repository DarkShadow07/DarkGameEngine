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
		int[] ints = loadTextVAO(text);

		text.setVAOData(ints[0], ints[1]);

		return text;
	}

	private int[] loadTextVAO(Text text)
	{
		ModelBuilder.TwoDimensional builder = new ModelBuilder.TwoDimensional();

		float prevX = text.getPosition().getX();

		for (char c : text.getText().toCharArray())
		{
			FontRenderer.CharData data = fontRenderer.getCharData(c);

			builder.addQuad(
					prevX + data.getxOffset(),
					text.getPosition().y + data.getyOffset(),
					prevX + data.getxOffset() + data.getWidth() * text.getSize(),
					text.getPosition().y + data.getyOffset() + data.getHeight() * text.getSize(),
					data.getX(), data.getY(),
					data.getX() + data.getWidth(), data.getY() + data.getHeight());

			prevX += data.getxAdvance() * text.getSize();
		}

		return new int[]{builder.buildRaw(), builder.getQuadCount()};
	}
}
