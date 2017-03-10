package nl.quintor.rc.config;

import com.atomikos.icatch.config.UserTransactionService;
import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY)
public class TXConfiguration {
    @Bean(name = "userTransactionService", initMethod = "init", destroyMethod = "shutdownWait")
    public UserTransactionService userTransactionService() {
        return new UserTransactionServiceImp(txProperties());
    }

    @DependsOn("userTransactionService")
    @Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
    public UserTransactionManager atomikosTransactionManager() {
        final UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        userTransactionManager.setStartupTransactionService(false);
        return userTransactionManager;
    }

    @Bean(name = "atomikosUserTransaction")
    public UserTransaction atomikosUserTransaction() throws SystemException {
        final UserTransaction userTransaction = new UserTransactionImp();
        userTransaction.setTransactionTimeout(30);
        return userTransaction;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() throws SystemException {
        final JtaTransactionManager transactionManager = new JtaTransactionManager();
        transactionManager.setTransactionManager(atomikosTransactionManager());
        transactionManager.setUserTransaction(atomikosUserTransaction());
        return transactionManager;
    }

    private Properties txProperties() {
        final Properties properties = new Properties();
        return properties;
    }
}
