package nl.quintor.rc.repository;

import nl.quintor.rc.config.TestApplicationContext;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.model.Overboeking;
import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.util.builders.RekeningBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationContext.class)
@TransactionConfiguration
@Transactional
public class RekeningRepositoryTest {

	@Autowired
	private RekeningRepository rekeningRepository;

	@Test
	public void testCreateRekening() {
		Klant klant = new Klant();

		Rekening rekening = new Rekening();
		rekening.setBeginDatum(new Date());
		rekening.setRekeningNummer("NL52INGB0006380423");
		rekening.setType("enkelvoudig");
		rekening.addRekeninghouder(klant);

		rekeningRepository.create(rekening);
	}

    @Test
    public void shouldFindOneRekening(){
        Collection<Rekening> rekeningen = rekeningRepository.getByKlantNummer(1);
        assertThat(rekeningen, is(notNullValue()));
        assertThat("onjuist aantal klanten gevonden", rekeningen, hasSize(1));
    }

    @Test
    public void shouldNotFindAnything(){
        Collection<Rekening> rekeningen = rekeningRepository.getByKlantNummer(333);
        assertThat(rekeningen, is(notNullValue()));
        assertThat("onjuist aantal klanten gevonden", rekeningen, hasSize(0));
    }

	@Test
	public void testGetGoedteKeurenRekeningen() {
		List<Rekening> rekeningen = rekeningRepository.getGoedTeKeurenRekeningen();
        assertThat("onjuist aantal goed te keuren rekeningen", rekeningen, hasSize(2));
        assertThat("onjuiste rekening gevonden", rekeningen.get(0).getRekeningNummer(), is("NL12RABO004324523"));
        assertThat("onjuiste rekening gevonden", rekeningen.get(1).getRekeningNummer(), is("NL48RABO0789441742"));
	}

    @Test
    public void testGetRekeningByRekeningnummer() {
        Rekening rekening = rekeningRepository.getRekeningByRekeningnummer("NL12RABO004324523");
        assertThat(rekening, is(notNullValue()));
    }

	@Test
	public void testGetOverboekingen() {
		Collection<Overboeking> overboekingen = rekeningRepository.getOverboekingen("NL12RABO004324523");
        assertThat(overboekingen, hasSize(1));
	}

    @Test(expected = NoResultException.class)
    public void deleteRekening() {
        // We kunnen niet een entity deleten die met liquibase aangemaakt is, daarom hier een nieuwe aanmaken
        // Dit heeft te maken met de transactie waarin hij is aangemaakt en de transactie waarin we nu draaien.
        Rekening rekening = RekeningBuilder.aRekening().rekeningNummer("NL76ABNA0539167193").build();
        rekeningRepository.create(rekening);
        Rekening before = rekeningRepository.getRekeningByRekeningnummer("NL76ABNA0539167193");
        assertThat(before, is(notNullValue()));
        rekeningRepository.verwijderRekening(rekening);

        Rekening after = rekeningRepository.getRekeningByRekeningnummer("NL76ABNA0539167193");
        assertThat(after, is(nullValue()));
    }
}
