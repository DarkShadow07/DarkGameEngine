package main.Engine.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Log
{
	private static final SimpleDateFormat format = new SimpleDateFormat("HH:MM:ss");

	public static void log(String level, Object... objects)
	{
		String log = "";

		for (Object obj : objects)
			log += String.valueOf(obj) + (Arrays.asList(objects).indexOf(obj) >= objects.length - 1 ? "" : " ");

		System.out.println(String.format("[%s] [%s/%s]: %s", format.format(new Date()), Thread.currentThread().getName(), level, log));
	}

	public static void all(Object... objects)
	{
		log("ALL", objects);
	}

	public static void debug(Object... objects)
	{
		log("DEBUG", objects);
	}

	public static void error(Object... objects)
	{
		log("ERROR", objects);
	}

	public static void fatal(Object... objects)
	{
		log("FATAL", objects);
	}

	public static void info(Object... objects)
	{
		log("INFO", objects);
	}

	public static void warn(Object... objects)
	{
		log("WARN", objects);
	}
}
