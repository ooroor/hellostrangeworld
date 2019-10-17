package net.barakiroth.hellostrangeworld.farbackend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GreetingDescriptorResource {

	@GET
	@Path("/GreetingDescriptor")
	@Produces({MediaType.APPLICATION_JSON})
	public String getGreetingDescriptor() {
		
		final GreetingDescriptor greetingDescriptor =
				new GreetingDescriptor();
		
		final String greeteeDescriptionAsJson =
				greetingDescriptor.describeGreetee();
		
		return greeteeDescriptionAsJson;
	}
}