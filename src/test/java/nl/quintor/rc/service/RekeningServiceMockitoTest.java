package nl.quintor.rc.service;

import com.google.common.collect.Lists;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.model.Overboeking;
import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.repository.KlantRepository;
import nl.quintor.rc.repository.RekeningRepository;
import nl.quintor.rc.service.util.IbanFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RekeningServiceMockitoTest {
    @Mock
    private RekeningRepository rekeningRepository;
    @Mock
    private KlantRepository klantRepository;
    @Mock
    private IbanFactory ibanFactory;
    @Mock
    private Rekening rekening1;
    @Mock
    private Rekening rekening2;
    @Mock
    private Klant klant1;
    @Mock
    private Klant klant2;
    @InjectMocks
    private RekeningService rekeningService; //Class under test

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldGetCorrectAantalRekeningen() {
        final Collection<Rekening> rekeningen = Lists.newArrayList(rekening1, rekening2);
        when(rekeningRepository.getByKlantNummer(123)).thenReturn(rekeningen);
        assertThat("Aantal rekeningen onjuist", rekeningService.getRekeningen(123).size(), is(2));
        assertThat(rekeningService.getRekeningen(123), is(rekeningen));
    }

    @Test
    public void shouldCreateRekeningMetTweeRekeningHouders() {
        when(ibanFactory.createRabobankIBAN()).thenReturn("NL44RABO0032443338");
        when(klantRepository.getKlantByKlantNummer(555l)).thenReturn(klant1);
        when(klantRepository.getKlantByKlantNummer(777l)).thenReturn(klant2);

        rekeningService.createRekening(rekening1, Lists.newArrayList(555l, 777l));

        verify(rekeningRepository, times(1)).create(rekening1);
        verify(klantRepository, times(1)).getKlantByKlantNummer(555l);
        verify(klantRepository, times(1)).getKlantByKlantNummer(777l);
        verify(rekening1, times(1)).addRekeninghouder(klant1);
        verify(rekening1, times(1)).addRekeninghouder(klant2);
        verify(rekening1, times(1)).setRekeningNummer("NL44RABO0032443338");

    }

    @Test
    public void shouldBeeindigRekening() {
        when(rekeningRepository.getRekeningByRekeningnummer("123")).thenReturn(rekening1);

        rekeningService.beeindigRekening("123");

        verify(rekening1).verwijderRekeninghouders();
        verify(rekeningRepository).verwijderRekening(rekening1);
    }

    @Test
    public void shouldDoeOverboeking() {
        final Overboeking overboeking = mock(Overboeking.class);
        when(rekeningRepository.getRekeningByRekeningnummer("123")).thenReturn(rekening1);

        rekeningService.doeOverboeking("123", overboeking);

        verify(rekening1).addOverboeking(overboeking);
    }

    @Test
    public void shouldGetOverboekingen() {
        final Collection<Overboeking> overboekingen = Lists.newArrayList();
        when(rekeningRepository.getOverboekingen("555")).thenReturn(overboekingen);

        assertThat(rekeningService.getOverboekingen("555"), is(overboekingen));
    }
}
