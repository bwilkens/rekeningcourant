package nl.quintor.rc.web.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by marcel on 5-2-2017.
 */
@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<Exception> {
    private static final Logger LOG = LoggerFactory.getLogger(RuntimeExceptionMapper.class);
    @Override
    public Response toResponse(Exception throwable) {
        LOG.error("exception during processing REST api request", throwable);
        return Response.serverError().entity("oeps..something went wrong").
                type("text/plain").
                build();
    }
}
