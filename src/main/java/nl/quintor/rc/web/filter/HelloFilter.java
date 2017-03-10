package nl.quintor.rc.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/hello")
public class HelloFilter implements Filter {
    static final Logger LOG = LoggerFactory.getLogger(HelloFilter.class);

    public void destroy() {
    }

    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain)
            throws IOException, ServletException {
        LOG.info("start...filter");
        filterChain.doFilter(servletRequest, servletResponse);
        LOG.info("eind...filter");
    }

    public void init(final FilterConfig filterConfig)
            throws ServletException {
    }

}
