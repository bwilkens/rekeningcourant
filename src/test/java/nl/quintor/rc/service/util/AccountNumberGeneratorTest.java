package nl.quintor.rc.service.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountNumberGeneratorTest {
    @Mock
    private Random random;

    private AccountNumberGenerator accountNumberGenerator; //Class under test

    @Before
    public void setUp() {
        initMocks(this);
        accountNumberGenerator = new AccountNumberGenerator(random);
    }

    @Test
    public void shouldGenerateValidAccountNumber() {
        when(random.nextInt(any(Integer.class))).thenReturn(445544545);
        assertThat(accountNumberGenerator.generateAccountNumber(), is("0445544545"));
    }
}
