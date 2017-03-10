package nl.quintor.rc.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Demo REST service
 */
@Path("/hellos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelloWebService {
    static final Logger LOG = LoggerFactory.getLogger(HelloWebService.class);


    @GET
    @Path("/")
    public Collection<String> getHellos() {
        Collection<String> hellos = new ArrayList<>();
        hellos.add("Java");
        hellos.add("JAX-RS");
        hellos.add("Jersey");
        return hellos;
    }


    @POST
    @Path("/")
    public void addHello(String hello) {
        LOG.trace("processing hello..." + hello);
    }
}