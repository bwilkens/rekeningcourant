package nl.quintor.rc.config;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.XADataSource;

/**
 * Created by marcel on 22-1-2015.
 */
@Configuration
@Profile(value = {"dev"})
@PropertySource(value = {"classpath:config/rc-config.properties", "classpath:config/rc-config.${spring.profiles.active}.properties"})
public class DevApplicationContext {
    @Autowired
    private Environment env;

    @Bean(name = "xaDataSource")
    public XADataSource xaDataSource() {
        MysqlXADataSource ds = new MysqlXADataSource();
        ds.setUrl(env.getProperty("jdbc.dataSource.url"));
        ds.setUser(env.getProperty("jdbc.dataSource.username"));
        ds.setPassword(env.getProperty("jdbc.dataSource.password"));
        ds.setPinGlobalTxToPhysicalConnection(true);
        return ds;
    }
}
