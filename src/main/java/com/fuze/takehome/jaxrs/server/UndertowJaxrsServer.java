package com.fuze.takehome.jaxrs.server;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;

import com.fuze.takehome.jaxrs.json.JsonMessageBodyReader;
import com.fuze.takehome.jaxrs.json.JsonMessageBodyWriter;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

public class UndertowJaxrsServer
{
	public static Undertow initializeServer() throws ServletException
	{		
		Undertow server = Undertow.builder()
                .addHttpListener(50001, "0.0.0.0")
                .setHandler(buildDeploymentManager())
                .build();
		
		server.start();		
		return server;
	}
	
	private static HttpHandler buildDeploymentManager() throws ServletException
	{
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(UndertowJaxrsServer.class.getClassLoader())
                .setDeploymentName("UndertowJaxrsServer")
                .setContextPath("/")
                .addServlets(
                		 Servlets.servlet("REST Service", HttpServlet30Dispatcher.class)
                		 .setAsyncSupported(true)
                         .setLoadOnStartup(1)
                         .addMapping("/*")
                )
                .addServletContextAttribute(ResteasyDeployment.class.getName(), getDeployment());

        DeploymentManager deploymentManager = Servlets.defaultContainer().addDeployment(servletBuilder);
        deploymentManager.deploy();
        return deploymentManager.start();
    }

	private static ResteasyDeployment getDeployment()
	{
		Application application = new TccRestApplication();
		ResteasyDeployment deployment = new ResteasyDeployment();
		deployment.setApplication(application);
		deployment.getProviders().add(new JsonMessageBodyReader());
		deployment.getProviders().add(new JsonMessageBodyWriter());
		return deployment;
	}
}
