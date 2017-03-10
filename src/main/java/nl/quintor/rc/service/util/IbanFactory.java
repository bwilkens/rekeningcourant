package nl.quintor.rc.service.util;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IbanFactory {
    private static final String RABO_BANKCODE = "RABO";
    private final AccountNumberGenerator accountNumberGenerator;

    @Autowired
    public IbanFactory(AccountNumberGenerator accountNumberGenerator) {
        this.accountNumberGenerator = accountNumberGenerator;
    }

    /**
     * Maak een Rabobank IBAN
     * @return The Rabobank IBAN
     */
    public String createRabobankIBAN() {
        final String generatedAccountNumber = accountNumberGenerator.generateAccountNumber();
        return new Iban.Builder().countryCode(CountryCode.NL).bankCode(RABO_BANKCODE).accountNumber(generatedAccountNumber).build().toString();
    }
}
