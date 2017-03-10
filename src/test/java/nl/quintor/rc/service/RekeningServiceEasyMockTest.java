package nl.quintor.rc.service;

import com.google.common.collect.Lists;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.model.Overboeking;
import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.model.Status;
import nl.quintor.rc.repository.KlantRepository;
import nl.quintor.rc.repository.RekeningRepository;
import nl.quintor.rc.service.util.IbanFactory;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.List;

import static nl.quintor.rc.util.matchers.RegexMatcher.matchesRegex;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(EasyMockRunner.class)
public class RekeningServiceEasyMockTest extends EasyMockSupport {

    @TestSubject
    private RekeningService service = new RekeningService();

    @Mock
    private RekeningRepository mockRekeningRepository;
    @Mock
    private KlantRepository mockKlantRepository;
    @Mock
    private IbanFactory ibanFactory;

    @Test
    public void testGoedTeKeurenRekeningen() {
        Rekening rekening = new Rekening();
        List<Rekening> rekeningen = Lists.newArrayList(rekening);
        expect(mockRekeningRepository.getGoedTeKeurenRekeningen()).andReturn(rekeningen);
        replayAll();
        List<Rekening> result = service.getGoedTeKeurenRekeningen();
        assertThat(result, is(rekeningen));
        verifyAll();
    }

    @Test
    public void testCreateRekening() {
        Klant klant = new Klant();
        klant.setId(42L);
        Rekening rekening = new Rekening();
        mockRekeningRepository.create(rekening);
        expectLastCall().once();
        expect(mockKlantRepository.getKlantByKlantNummer(42L)).andReturn(klant);
        expect(ibanFactory.createRabobankIBAN()).andReturn("NL44RABO0032443338");

        replayAll();
        service.createRekening(rekening, Lists.newArrayList(42L));

        assertThat(rekening.getRekeningNummer(), is(notNullValue()));
        assertThat("Rekening nummer not valid iban (should be XX00XXXX0000000000)", rekening.getRekeningNummer(), is(matchesRegex("\\D\\D\\d\\d\\D\\D\\D\\D\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d")));
        assertThat(rekening.getRekeninghouders(), contains(klant));

        verifyAll();
    }

    @Test
    public void testBlokkeerRekening() {
        String rekeningNummer = "XX00XXXX000000000";
        Rekening rekening = new Rekening();
        rekening.setRekeningNummer(rekeningNummer);
        expect(mockRekeningRepository.getRekeningByRekeningnummer(rekeningNummer)).andReturn(rekening);
        mockRekeningRepository.saveRekening(rekening);
        expectLastCall().once();

        replayAll();
        service.blokkeerRekening(rekeningNummer, "Wanbetaler");

        assertThat(rekening.getRedenGeblokkeerd(), is("Wanbetaler"));
        assertThat(rekening.getDatumGeblokkeerd(), is(notNullValue()));
        assertThat(rekening.getStatus(), is(Status.GEBLOKKEERD));
        verifyAll();
    }

    @Test
    public void testGetRekeningen() {
        Rekening rekening = new Rekening();
        expect(mockRekeningRepository.getByKlantNummer(42L)).andReturn(Lists.newArrayList(rekening));

        replayAll();
        Collection<Rekening> result = service.getRekeningen(42L);
        assertThat(result, hasSize(1));
        assertThat(result, hasItem(rekening));
        verifyAll();
    }

    @Test
    public void testGetOverboekingen() {
        String rekeningNummer = "XX00XXXX0123456789";
        Overboeking overboeking = new Overboeking();
        expect(mockRekeningRepository.getOverboekingen(rekeningNummer)).andReturn(Lists.newArrayList(overboeking));

        replayAll();
        Collection<Overboeking> result = service.getOverboekingen(rekeningNummer);
        assertThat(result, hasSize(1));
        assertThat(result, hasItem(overboeking));
        verifyAll();
    }

    @Test
    public void testBeeindigRekening() {
        String rekeningNummer = "XX00XXXX0123456789";
        Klant klant = new Klant();
        Rekening rekening = new Rekening();
        rekening.addRekeninghouder(klant);

        expect(mockRekeningRepository.getRekeningByRekeningnummer(rekeningNummer)).andReturn(rekening);
        mockRekeningRepository.verwijderRekening(rekening);

        replayAll();

        service.beeindigRekening(rekeningNummer);

        assertThat(rekening.getRekeninghouders(), hasSize(0));

        verifyAll();
    }

    @Test
    public void testDoeOverboeking() {
        String rekeningNummer = "XX00XXXX0123456789";
        Overboeking overboeking = new Overboeking();
        Rekening rekening = new Rekening();

        expect(mockRekeningRepository.getRekeningByRekeningnummer(rekeningNummer)).andReturn(rekening);

        replayAll();

        service.doeOverboeking(rekeningNummer, overboeking);

        assertThat(rekening.getOverboekingen(), hasSize(1));
        assertThat(rekening.getOverboekingen(), hasItem(overboeking));
        verifyAll();
    }
}
