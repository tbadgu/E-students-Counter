package gui;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4j {

	static final Logger log = Logger.getRootLogger();

	public Log4j()
	{
		
	}
	public void infor(String s)
	{
		PropertyConfigurator.configure("log4j.properties");
		log.info(s);
	}

	public void debuger()
	{
		PropertyConfigurator.configure("log4j.properties");
		log.debug("Sample debug message");
	}
	
	public void warning(String s)
	{
		PropertyConfigurator.configure("log4j.properties");
		log.warn(s);
	}
	
	public void errorer(String s)
	{
		PropertyConfigurator.configure("log4j.properties");
		log.error("printStackTraceError " + s);
	}
	
	public void fatal_error()
	{
		PropertyConfigurator.configure("log4j.properties");
		log.fatal("Sample fatal message");
	}
}
