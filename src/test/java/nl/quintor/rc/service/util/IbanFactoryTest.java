package nl.quintor.rc.service.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class IbanFactoryTest {
    @Mock
    private AccountNumberGenerator accountNumberGenerator;
    @InjectMocks
    private IbanFactory ibanFactory;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldCreateRaboRekeningnummer() {
        when(accountNumberGenerator.generateAccountNumber()).thenReturn("0032443338");
        assertThat(ibanFactory.createRabobankIBAN(), is("NL44RABO0032443338"));
    }
}
