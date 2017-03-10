package nl.quintor.rc.logging;

import org.apache.commons.lang3.time.FastDateFormat;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Aspect voor het aanmaken van een audit trail
 */
@Aspect
@Component
public class AuditAspect {
    static final Logger LOG = LoggerFactory.getLogger(AuditAspect.class);
    private static final FastDateFormat SDF = FastDateFormat.getInstance("dd-MM-yyyy HH:mm:ss");

    @Around("execution(* nl.quintor.rc.service.*Service.*(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String gebruiker = auth == null ? "GEEN_GEBRUIKER" : auth.getName();

        LOG.debug("{} - [{}] roept [{}] aan", SDF.format(new Date()), gebruiker, joinPoint.getSignature());
        Object result = joinPoint.proceed();
        LOG.debug("Klaar");
        return result;
    }
}
