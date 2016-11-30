package main.Engine.network.channel;

import com.sun.istack.internal.Nullable;
import io.netty.buffer.ByteBuf;
import main.Engine.network.connection.IConnectionHandler;
import main.Engine.network.message.IMessage;
import main.Engine.network.message.MessageHandler;

public class SimpleChannel
{
	private IConnectionHandler connection;

	public SimpleChannel(IConnectionHandler connection)
	{
		this.connection = connection;
	}

	@Nullable
	public IMessage messageReceived(int id, ByteBuf buf)
	{
		IMessage message = MessageHandler.instance.createMessage(id);
		message.readBytes(buf);

		return message.onMessage(message);
	}

	public IConnectionHandler getConnection()
	{
		return connection;
	}
}
