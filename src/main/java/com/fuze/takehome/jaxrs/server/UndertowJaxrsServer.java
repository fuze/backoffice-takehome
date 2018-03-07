package com.fuze.takehome.jaxrs.server;

import javax.servlet.ServletException;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.jboss.resteasy.plugins.spring.SpringContextLoaderListener;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

public class UndertowJaxrsServer {
	
	public static Undertow initializeServer() throws ServletException {
		//Add an HTTP listener bound to port 50001 and listing to all
		Undertow server = Undertow.builder()
                .addHttpListener(50001, "0.0.0.0")
                .setHandler(buildDeploymentManager())
                .build();
		
		server.start();		
		return server;
	}
	
	private static HttpHandler buildDeploymentManager() throws ServletException {
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(UndertowJaxrsServer.class.getClassLoader())
                .setDeploymentName("UndertowJaxrsServer")
                .setContextPath("/")
                //Location of the Spring XML configuration file
                .addInitParameter("contextConfigLocation", "classpath:spring/application.xml")
                .addServlets(
                		 Servlets.servlet("REST Service", HttpServlet30Dispatcher.class)
                		 .setAsyncSupported(true)
                         .setLoadOnStartup(1)
                         .addMapping("/*")
                )
                .addListeners(
                		//Initializes Rest Easy automatically
                        Servlets.listener(ResteasyBootstrap.class),
                        //Initializes Spring
                        Servlets.listener(SpringContextLoaderListener.class)
                );

        DeploymentManager deploymentManager = Servlets.defaultContainer().addDeployment(servletBuilder);
        deploymentManager.deploy();
        return deploymentManager.start();
    }
}
