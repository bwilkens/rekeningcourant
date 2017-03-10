package nl.quintor.rc.service;

import nl.quintor.rc.config.MockApplicationContext;
import nl.quintor.rc.model.Gebruiker;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.repository.GebruikerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MockApplicationContext.class)
public class GebruikerServiceTestSpring {

    private GebruikerService service;

    @Autowired
    private GebruikerRepository mockGebruikerRepository;

    @Before
    public void setup() {
        service = new GebruikerService();
        service.gebruikerRepository = mockGebruikerRepository;
        reset(mockGebruikerRepository);
    }

    @Test
    public void testInloggenGebruiker() {
        String username = "user";
        Gebruiker gebruiker = new Klant();

        expect(mockGebruikerRepository.getGebruikerByInlognaam(username)).andReturn(gebruiker);

        replay(mockGebruikerRepository);

        Gebruiker result = service.inloggenGebruiker(username);

        verify(mockGebruikerRepository);

        assertEquals(gebruiker, result);
        assertNotNull(result.getLaatstIngelogd());
        assertNull(result.getVoorlaatstIngelogd());
    }

    @Test
    public void testInloggenGebruikerEerderIngelogd() {
        String username = "user";
        Date laatstIngelogd = new Date();
        Gebruiker gebruiker = new Klant();
        gebruiker.setLaatstIngelogd(laatstIngelogd);

        expect(mockGebruikerRepository.getGebruikerByInlognaam(username)).andReturn(gebruiker);

        replay(mockGebruikerRepository);

        Gebruiker result = service.inloggenGebruiker(username);

        verify(mockGebruikerRepository);

        assertEquals(gebruiker, result);
        assertNotNull(result.getLaatstIngelogd());
        assertEquals(laatstIngelogd, result.getVoorlaatstIngelogd());
    }

    @Test
    public void testToevoegenGebruiker() {
        Gebruiker gebruiker = new Klant();

        mockGebruikerRepository.create(gebruiker);
        expectLastCall().once();

        replay(mockGebruikerRepository);

        service.toevoegenGebruiker(gebruiker);

        verify(mockGebruikerRepository);
    }
}
