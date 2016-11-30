package main.Engine.network.channel;

import com.sun.istack.internal.NotNull;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import main.Engine.network.connection.ConnectionStreams;
import main.Engine.network.message.IMessage;
import main.Engine.network.message.MessageHandler;
import main.Engine.util.Log;
import main.Engine.util.Tuple;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ChannelHandler
{
	public static final ChannelHandler instance = new ChannelHandler();

	private List<SimpleChannel> channels = new ArrayList<>();
	private Deque<Tuple<Integer, Integer, IMessage>> queue = new ArrayDeque<>();

	private int bufferSize = 0;

	private Thread messageThread;

	public ChannelHandler()
	{
		messageThread = new Thread(() ->
		{
			while (true)
			{
				try
				{
					Thread.sleep(1);

					if (!queue.isEmpty())
					{
						Tuple<Integer, Integer, IMessage> message = queue.pollFirst();
						writeAndFlush(message.getFirst(), message.getSecond(), message.getThird());
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}, "ChannelHandler");
		messageThread.start();
	}

	public int getBufferSize()
	{
		return bufferSize;
	}

	public void setBufferSize(int bufferSize)
	{
		this.bufferSize = bufferSize;
	}

	public void messageReceived(int from, int to, int id, ByteBuf buf)
	{
		IMessage reply = channels.get(to).messageReceived(id, buf);

		if (reply != null)
			sendMessage(to, from, reply);
	}

	public void sendMessage(@NotNull int from, @NotNull int to, @NotNull IMessage message)
	{
		if (from == -1) throw new NullPointerException("No Channel with ID " + from);
		if (to == -1) throw new NullPointerException("No Channel with ID " + to);
		if (from == to) throw new IllegalArgumentException("Can't send to the same Channel");

		queue.addLast(new Tuple<>(from, to, message));
	}

	private void writeAndFlush(int from, int to, IMessage message)
	{
		ByteBuf buf = Unpooled.buffer(bufferSize);
		message.writeBytes(buf);

		byte[] bytes = buf.array();

		for (ConnectionStreams streams : channels.get(from).getConnection().getStreams())
		{
			OutputStream output = streams.getOutput();

			try
			{
				output.write(from);
				output.write(to);
				output.write(MessageHandler.instance.getMessageId(message.getClass()));
				output.write(bytes, 0, buf.readableBytes());
				output.flush();
			} catch (IOException e)
			{
				Log.error(String.format("Caught Exception while trying to send Message %s, from Channel %s to Channel %s", message.getClass().getSimpleName(), from, to));
				e.printStackTrace();
			}
		}
	}

	public void registerChannel(SimpleChannel channel)
	{
		channels.add(channel);
	}
}
