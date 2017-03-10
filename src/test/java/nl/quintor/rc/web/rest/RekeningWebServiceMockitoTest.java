package nl.quintor.rc.web.rest;

import com.google.common.collect.Lists;
import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.service.RekeningService;
import nl.quintor.rc.web.rest.dto.KlantDto;
import nl.quintor.rc.web.rest.dto.RekeningDto;
import nl.quintor.rc.web.transformer.RekeningTransformer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RekeningWebServiceMockitoTest extends JerseyTest {

    @Mock
    private RekeningService mockRekeningService;
    @Mock
    private RekeningTransformer rekeningTransformer;
    @Captor
    private ArgumentCaptor<Rekening> rekeningArgumentCaptor;
    private GenericApplicationContext context;

    @Override
    protected Application configure() {
        initMocks(this);
        DefaultListableBeanFactory parentBeanFactory = new DefaultListableBeanFactory();
        parentBeanFactory.registerSingleton("rekeningService", mockRekeningService);
        parentBeanFactory.registerSingleton("rekeningTransformer", rekeningTransformer);

        context = new GenericApplicationContext(parentBeanFactory);
        context.refresh();
        return new ResourceConfig() {
            {
                property("contextConfig", context);
                register(RekeningWebService.class);
            }
        };
    }

    @Test
    public void testCreateRekening() {
        final KlantDto klant = new KlantDto();
        klant.setKlantnummer(42L);
        RekeningDto rekeningDto = new RekeningDto();
        rekeningDto.getKlanten().add(klant);
        rekeningDto.setType("betaalrekening");
        Collection<Rekening> rekeningen = Lists.newArrayList();

        when(mockRekeningService.getRekeningen(1)).thenReturn(rekeningen);

        Response response = target("rekeningen").request().post(Entity.json(rekeningDto));

        verify(mockRekeningService, timeout(1)).createRekening(rekeningArgumentCaptor.capture(), Matchers.<List<Long>>anyObject());
        assertThat(rekeningArgumentCaptor.getValue().getType(), is("betaalrekening"));

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(notNullValue()));

    }
}
