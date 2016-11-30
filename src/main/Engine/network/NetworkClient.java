package main.Engine.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import main.Engine.network.channel.ChannelHandler;
import main.Engine.network.connection.ClientConnectionHandler;
import main.Engine.network.connection.Connection;
import main.Engine.network.connection.ConnectionStreams;
import main.Engine.util.Log;

import java.io.InputStream;
import java.net.SocketException;

public class NetworkClient
{
	private ClientConnectionHandler connection = new ClientConnectionHandler();

	private Thread clientThread;

	public NetworkClient(Connection connection)
	{
		this.connection.setConnection(connection);
	}

	public void startClient()
	{
		if (connection.getStatus().equals(Connection.Status.CONNECTED)) return;

		connection.startConnection();

		if (!connection.getStatus().equals(Connection.Status.CONNECTED)) return;

		clientThread = new Thread(() ->
		{
			Log.info("Client Connection Started");

			try
			{
				while (connection.getStatus().equals(Connection.Status.CONNECTED))
				{
					ConnectionStreams streams = connection.getStreams().get(0);

					InputStream input = streams.getInput();

					byte[] bytes = new byte[ChannelHandler.instance.getBufferSize()];
					int from = input.read(), to = input.read(), id = input.read();
					int size = input.read(bytes);
					if (size <= 0) return;
					ByteBuf buf = Unpooled.copiedBuffer(bytes, 0, size);
					ChannelHandler.instance.messageReceived(from, to, id, buf);
				}
			} catch (SocketException ignored)
			{

			} catch (Exception e)
			{
				connection.terminateConnection();

				e.printStackTrace();
			}
		}, "Client Thread");
		clientThread.start();
	}

	public synchronized void stopClient()
	{
		Log.info("Terminating Client Connection...");
		connection.terminateConnection();
		try
		{
			clientThread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		Log.info("Client Connection Closed");
	}

	public ClientConnectionHandler getConnection()
	{
		return connection;
	}

	public Connection.Status getStatus()
	{
		return connection.getStatus();
	}
}
