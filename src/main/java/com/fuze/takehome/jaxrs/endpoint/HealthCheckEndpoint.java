package com.fuze.takehome.jaxrs.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/health")
public class HealthCheckEndpoint {
	
	@POST
	@PUT 
	@GET 
	@HEAD
	public Response checkHealth() {
		return Response.status(Status.OK).build();
	}
}
