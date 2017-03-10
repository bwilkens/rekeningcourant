package nl.quintor.rc.it;

import nl.quintor.rc.config.ITTestApplicationContext;
import nl.quintor.rc.repository.KlantRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITTestApplicationContext.class)
public abstract class AbstractIntegrationTest {


    protected static final String SUT_URL = "http://localhost:26186/rc/api";

    @Autowired
    private JpaTransactionManager transactionManager;

    @Autowired
    protected KlantRepository klantRepository;

    protected TransactionStatus beginTransaction() {
        return transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
    }

    protected void commitTransaction(TransactionStatus status) {
        transactionManager.commit(status);
    }
}
