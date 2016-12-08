package main.Engine.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Log
{
	private static final SimpleDateFormat format = new SimpleDateFormat("HH:MM:ss");

	public static void log(Class<?> logger, String level, Object... objects)
	{
		String log = "";

		for (Object obj : objects)
			log += String.valueOf(obj) + (Arrays.asList(objects).indexOf(obj) >= objects.length - 1 ? "" : " ");

		String info = String.format("[%s] [%s/%s] [%s]: ", format.format(new Date()), Thread.currentThread().getName(), level, logger.getSimpleName());

		System.out.println(info + log);

//		if (Arrays.stream(Message.Level.values()).anyMatch(level1 -> level1.toString().equalsIgnoreCase(level)))
//			SmartLog.instance.log(info, log, Message.Level.valueOf(level));
	}

	public static void all(Class<?> logger, Object... objects)
	{
		log(logger, "ALL", objects);
	}

	public static void debug(Class<?> logger, Object... objects)
	{
		log(logger, "DEBUG", objects);
	}

	public static void error(Class<?> logger, Object... objects)
	{
		log(logger, "ERROR", objects);
	}

	public static void fatal(Class<?> logger, Object... objects)
	{
		log(logger, "FATAL", objects);

		System.exit(0);
	}

	public static void info(Class<?> logger, Object... objects)
	{
		log(logger, "INFO", objects);
	}

	public static void warn(Class<?> logger, Object... objects)
	{
		log(logger, "WARN", objects);
	}

	public static void log(String level, Object... objects)
	{
		String log = "";

		for (Object obj : objects)
			log += String.valueOf(obj) + (Arrays.asList(objects).indexOf(obj) >= objects.length - 1 ? "" : " ");

		String info = String.format("[%s] [%s/%s]: ", format.format(new Date()), Thread.currentThread().getName(), level);

		System.out.println(info + log);

//		if (Arrays.stream(Message.Level.values()).anyMatch(level1 -> level1.toString().equalsIgnoreCase(level)))
//			SmartLog.instance.log(info, log, Message.Level.valueOf(level));
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

		System.exit(0);
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
