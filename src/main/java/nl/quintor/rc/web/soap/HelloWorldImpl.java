

package nl.quintor.rc.web.soap;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface = "nl.quintor.rc.web.soap.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    @Override
    public String getHelloWorldAsString(@WebParam(name = "hello") String hello) {
        return String.format("Hello %s from JAX-WS ", hello);
    }
}
