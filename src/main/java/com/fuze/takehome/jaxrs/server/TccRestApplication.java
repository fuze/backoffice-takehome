package com.fuze.takehome.jaxrs.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.fuze.takehome.jaxrs.server.endpoint.CustomerEndpoint;

@ApplicationPath("/")
public class TccRestApplication extends Application
{
	@Override
	public Set<Object> getSingletons()
	{
		Set<Object> singletons = new HashSet<Object>();
		singletons.add(new CustomerEndpoint());
		return singletons;
	}
}
