package main.Engine.font;

import javafx.util.Pair;
import main.Engine.engine.model.ModelLoader;
import main.Engine.engine.model.resource.ResourceLocation;
import main.Engine.engine.model.texture.ModelTexture;
import main.Engine.util.Log;
import org.lwjgl.opengl.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontRenderer
{
	private static final char formatter = '§';
	public static final String BLACK = formatter + "000000";
	public static final String DARK_BLUE = formatter + "00002A";
	public static final String DARK_GREEN = formatter + "002A00";
	public static final String DARK_AQUA = formatter + "002A2A";
	public static final String DARK_RED = formatter + "2A0000";
	public static final String DARK_PURPLE = formatter + "2A002A";
	public static final String GOLD = formatter + "2A2A00";
	public static final String GRAY = formatter + "2A2A2A";
	public static final String DARK_GRAY = formatter + "151515";
	public static final String BLUE = formatter + "15153F";
	public static final String GREEN = formatter + "153F15";
	public static final String AQUA = formatter + "153F3F";
	public static final String RED = formatter + "3F1515";
	public static final String PURPLE = formatter + "3F153F";
	public static final String YELLOW = formatter + "3F3F15";
	public static final String OBFUSCATED = formatter + "k";
	public static final String BOLD = formatter + "l";
	public static final String STRIKETHROUGHT = formatter + "m";
	public static final String UNDERLINE = formatter + "n";
	public static final String ITALIC = formatter + "o";
	public FontLoader fontLoader;
	private String fontFile, fontImg;
	private int lineHeight;
	private Map<Integer, CharData> characters;
	private float scale, xScale, yScale;
	private FontShader shader;
	private List<Text> texts = new ArrayList<>();

	public FontRenderer(ResourceLocation location)
	{
		fontFile = location.toString();
		scale = 1f;

		shader = new FontShader();
		fontLoader = new FontLoader(this);

		readFile();
	}

	public void renderText(Text text)
	{
		if (!texts.contains(text))
		{
			text = fontLoader.loadText(text);
			texts.add(text);
		}
	}

	public void removeText(Text text)
	{
		texts.remove(text);
	}

	public CharData getCharData(char c)
	{
		return characters.get((int) c);
	}

	public void render()
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		shader.start();
		for (Text text : texts)
		{
			GL30.glBindVertexArray(text.getVao());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			shader.color(text.getColor());
			shader.translation(text.getPosition().toVector2());
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, getFontTextureAtlas().getId());
			GL11.glDrawElements(GL11.GL_TRIANGLES, text.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
		shader.stop();

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public void cleanUp()
	{
		shader.remove();
	}

	public int getStringWidth(String string)
	{
		int size = 0;

		for (char c : string.toCharArray())
			size += characters.get((int) c).getWidth() * scale;

		return size;
	}

	public ModelTexture getFontTextureAtlas()
	{
		return ModelLoader.instance.loadTexture(new ResourceLocation(fontImg));
	}

	private void readFile()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File(fontFile)));

			String line;
			List<Pair<String, String>> lineData = new ArrayList<>();

			while ((line = reader.readLine()) != null)
			{
				lineData.clear();

				for (String split : line.split(" "))
				{
					String[] values = split.split("=");

					if (values.length == 2)
						lineData.add(new Pair<>(values[0], values[1]));
				}

				if (lineData.size() == 3)
				{
					fontImg = lineData.get(0).getValue().replaceAll("\"", "");
					characters = new HashMap<>(Integer.valueOf(lineData.get(1).getValue()));
					lineHeight = Integer.valueOf(lineData.get(2).getValue());

					yScale = 0.03f / lineHeight;
					xScale = yScale / ((float) Display.getWidth() / Display.getHeight());
				} else
				{
					characters.put(Integer.valueOf(lineData.get(0).getValue()), new CharData(
							Integer.valueOf(lineData.get(1).getValue()),
							Integer.valueOf(lineData.get(2).getValue()),
							Integer.valueOf(lineData.get(3).getValue()) * xScale,
							Integer.valueOf(lineData.get(4).getValue()) * yScale,
							Integer.valueOf(lineData.get(3).getValue()),
							Integer.valueOf(lineData.get(4).getValue()),
							Integer.valueOf(lineData.get(5).getValue()) * xScale,
							Integer.valueOf(lineData.get(6).getValue()) * yScale,
							Integer.valueOf(lineData.get(7).getValue()) * xScale
					));
				}
			}

			Log.info(this.getClass(), String.format("Loaded %s characters", characters.size()));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static class CharData
	{
		private float x, y, width, height, textureWidth, textureHeight, xOffset, yOffset, xAdvance;

		public CharData(float x, float y, float width, float height, float textureWidth, float textureHeight, float xOffset, float yOffset, float xAdvance)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.textureWidth = textureWidth;
			this.textureHeight = textureHeight;
			this.xOffset = xOffset;
			this.yOffset = yOffset;
			this.xAdvance = xAdvance;
		}

		public float getX()
		{
			return x;
		}

		public float getY()
		{
			return y;
		}

		public float getWidth()
		{
			return width;
		}

		public float getHeight()
		{
			return height;
		}

		public float getTextureWidth()
		{
			return textureWidth;
		}

		public float getTextureHeight()
		{
			return textureHeight;
		}

		public float getxOffset()
		{
			return xOffset;
		}

		public float getyOffset()
		{
			return yOffset;
		}

		public float getxAdvance()
		{
			return xAdvance;
		}
	}
}
