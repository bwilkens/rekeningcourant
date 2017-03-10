package nl.quintor.rc.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.service.KlantService;
import nl.quintor.rc.service.RekeningService;
import nl.quintor.rc.web.rest.dto.KlantDto;
import nl.quintor.rc.web.rest.dto.RekeningDto;
import nl.quintor.rc.web.rest.hal.HalSupport;
import nl.quintor.rc.web.rest.hal.UrlElement;
import nl.quintor.rc.web.transformer.KlantTransformer;
import nl.quintor.rc.web.transformer.RekeningTransformer;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Path("/klanten")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Api("klanten")
public class KlantWebService {
    static final Logger LOG = LoggerFactory.getLogger(KlantWebService.class);

    @Autowired
    private RekeningService rekeningService;

    @Autowired
    private KlantService klantService;

    @Autowired
    private KlantTransformer klantTransformer;

    @Autowired
    private RekeningTransformer rekeningTransformer;


    @Secured("ROLE_ADMIN")
    @GET
    @Path("/")
    @ApiOperation(value = "Ophalen klanten", response = KlantDto.class, responseContainer = "list")
    public Collection<KlantDto> getKlanten() {
        Collection<Klant> klanten = klantService.getKlanten();
        return CollectionUtils.collect(klanten, KlantTransformer.TO_DTO_TRANSFORMER);
    }


    @Secured("ROLE_ADMIN")
    @POST
    @Path("/")
    @ApiOperation(value = "Aanmaken van een klant", response = void.class)
    public Response addKlant(KlantDto klantDto) {
        LOG.trace("processing add new klant reqeust");
        Klant k = klantTransformer.toModel(klantDto);
        klantService.create(k);
        return Response.created(URI.create("/" + k.getId())).build();
    }

    @Secured("ROLE_ADMIN")
    @DELETE
    @Path("/{klantnummer}")
    @ApiOperation(value = "verwijderen van een klant")
    public void verwijderKlant(@PathParam("klantnummer") long id) {
        klantService.delete(id);
    }

    @GET
    @Path("/{klantnummer}")
    @Produces("application/hal+json")
    @Transactional(readOnly = true)
    @ApiOperation(value = "Ophalen klant met hypermedia")
    public Response getKlantGegevensAndRekeningen(@PathParam("klantnummer") long id, @Context UriInfo uriInfo) {
        LOG.trace("getting klantgegevens voor {}", id);
        Klant klant = klantService.getKlantByKlantNummer(id);
        HalSupport halResponse = klantTransformer.toDto(klant);
        halResponse.addLink("self", uriInfo.getAbsolutePathBuilder().build(id).getPath());

        final List<UrlElement> links = klant.getRekeningen()
                .stream()
                .map(rekening -> new UrlElement(uriInfo.getAbsolutePathBuilder().build(id).getPath() + "rekeningen/" + rekening.getRekeningNummer(), Optional.of(rekening.getRekeningNummer()))).collect(Collectors.toList());
        halResponse.addLinks("rekeningen", links);
        return Response.ok(halResponse).build();
    }

    @Secured("ROLE_USER")
    @GET
    @Path("/{klantnummer}")
    @ApiOperation(value = "Ophalen klant")
    public KlantDto getKlantGegevens(@PathParam("klantnummer") long id) {
        LOG.trace("getting klantgegevens voor {}", id);
        Klant klant = klantService.getKlantByKlantNummer(id);

        return klantTransformer.toDto(klant);
    }

    @Secured("ROLE_USER")
    @PUT
    @Path("/{klantnummer}")
    @ApiOperation("update van een klant")
    public Response editKlant(@PathParam("klantnummer") long klantnummer, KlantDto klantDto) {
        LOG.trace("processing edit klant reqeust");
        Klant k = klantTransformer.toModel(klantDto);
        k.setId(klantnummer);
        klantService.updateKlant(k);
        return Response.noContent().build();
    }

    @Secured("ROLE_USER")
    @GET
    @Path("/{klantNummer}/rekeningen")
    @ApiOperation("Ophalen rekeningen")
    public Collection<RekeningDto> getRekeningen(@PathParam("klantNummer") long klantNummer) {
        Collection<Rekening> rekeningen = rekeningService.getRekeningen(klantNummer);
        return CollectionUtils.collect(rekeningen, rekeningTransformer.TO_DTO_TRANSFORMER);
    }


}