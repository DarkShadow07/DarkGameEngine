package main.Engine.model;

public class ModelUtil
{
	public static class Dynamic
	{
		public static float[] updatePositions(float[] data, int quadIndex, float x1, float y1, float z1, float x2, float y2, float z2)
		{
			data[12 * quadIndex] = x1;
			data[12 * quadIndex + 1] = y2;
			data[12 * quadIndex + 2] = z1;
			data[12 * quadIndex + 3] = x1;
			data[12 * quadIndex + 4] = y1;
			data[12 * quadIndex + 5] = z1;
			data[12 * quadIndex + 6] = x2;
			data[12 * quadIndex + 7] = y1;
			data[12 * quadIndex + 8] = z2;
			data[12 * quadIndex + 9] = x2;
			data[12 * quadIndex + 10] = y2;
			data[12 * quadIndex + 11] = z2;

			return data;
		}

		public static float[] updateUVs(float[] data, int quadIndex, float u1, float v1, float u2, float v2)
		{
			data[8 * quadIndex] = u1;
			data[8 * quadIndex + 1] = v1;
			data[8 * quadIndex + 2] = u1;
			data[8 * quadIndex + 3] = v2;
			data[8 * quadIndex + 4] = u2;
			data[8 * quadIndex + 5] = v2;
			data[8 * quadIndex + 6] = u2;
			data[8 * quadIndex + 7] = v1;

			return data;
		}
	}
}
