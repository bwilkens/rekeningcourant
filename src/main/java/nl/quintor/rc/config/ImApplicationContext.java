package nl.quintor.rc.config;

import org.apache.activemq.broker.BrokerService;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.XADataSource;
import java.sql.SQLException;

/**
 * Created by marcel on 22-1-2015.
 */
@Configuration
@Profile(value = {"im"})
@PropertySource(value = {"classpath:config/rc-config.properties", "classpath:config/rc-config.${spring.profiles.active}.properties"})
public class ImApplicationContext {
    @Autowired
    private Environment env;

    @Bean(name = "dbServer", destroyMethod = "stop", initMethod = "start")
    public Server dbServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "1580");
    }

    @Bean(initMethod = "start", destroyMethod = "stop" )
    public BrokerService broker() throws Exception {
        final BrokerService broker = new BrokerService();
        broker.addConnector(env.getProperty("jms.broker.url"));

        broker.setPersistent( false );
        broker.setUseShutdownHook(false);
        return broker;
    }

    @Bean(name = "xaDataSource")
    @DependsOn("dbServer")
    public XADataSource xaDataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl(env.getProperty("jdbc.dataSource.url"));
        ds.setUser(env.getProperty("jdbc.dataSource.username"));
        ds.setPassword(env.getProperty("jdbc.dataSource.password"));
        return ds;
    }
}
