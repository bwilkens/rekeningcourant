package nl.quintor.rc.web.rest;

import nl.quintor.rc.model.Gebruiker;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.service.GebruikerService;
import nl.quintor.rc.web.transformer.GebruikerTransformer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class LoginWebServiceTest extends JerseyTest {

    private GebruikerService mockGebruikerService;
    private GebruikerTransformer gebruikerTransformer;

    private GenericApplicationContext context;

    @Before
    public void setup() {
    }

    @Override
    protected Application configure() {
        mockGebruikerService = createMock(GebruikerService.class);
        gebruikerTransformer = new GebruikerTransformer();

        DefaultListableBeanFactory parentBeanFactory = new DefaultListableBeanFactory();
        parentBeanFactory.registerSingleton("gebruikerService", mockGebruikerService);
        parentBeanFactory.registerSingleton("gebruikerTransformer", gebruikerTransformer);

        context = new GenericApplicationContext(parentBeanFactory);
        context.refresh();

        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig() {
            {
                property("contextConfig", context);
                register(LoginWebService.class);
            }
        };
    }

    @Test
    public void testLogin() {
        Gebruiker gebruiker = new Klant();
        expect(mockGebruikerService.inloggenGebruiker("user")).andReturn(gebruiker);

        replay(mockGebruikerService);

        Response response = target("gebruiker/user").request().get();
        assertThat("Http 200 (ok) verwacht", response.getStatus(), is(200));
        String xml = response.readEntity(String.class);
        assertThat("Rol klant verwacht", xml, containsString("<rol>klant</rol>"));
    }

    @Test
    public void testLoginFail() {
        expect(mockGebruikerService.inloggenGebruiker("user")).andReturn(null);

        replay(mockGebruikerService);

        Response response = target("gebruiker/user").request().get();
        assertThat("Http 500 (internal server error) verwacht", response.getStatus(), is(500));
    }
}
