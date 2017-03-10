package nl.quintor.rc.web.rest;


import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/*")
public class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig() {
        packages("nl.quintor.rc.web.rest", "io.swagger.jaxrs.listing");

        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }


}
