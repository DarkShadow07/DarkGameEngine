package main.Engine.network;

import main.Engine.network.connection.Connection;
import main.Engine.network.connection.ServerConnectionHandler;
import main.Engine.util.Log;

public class NetworkServer
{
	private ServerConnectionHandler connection = new ServerConnectionHandler();

	private Thread connectionThread;

	public NetworkServer(Connection connection)
	{
		this.connection.setConnection(connection);
	}

	public void startServer()
	{
		connection.startConnection();

		Log.info(String.format("Server Started, address: '%s:%s'", connection.getConnection().getHost(), connection.getConnection().getPort()));

		connectionThread = new Thread(() ->
		{
			try
			{
				Log.info("Listening Connections...");

				while (connection.getStatus().equals(Connection.Status.ONLINE))
				{
					Thread.sleep(1);

					connection.listenConnections();
				}
			} catch (Exception e)
			{
				connection.terminateConnection();

				e.printStackTrace();
			}
		}, "Connection Thread");
		connectionThread.start();
	}

	public synchronized void stopServer()
	{
		Log.info("Terminating Server...");

		connection.terminateConnection();

		try
		{
			connectionThread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		Log.info("Server Closed");
	}

	public ServerConnectionHandler getConnection()
	{
		return connection;
	}

	public Connection.Status getStatus()
	{
		return connection.getStatus();
	}
}
