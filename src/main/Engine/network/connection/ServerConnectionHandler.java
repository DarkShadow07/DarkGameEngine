package main.Engine.network.connection;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import main.Engine.network.channel.ChannelHandler;
import main.Engine.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ServerConnectionHandler implements IConnectionHandler
{
	private Connection connection;

	private ServerSocket serverSocket;

	private List<ConnectionStreams> clients = new ArrayList<>();
	private List<ConnectionStreams> addQueue = new ArrayList<>();

	private Connection.Status status = Connection.Status.OFFLINE;

	public ServerConnectionHandler()
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
			serverSocket = new ServerSocket(connection.getPort(), 0, connection.getAddress());

			setStatus(Connection.Status.ONLINE);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void listenConnections()
	{
		try
		{
			Socket socket = serverSocket.accept();
			final ConnectionStreams streams = new ConnectionStreams(socket.getInputStream(), socket.getOutputStream());
			addQueue.add(streams);

			Log.info(String.format("Client Connection Accepted, %s", socket.getInetAddress().getHostName()));

			new Thread(() ->
			{
				while (!socket.isClosed())
				{
					try
					{
						InputStream input = streams.getInput();

						byte[] bytes = new byte[ChannelHandler.instance.getBufferSize()];
						int from = input.read(), to = input.read(), id = input.read();
						int size = input.read(bytes);
						if (size <= 0) return;
						ByteBuf buf = Unpooled.copiedBuffer(bytes, 0, size);
						ChannelHandler.instance.messageReceived(from, to, id, buf);
					} catch (SocketException ignored)
					{

					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}, "Listen Client@" + socket.getInetAddress().getHostName()).start();

			updateStreams();
		} catch (IOException e)
		{
			if (serverSocket != null && !serverSocket.isClosed())
				e.printStackTrace();
		}
	}

	@Override
	public void terminateConnection()
	{
		if (serverSocket == null) return;

		setStatus(Connection.Status.TERMINATING);

		try
		{
			serverSocket.close();
			serverSocket = null;

			try
			{
				clients.forEach(ConnectionStreams::close);
			} finally
			{
				clients.clear();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		setStatus(Connection.Status.OFFLINE);
	}

	@Override
	public List<ConnectionStreams> getStreams()
	{
		return clients;
	}

	@Override
	public void updateStreams()
	{
		clients.addAll(addQueue);
		addQueue.clear();
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
