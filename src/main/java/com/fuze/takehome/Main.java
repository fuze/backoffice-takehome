package com.fuze.takehome;

import java.io.IOException;
import java.net.UnknownHostException;
import javax.servlet.ServletException;

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
		UndertowJaxrsServer.initializeServer();
		log.info("Undertow REST server started.");
    }
}
