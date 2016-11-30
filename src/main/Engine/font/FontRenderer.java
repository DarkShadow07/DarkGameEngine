package main.Engine.font;

import main.Engine.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
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
	private String fontFile, fontImg;
	private Map<Integer, CharData> characters;
	private float scale;

	public FontRenderer(String fontFile)
	{
		this.fontFile = fontFile;
		scale = 1f;

		readFile();
	}

	public int getStringWidth(String string)
	{
		int size = 0;

		for (char c : string.toCharArray())
			size += characters.get((int) c).getWidth() * scale;

		return size;
	}

	private void readFile()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(Class.class.getResourceAsStream(fontFile)));

			String line;

			while ((line = reader.readLine()) != null)
			{
				if (line.contains("file="))
					fontImg = line.substring(line.indexOf("file=") + 5).replaceAll("\"", "");
				if (line.contains("count="))
					characters = new HashMap<>(Integer.valueOf(line.split("=")[1]));
				if (line.contains("char id"))
				{
					int id = 0, x = 0, y = 0, width = 0, height = 0, yOffset = 0;

					for (String s : line.split(" "))
						if (s.contains("id="))
							id = Integer.valueOf(s.split("=")[1]);
						else if (s.contains("x="))
							x = Integer.valueOf(s.split("=")[1]);
						else if (s.contains("y="))
							y = Integer.valueOf(s.split("=")[1]);
						else if (s.contains("width="))
							width = Integer.valueOf(s.split("=")[1]);
						else if (s.contains("height="))
							height = Integer.valueOf(s.split("=")[1]);
						else if (s.contains("yoffset="))
							yOffset = Integer.valueOf(s.split("=")[1]);

					characters.put(id, new CharData(x, y, width, height, yOffset));
				}
			}

			if (fontImg == null)
				throw new NullPointerException(String.format("Font Image is not defined for font %s!", fontFile));
			Log.info(String.format("Loaded %s characters", characters.size()));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private class CharData
	{
		private int x, y, width, height, yOffset;

		public CharData(int x, int y, int width, int height, int yOffset)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.yOffset = yOffset;
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}

		public int getWidth()
		{
			return width;
		}

		public int getHeight()
		{
			return height;
		}

		public int getyOffset()
		{
			return yOffset;
		}
	}
}
