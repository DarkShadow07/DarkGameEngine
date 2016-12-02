package main.Engine.engine.network.connection;

import java.util.List;

public interface IConnectionHandler
{
	void startConnection(Connection connection);

	void terminateConnection();

	List<ConnectionStreams> getStreams();

	void updateStreams();

	Connection.Status getStatus();

	void setStatus(Connection.Status status);

	Connection getConnection();

	void setConnection(Connection connection);
}
