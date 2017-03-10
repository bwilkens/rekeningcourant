package nl.quintor.rc.web.servlet;

import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Created by marcel on 1/2/2017.
 */
@WebServlet(loadOnStartup = 2)
public class SwaggerBootstrapServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {

        System.out.println("init swagger....");
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("http://localhost:8080/rc");
        beanConfig.setResourcePackage("nl.quintor.rc.web.rest");
        beanConfig.setScan(true);
    }



}
