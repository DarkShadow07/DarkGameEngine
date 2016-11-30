package main.Engine.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Util
{
	public static ByteBuffer imageToByteBuffer(InputStream imageStream) throws IOException
	{
		BufferedImage bufferedimage = ImageIO.read(imageStream);
		int[] rgb = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
		ByteBuffer bytebuffer = ByteBuffer.allocate(4 * rgb.length);

		for (int i : rgb)
		{
			bytebuffer.putInt(i << 8 | i >> 24 & 255);
		}

		bytebuffer.flip();
		return bytebuffer;
	}

	public static BufferedImage loadImage(String path)
	{
		try
		{
			return ImageIO.read(Class.class.getResourceAsStream("/" + path));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
