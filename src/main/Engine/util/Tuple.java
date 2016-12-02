package main.Engine.util;

public class Tuple<F, S, T>
{
	private F first;
	private S second;
	private T third;

	public Tuple(F first, S second, T third)
	{
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public F getFirst()
	{
		return first;
	}

	public S getSecond()
	{
		return second;
	}

	public T getThird()
	{
		return third;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj instanceof Tuple)
		{
			Tuple tuple = (Tuple) obj;
			return tuple.first.equals(first) && tuple.second.equals(second) && tuple.third.equals(third);
		}

		return false;
	}

	@Override
	public String toString()
	{
		return first + ", " + second + ", " + third;
	}
}
