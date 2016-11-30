package main.Engine.network.connection;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Connection
{
	private String host;
	private int port;

	public Connection(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public InetAddress getAddress()
	{
		try
		{
			return InetAddress.getByName(host);
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public String getHost()
	{
		return host;
	}

	public int getPort()
	{
		return port;
	}

	public static enum Status
	{
		OFFLINE,
		ONLINE,
		TERMINATING,
		CONNECTED,
		DISCONECTED,
		DISCONECTING,
		FAILED
	}
}
