package nl.quintor.rc.repository;

import nl.quintor.rc.config.TestApplicationContext;
import nl.quintor.rc.model.Gebruiker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationContext.class)
@TransactionConfiguration
@Transactional
public class GebruikerRepositoryTest {
	
	@Autowired
	private GebruikerRepository repository;
	
	@Test
	public void testGetGebruikerByInlognaam(){
		Gebruiker gebruiker = repository.getGebruikerByInlognaam("test");
        assertThat(gebruiker, is(notNullValue()));
	}

    @Test
	public void testGetGebruikerByInlognaamNamed() {
        Gebruiker gebruiker = repository.getGebruikerByInlognaamNamed("test");
        assertThat(gebruiker, is(notNullValue()));
    }

	@Test
	public void testGetGebruikerByInlognaamCriteria(){
		Gebruiker gebruiker = repository.getGebruikerByInlognaamCriteria("test");
        assertThat(gebruiker, is(notNullValue()));
	}
}