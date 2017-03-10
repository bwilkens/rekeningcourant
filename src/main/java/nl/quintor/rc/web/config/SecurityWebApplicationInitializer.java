package nl.quintor.rc.web.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(value = 100)
public class SecurityWebApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {
    public SecurityWebApplicationInitializer() {
    }
}