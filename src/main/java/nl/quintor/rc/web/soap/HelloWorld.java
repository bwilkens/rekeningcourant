


package nl.quintor.rc.web.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC, use= SOAPBinding.Use.LITERAL) //optional
public interface HelloWorld{

    @WebMethod ()
    String getHelloWorldAsString(@WebParam(name = "hello") String hello);
}