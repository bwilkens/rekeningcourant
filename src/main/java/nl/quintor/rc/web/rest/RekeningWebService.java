package nl.quintor.rc.web.rest;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import nl.quintor.rc.model.Klant;
import nl.quintor.rc.model.Overboeking;
import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.model.Status;
import nl.quintor.rc.service.RekeningService;
import nl.quintor.rc.web.rest.dto.KlantDto;
import nl.quintor.rc.web.rest.dto.OverboekingDto;
import nl.quintor.rc.web.rest.dto.RekeningDto;
import nl.quintor.rc.web.transformer.RekeningTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Service
@Path("/rekeningen")
@Api("rekeningen")
public class RekeningWebService {
    static final Logger LOG = LoggerFactory.getLogger(RekeningWebService.class);

    @Inject
    private RekeningService rekeningService;

    @Autowired
    private RekeningTransformer rekeningTransformer;


    @Transactional(readOnly = true)
    @Secured("ROLE_ADMIN")
    @GET
    @Path("/")
    @ApiOperation("Ophalen rekeningen met bepaalde status")
    public List<RekeningDto> getNietGoedGekeurdeRekeningen(@QueryParam("status") String status) {
        LOG.debug("ophalen nog niet goedgekeurde rekeningen");
        List<RekeningDto> rekeningen = new ArrayList<>();
        if (status.equals(Status.INITIEEL.name())) {
            for (Rekening rekening : rekeningService.getGoedTeKeurenRekeningen()) {
                RekeningDto rekeningDTO = new RekeningDto();

                rekeningDTO.setRekeningNummer(rekening.getRekeningNummer());
                rekeningDTO.setType(rekening.getType());

                List<KlantDto> klanten = new ArrayList<>();
                for (Klant klant : rekening.getRekeninghouders()) {
                    KlantDto klantDTO = new KlantDto();
                    klantDTO.setBsn(klant.getBsn());
                    klanten.add(klantDTO);
                }
                rekeningDTO.setKlanten(klanten);
                rekeningen.add(rekeningDTO);
            }
        } else {
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED).entity("alleen status INITIEEL wordt ondersteund").type("text/plain").build());
        }

        return rekeningen;
    }

    @Secured("ROLE_ADMIN")
    @Path("/{rekeningnummer}/acties/blokkeer")
    @POST
    @ApiOperation("blokkeren van een rekening")
    public void blokkeerRekening(@PathParam("rekeningnummer") String rekeningnummer, String reden) {
        rekeningService.blokkeerRekening(rekeningnummer, reden);
    }

    @Secured("ROLE_ADMIN")
    @Path("/{rekeningnummer}/acties/goedkeuren")
    @POST
    @ApiOperation("goedkeuren van een rekening")
    public void rekeningGoedkeuren(@PathParam("rekeningnummer") String rekeningnummer) {
        rekeningService.goedkeurenRekening(rekeningnummer);
    }

    @Secured("ROLE_ADMIN")
    @DELETE
    @Path("/{rekeningnummer}")
    @ApiOperation("beeindigen van een rekening")
    public void beeindigRekening(@PathParam("rekeningnummer") String rekeningnummer) {
        rekeningService.beeindigRekening(rekeningnummer);
    }

    @Secured("ROLE_USER")
    @Path("/")
    @POST
    @ApiOperation("aanmaken van een rekening")
    public Response createRekening(RekeningDto rekeningDto) {
        List<Long> klanten = new ArrayList<>();
        for (KlantDto klantDto : rekeningDto.getKlanten()) {
            klanten.add(klantDto.getKlantnummer());
        }
        Rekening rekening = new Rekening();
        rekening.setType(rekeningDto.getType());
        try {
            rekeningService.createRekening(rekening, klanten);
            LOG.debug("aantal rekeningen in database = {}", rekeningService.getRekeningen(1).size());
        } catch (Exception e) {
            LOG.error("fout bij aanmaken rekening", e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.created(URI.create("/" + rekening.getRekeningNummer())).build();
    }

    @Secured("ROLE_USER")
    @Path("/{rekeningnummer}/overboekingen")
    @GET
    @ApiOperation("overboeking ophalen")
    public List<OverboekingDto> getOverboekingen(@PathParam("rekeningnummer") String rekeningnummer) {
        Collection<Overboeking> overboekingen = rekeningService.getOverboekingen(rekeningnummer);

        List<OverboekingDto> result = new ArrayList<>();
        for (Overboeking overboeking : overboekingen) {
            OverboekingDto overboekingDto = new OverboekingDto();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            overboekingDto.setBoekdatum(sdf.format(overboeking.getBoekdatum()));
            overboekingDto.setBedrag(overboeking.getBedrag());
            overboekingDto.setOmschrijving(overboeking.getOmschrijving());
            overboekingDto.setTegenRekening(overboeking.getTegenRekening());
            result.add(overboekingDto);
        }
        return result;
    }

    @Secured("ROLE_USER")
    @Path("/{rekeningnummer}/overboekingen")
    @POST
    @ApiOperation("uitvoeren van een overboeking")
    public Response createOverboeking(@PathParam("rekeningnummer") String rekeningnummer, OverboekingDto overboekingDto) {
        Overboeking overboeking = new Overboeking();
        overboeking.setBedrag(overboekingDto.getBedrag());
        overboeking.setTegenRekening(overboekingDto.getTegenRekening());
        overboeking.setOmschrijving(overboekingDto.getOmschrijving());
        rekeningService.doeOverboeking(rekeningnummer, overboeking);
        return Response.created(URI.create("/" + rekeningnummer + "/overboekingen/"+ overboeking.getId())).build();
    }

}

