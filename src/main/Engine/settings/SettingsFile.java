package main.Engine.settings;

import java.util.HashMap;
import java.util.Map;

public class SettingsFile
{
	private Map<String, Object> values = new HashMap<>();

	public SettingsFile()
	{

	}

	public void setValue(String property, Object value)
	{
		values.put(property, value);
	}

	public <T> T getValue(String property)
	{
		if (values.containsKey(property))
			return (T) values.get(property);
		else return null;
	}
}
