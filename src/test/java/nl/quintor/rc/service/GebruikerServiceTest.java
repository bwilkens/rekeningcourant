package nl.quintor.rc.service;

import nl.quintor.rc.model.Gebruiker;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.repository.GebruikerRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(EasyMockRunner.class)
public class GebruikerServiceTest extends EasyMockSupport {

    @TestSubject
    private GebruikerService service = new GebruikerService();

    @Mock
    private GebruikerRepository mockGebruikerRepository;

    @Test
    public void testInloggenGebruiker() {
        String username = "user";
        Gebruiker gebruiker = new Klant();

        expect(mockGebruikerRepository.getGebruikerByInlognaam(username)).andReturn(gebruiker);

        replayAll();

        Gebruiker result = service.inloggenGebruiker(username);

        verifyAll();

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

        replayAll();

        Gebruiker result = service.inloggenGebruiker(username);

        verifyAll();

        assertEquals(gebruiker, result);
        assertNotNull(result.getLaatstIngelogd());
        assertEquals(laatstIngelogd, result.getVoorlaatstIngelogd());
    }

    @Test
    public void testToevoegenGebruiker() {
        Gebruiker gebruiker = new Klant();

        mockGebruikerRepository.create(gebruiker);
        expectLastCall().once();

        replayAll();

        service.toevoegenGebruiker(gebruiker);

        verifyAll();
    }
}
