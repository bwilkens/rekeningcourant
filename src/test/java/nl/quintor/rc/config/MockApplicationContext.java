package nl.quintor.rc.config;

import nl.quintor.rc.repository.GebruikerRepository;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class MockApplicationContext {

    @Bean(name = "mockGebruikerRepository")
    public GebruikerRepository gebruikerRepository() {
        return EasyMock.createMock(GebruikerRepository.class);
    }

    @Bean(name = "RC_Unit")
    public EntityManagerFactory emf() {
        return EasyMock.createMock(EntityManagerFactory.class);
    }

}
