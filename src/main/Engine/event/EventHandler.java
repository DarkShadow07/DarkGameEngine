package main.Engine.event;

import main.Engine.event.base.Handler;
import main.Engine.event.base.IEvent;
import main.Engine.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventHandler
{
	public static final EventHandler instance = new EventHandler();

	private Map<Class<? extends IEvent>, List<Handler.FunctionalHandler>> handlers = new HashMap<>();

	public void sendEvent(IEvent event)
	{
		if (handlers.containsKey(event.getClass()))
			for (Handler.FunctionalHandler handler : handlers.get(event.getClass()))
			{
				if (event.isCancelled())
					return;

				handler.handle(event);
			}
	}

	public void registerHandler(Object object)
	{
		try
		{
			object = object.getClass().newInstance();

			for (Field field : object.getClass().getFields())
				if (field.isAnnotationPresent(Handler.class) && Modifier.isFinal(field.getModifiers()))
				{
					Handler.FunctionalHandler handler = field.get(o)

					if (handlers.containsKey(handler.value()))
						handlers.get(handler.value()).add((Handler.FunctionalHandler) field.get(object));
					else
						handlers.put(handler.value(), Collections.singletonList((Handler.FunctionalHandler) field.get(object)));

					Log.info(String.format("Registered Handler %s@%s for Event %s", field.getName(), object.getClass().getSimpleName(), handler.value().getSimpleName()));
				}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
