package com.fuze.takehome;

import java.io.IOException;
import java.net.UnknownHostException;
import javax.servlet.ServletException;

import org.hsqldb.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuze.takehome.jaxrs.server.UndertowJaxrsServer;

public class Main
{
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception
	{
		try
		{
    		initializeDBServer();
		}
		catch(Throwable t)
		{
			log.error("Failed to start Web Server", t);
			return;
		}
		
		try
		{
    		initializeRestServer();
		}
		catch(Throwable t)
		{
			log.error("Failed to start Web Server", t);
			return;
		}
	}
	
	private static void initializeRestServer() throws UnknownHostException, IOException, InterruptedException, ServletException
	{		
		log.info("Initializing Undertow REST server...");
		//Starts and Undertow web server that provides a servlet container for RestEasy
		//Also initializes Spring.
		UndertowJaxrsServer.initializeServer(); 
		log.info("Undertow REST server started.");
    }
	
	private static void initializeDBServer()
	{		
		log.info("Initializing HSQLDB in-memory DB server...");
		Server server = new Server();
		server.setDatabaseName(0, "takeHomeDB");
		server.setDatabasePath(0, "mem:takeHomeDB"); //In-memory only configuration
		server.setPort(9097); //Pick a port unlikely to conflict
		server.start();
		log.info("HSQLDB server started.");
    }
}
