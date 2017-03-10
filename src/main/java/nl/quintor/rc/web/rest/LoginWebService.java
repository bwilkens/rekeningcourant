package nl.quintor.rc.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nl.quintor.rc.model.Gebruiker;
import nl.quintor.rc.service.GebruikerService;
import nl.quintor.rc.web.rest.dto.*;
import nl.quintor.rc.web.transformer.GebruikerTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Service
@Path("/gebruiker")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Api("gebruiker")
public class LoginWebService {
    static final Logger LOG = LoggerFactory.getLogger(LoginWebService.class);

    @Autowired
    private GebruikerService gebruikerService;

    @Autowired
    private GebruikerTransformer gebruikerTransformer;

    @GET
    @Path("/{username}")
    @ApiOperation("inloggen van een gebruiker")
    public GebruikerDto login(@PathParam("username") String username) {
        try {
        	System.out.println("login voor " + username);
            Gebruiker gebruiker = gebruikerService.inloggenGebruiker(username);
            return gebruikerTransformer.toDto(gebruiker);
        } catch (Exception e) {
            //todo, use exception mapper
            LOG.error("fout tijdens inloggen", e);
            throw new WebApplicationException(e);
        }
    }
}