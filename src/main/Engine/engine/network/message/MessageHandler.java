package main.Engine.engine.network.message;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler
{
	public static final MessageHandler instance = new MessageHandler();

	private List<Class<? extends IMessage>> messages = new ArrayList<>();

	public void registerMessage(Class<? extends IMessage> message)
	{
		messages.add(message);
	}

	public int getMessageId(Class<? extends IMessage> message)
	{
		return messages.indexOf(message);
	}

	public IMessage createMessage(int id)
	{
		try
		{
			return messages.get(id).newInstance();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
