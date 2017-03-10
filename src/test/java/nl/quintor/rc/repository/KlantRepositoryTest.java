package nl.quintor.rc.repository;

import nl.quintor.rc.config.TestApplicationContext;
import nl.quintor.rc.model.Adres;
import nl.quintor.rc.model.Geslacht;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.util.builders.AdresBuilder;
import nl.quintor.rc.util.builders.KlantBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationContext.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class KlantRepositoryTest {
	
	@Autowired
	private KlantRepository klantRepository;

    @Autowired
    private JpaTransactionManager transactionManager;

	@Test
	public void testGetAlleKlanten(){
		Collection<Klant> klanten = klantRepository.getKlanten();
        assertThat(klanten, is(notNullValue()));
        assertThat("onjuist aantal klanten gevonden", klanten, hasSize(2));
	}

	@Test
	public void testGetKlantById(){
		Klant klant = klantRepository.getKlantByKlantNummer(1);
        assertThat(klant, is(notNullValue()));
        assertThat(klant.getAchternaam(), is("Omlo"));
        assertThat(klant.getVoorletters(), is("J.W."));
        assertThat(klant.getGeslacht(), is(Geslacht.MAN));
        assertThat(klant.getAdres().getStraat(), is("Ubbo Emmiussingel"));
        assertThat(klant.getAdres().getHuisnummer(), is(88));
        assertThat(klant.getAdres().getPostcode(), is("9727DC"));
        assertThat(klant.getAdres().getWoonplaats(), is("Groningen"));
        assertThat(klant.getBsn(), is(193707883L));
        assertThat(klant.getTelefoonNummer(), is("06-55486935"));
        assertThat(klant.getEmail(), is("j.omlo@quintor.nl"));
	}

    @Test
    public void testCreate() {
        Collection<Klant> beforeTest = klantRepository.getKlanten();
        assertThat(beforeTest, is(notNullValue()));
        assertThat("onjuist aantal klanten gevonden", beforeTest, hasSize(2));

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
        Adres adres = AdresBuilder.anAdres().huisnummer(1).build();
        Klant klant = KlantBuilder.aKlant().achternaam("Snippen").voorletters("P.D.").bsn(107995839L).adres(adres).build();
        klantRepository.create(klant);

        Collection<Klant> klanten = klantRepository.getKlanten();
        assertThat(klanten, is(notNullValue()));
        assertThat("onjuist aantal klanten gevonden", klanten, hasSize(3));
        transactionManager.rollback(status);

        Collection<Klant> afterRollback = klantRepository.getKlanten();
        assertThat(afterRollback, is(notNullValue()));
        assertThat("onjuist aantal klanten gevonden", afterRollback, hasSize(2));

    }

    @Test
    @Transactional
    public void testCreateAnnotation() {
        Collection<Klant> klanten = klantRepository.getKlanten();
        assertThat(klanten, is(notNullValue()));
        assertThat("onjuist aantal klanten gevonden", klanten, hasSize(2));

        Adres adres = AdresBuilder.anAdres().huisnummer(1).build();
        Klant klant = KlantBuilder.aKlant().achternaam("Snippen").voorletters("P.D.").bsn(107995839L).adres(adres).build();
        klantRepository.create(klant);

        Collection<Klant> after = klantRepository.getKlanten();
        assertThat(after, is(notNullValue()));
        assertThat("onjuist aantal klanten gevonden", after, hasSize(3));

    }
}