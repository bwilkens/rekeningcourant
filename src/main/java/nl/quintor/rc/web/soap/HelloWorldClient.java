package nl.quintor.rc.web.soap;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class HelloWorldClient {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:9999/ws/hello?wsdl");

        //Represents a qualified name as per XML Schema's definition. The value of a QName contains a Namespace URI, local part and prefix.
        QName qname = new QName("http://soap.web.rc.quintor.nl/", "HelloWorldImplService");

        //Service objects provide the client view of a Web service.
        Service service = Service.create(url, qname);

        //The getPort method returns a proxy. A service client uses this proxy to invoke operations on the target service endpoint.
        //The serviceEndpointInterface specifies the service endpoint interface that is supported by the created dynamic proxy instance.
        HelloWorld hello = service.getPort(HelloWorld.class);
        System.out.println(hello.getHelloWorldAsString("Marcel"));
    }
}