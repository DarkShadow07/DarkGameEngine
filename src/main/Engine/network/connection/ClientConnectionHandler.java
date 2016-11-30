package main.Engine.network.connection;

import main.Engine.network.channel.ChannelHandler;
import main.Engine.network.channel.SimpleChannel;
import main.Engine.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

public class ClientConnectionHandler implements IConnectionHandler
{
	private Connection connection;

	private Connection.Status status = Connection.Status.DISCONECTED;

	private Socket socket;

	private ConnectionStreams streams;

	public ClientConnectionHandler()
	{

	}

	public void startConnection()
	{
		startConnection(connection);
	}

	@Override
	public void startConnection(Connection connection)
	{
		this.connection = connection;

		try
		{
			socket = new Socket(connection.getAddress(), connection.getPort());

			streams = new ConnectionStreams(socket.getInputStream(), socket.getOutputStream());
		} catch (IOException e)
		{
			Log.error("Failed to Connect to Server", e);
		}

		setStatus(Connection.Status.CONNECTED);
	}

	@Override
	public void terminateConnection()
	{
		if (socket == null) return;

		setStatus(Connection.Status.TERMINATING);

		try
		{
			socket.close();
			socket = null;
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		setStatus(Connection.Status.DISCONECTED);
	}

	@Override
	public List<ConnectionStreams> getStreams()
	{
		return Collections.singletonList(streams);
	}

	@Override
	public void updateStreams()
	{

	}

	@Override
	public Connection.Status getStatus()
	{
		return status;
	}

	@Override
	public void setStatus(Connection.Status status)
	{
		this.status = status;
	}

	@Override
	public Connection getConnection()
	{
		return connection;
	}

	@Override
	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}
}
