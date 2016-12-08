package main.Engine.util;

import java.io.PrintStream;

public class GamePrintStream extends PrintStream
{
	public GamePrintStream(PrintStream out)
	{
		super(out);
	}

	@Override
	public void println(String x)
	{
		super.println(x);
	}
}
