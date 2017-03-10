package nl.quintor.rc.jms.util;

import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

public final class JMSUtil {

    private JMSUtil(){}

    public static MappingJackson2MessageConverter converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("JMSType");
        return converter;
    }

    public static Session session(ConnectionFactory connectionFactory) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        // If you don't receive any messages, start the connection!!
        connection.start();
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
}
