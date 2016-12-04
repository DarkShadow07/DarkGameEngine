package main.Engine.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class GamePrintStream extends PrintStream
{
	List<String> data = new ArrayList<>();

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
