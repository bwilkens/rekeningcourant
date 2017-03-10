package nl.quintor.rc.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountNumberGenerator {
    private final Random random;

    @Autowired
    public AccountNumberGenerator(@Value("#{new java.util.Random()}") Random random) {
        this.random = random;
    }

    /**
     * Generate a random account numer
     * @return the generated account number
     */
    public String generateAccountNumber() {
        final Integer randomAccountNum = random.nextInt(1000000000);
        return String.format("0%9s", randomAccountNum.toString()).replace(' ', '0');
    }
}
