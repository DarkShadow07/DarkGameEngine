package main.Engine.event;

import main.Engine.event.base.FunctionalHandler;
import main.Engine.event.base.Handler;
import main.Engine.event.base.IEvent;
import main.Engine.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventHandler
{
	public static final EventHandler instance = new EventHandler();

	private Map<Class<? extends IEvent>, List<FunctionalHandler>> handlers = new HashMap<>();

	@SuppressWarnings("unchecked")
	public void sendEvent(IEvent event)
	{
		if (handlers.containsKey(event.getClass()))
			for (FunctionalHandler handler : handlers.get(event.getClass()))
			{
				if (event.isCancelled())
					return;

				handler.handle(event);
			}
	}

	@SuppressWarnings("unchecked")
	public void registerHandler(Object object)
	{
		try
		{
			for (Field field : object.getClass().getDeclaredFields())
				if (field.isAnnotationPresent(Handler.class))
				{
					FunctionalHandler handler = (FunctionalHandler) field.get(object);

					Class<? extends IEvent> event = (Class<? extends IEvent>) Class.forName(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName());

					if (handlers.containsKey(event))
						handlers.get(event).add(handler);
					else
						handlers.put(event, Collections.singletonList(handler));
					Log.info(String.format("Registered Handler %s@%s for %s", field.getName(), object.getClass().getSimpleName(), event.getSimpleName()));
				}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
