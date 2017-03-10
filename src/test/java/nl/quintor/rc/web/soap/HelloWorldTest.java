package nl.quintor.rc.web.soap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HelloWorldTest {

    private static HelloWorldImpl sut = new HelloWorldImpl();
    private static Endpoint endpoint;

    @BeforeClass
    public static void beforeClass() throws Exception {
        endpoint = Endpoint.publish("http://localhost:9999/ws/hello", sut);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        endpoint.stop();
    }

    @Test
    public void testGetHelloWorldAsString() throws MalformedURLException {
        String name = "masterclass";

        URL wsdlUrl = new URL("http://localhost:9999/ws/hello?wsdl");
        QName serviceName = new QName("http://soap.web.rc.quintor.nl/", "HelloWorldImplService");
        Service service = Service.create(wsdlUrl, serviceName);

        HelloWorld port = service.getPort(HelloWorld.class);

        String result = port.getHelloWorldAsString(name);
        assertThat(result, is("Hello masterclass from JAX-WS "));
    }
}
