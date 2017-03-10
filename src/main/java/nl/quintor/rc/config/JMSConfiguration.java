package nl.quintor.rc.config;

import com.atomikos.jms.AtomikosConnectionFactoryBean;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;

@Import({ApplicationContext.class, TXConfiguration.class})
@Configuration
public class JMSConfiguration {
    @Autowired
    private Environment env;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private MessageListener betaalopdrachtListener;

    @Autowired
    private MessageListener overboekingListener;

    @Bean
    public MessageListenerContainer betaalopdrachtMessageListenerContainer() {
        return createMessageListenerContainer(betaalopdrachtListener, "RC.BETAALOPDRACHTEN");
    }

    @Bean
    public MessageListenerContainer overboekingMessageListenerContainer() {
        return createMessageListenerContainer(overboekingListener, "RC.OVERBOEKINGEN");
    }

    @Bean
    public JmsTemplate betaalopdrachtStatusTemplate() {
        JmsTemplate template = createJmsTemplate("RC.BETAALOPDRACHTEN.STATUS");
        return template;
    }

    @Bean
    public JmsTemplate swiftBetaalopdrachtTemplate() {
        JmsTemplate template = createJmsTemplate("RC.SWIFT.BETAALOPDRACHTEN");
        return template;
    }

    @Bean
    public JmsTemplate overboekingTemplate() {
        return createJmsTemplate("RC.OVERBOEKINGEN");
    }

    private JmsTemplate createJmsTemplate(String destinationName) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(jmsConnectionFactory());
        template.setDefaultDestinationName(destinationName);
        return template;
    }

    @Bean
    public ConnectionFactory jmsConnectionFactory() {
        final RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(1);
        final ActiveMQXAConnectionFactory xaConnectionFactory = new ActiveMQXAConnectionFactory();
        xaConnectionFactory.setBrokerURL(env.getProperty("jms.broker.url"));
        xaConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        final AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setUniqueResourceName("tm_mq");
        atomikosConnectionFactoryBean.setMinPoolSize(12);
        atomikosConnectionFactoryBean.setMaxPoolSize(24);
        atomikosConnectionFactoryBean.setXaConnectionFactory(xaConnectionFactory);
        return atomikosConnectionFactoryBean;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("JMSType");
        return converter;
    }

    private MessageListenerContainer createMessageListenerContainer(final MessageListener messageListener, final String destinationName) {
        final DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(jmsConnectionFactory());
        messageListenerContainer.setDestinationName(destinationName);
        messageListenerContainer.setMessageConverter(jacksonJmsMessageConverter());
        messageListenerContainer.setMessageListener(messageListener);
        messageListenerContainer.setTransactionManager(transactionManager);
        return messageListenerContainer;
    }
}
