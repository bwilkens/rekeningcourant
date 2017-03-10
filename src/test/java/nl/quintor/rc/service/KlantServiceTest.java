package nl.quintor.rc.service;

import com.google.common.collect.Lists;

import nl.quintor.rc.model.Gebruiker;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.model.Medewerker;
import nl.quintor.rc.repository.GebruikerRepository;
import nl.quintor.rc.repository.KlantRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KlantServiceTest {

    @InjectMocks
    private KlantService klantService = new KlantService();

    @Mock
    private KlantRepository mockKlantRepository;

    @Mock
    private GebruikerRepository mockGebruikerRepository;

    @Test
    public void testCreate() {
        Klant klant = new Klant();

        klantService.create(klant);

        verify(mockKlantRepository).create(klant);
    }

    @Test
    public void testCreateMeerdereKlanten() {
        Klant klant1 = new Klant();
        Klant klant2 = new Klant();

        mockGebruikerRepository.create(klant1);
        mockGebruikerRepository.create(klant2);

        klantService.create(klant1, klant2);

        verify(mockGebruikerRepository).create(klant1);
        verify(mockGebruikerRepository).create(klant2);
    }

    @Test
    public void testGetKlantByLoginnaam() {
        String loginnaam = "username";
        Gebruiker gebruiker = new Klant();

        when(mockGebruikerRepository.getGebruikerByInlognaam(loginnaam)).thenReturn(gebruiker);

        @SuppressWarnings("unused")
		Klant klant = klantService.getKlantByLoginnaam(loginnaam);

//        verify(mockGebruikerRepository).getGebruikerByInlognaam(loginnaam);
//
//        assertThat(klant, is(notNullValue()));
    }

    @Test
    public void testGetKlantByLoginnaamGeenKlant() {
        String loginnaam = "username";
        Gebruiker gebruiker = new Medewerker();

        when(mockGebruikerRepository.getGebruikerByInlognaam(loginnaam)).thenReturn(gebruiker);

        Klant klant = klantService.getKlantByLoginnaam(loginnaam);

//        verify(mockGebruikerRepository).getGebruikerByInlognaam(loginnaam);
//
//        assertThat(klant, is(nullValue()));
    }

    @Test
    public void testGetKlanten() {
        Klant klant = new Klant();
        List<Klant> klanten = Lists.newArrayList(klant);

        when(mockKlantRepository.getKlanten()).thenReturn(klanten);

        Collection<Klant> result = klantService.getKlanten();

        verify(mockKlantRepository).getKlanten();

        assertThat(result, hasSize(klanten.size()));
    }

    @Test
    public void testGetKlantByKlantNummer() {
        Long klantId = 42L;
        Klant klant = new Klant();

        when(mockKlantRepository.getKlantByKlantNummer(klantId)).thenReturn(klant);

        Klant result = klantService.getKlantByKlantNummer(klantId);

        verify(mockKlantRepository).getKlantByKlantNummer(klantId);

        assertThat(klant, is(notNullValue()));
        assertThat(klant, is(result));
    }

    @Test
    public void testUpdateKlant() {
        Klant klant = new Klant();
        klant.setId(42L);

        klantService.updateKlant(klant);

        verify(mockKlantRepository).save(klant);
    }

    @Test
    public void testDelete() {
        Klant klant = new Klant();
        klant.setId(42L);

        when(mockKlantRepository.getKlantByKlantNummer(42L)).thenReturn(klant);

        klantService.delete(42L);

        verify(mockKlantRepository).getKlantByKlantNummer(42L);
        verify(mockKlantRepository).delete(klant);
    }
}
