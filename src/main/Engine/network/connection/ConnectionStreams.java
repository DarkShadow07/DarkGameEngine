package main.Engine.network.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectionStreams
{
	private InputStream input;
	private OutputStream output;

	public ConnectionStreams(InputStream input, OutputStream output)
	{
		this.input = input;
		this.output = output;
	}

	public void close()
	{
		try
		{
			input.close();
			output.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public InputStream getInput()
	{
		return input;
	}

	public OutputStream getOutput()
	{
		return output;
	}
}
